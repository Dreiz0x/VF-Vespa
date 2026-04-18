package dev.vskelk.cdf.data.repository.repository

import dev.vskelk.cdf.core.database.dao.CuarentenaDao
import dev.vskelk.cdf.core.database.dao.NormativeDao
import dev.vskelk.cdf.core.database.entity.CuarentenaFragmentoEntity
import dev.vskelk.cdf.core.datastore.datasource.LlmProvider
import dev.vskelk.cdf.core.datastore.datasource.PreferencesDataSource
import dev.vskelk.cdf.core.network.datasource.LlmRemoteDataSource
import dev.vskelk.cdf.domain.model.CertezaNivel
import dev.vskelk.cdf.domain.model.CuarentenaEstado
import dev.vskelk.cdf.domain.model.FragmentoCuarentena
import dev.vskelk.cdf.domain.model.FuenteTipo
import dev.vskelk.cdf.domain.model.ResultadoInvestigacion
import dev.vskelk.cdf.domain.model.SolicitudInvestigacion
import dev.vskelk.cdf.domain.repository.InvestigadorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InvestigadorRepositoryImpl @Inject constructor(
    private val llmDataSource: LlmRemoteDataSource,
    private val cuarentenaDao: CuarentenaDao,
    private val normativeDao: NormativeDao,
    private val preferencesDataSource: PreferencesDataSource,
) : InvestigadorRepository {

    // ── Paso 1: acotar — ver qué hay en Room sobre el tema ──────────────────
    override suspend fun investigar(solicitud: SolicitudInvestigacion): ResultadoInvestigacion {
        val contextoExistente = normativeDao.searchByKeyword(solicitud.tema)
            .take(3)
            .joinToString("\n") { it.content }

        // ── Paso 2: formular prompt de extracción estructurada ───────────────
        val prompt = buildExtractionPrompt(solicitud.tema, contextoExistente)

        val prefs = preferencesDataSource.observeData().first()
        val apiKey = preferencesDataSource.getApiKey(prefs.activeProvider) ?: ""

        // ── Paso 3: llamar a la IA y parsear respuesta JSON ──────────────────
        val result = llmDataSource.sendMessage(apiKey, prompt)
        val rawJson = result.getOrNull() ?: return ResultadoInvestigacion(
            fragmentos = emptyList(), tokensUsados = 0, modeloUsado = "unknown"
        )

        val fragmentos = parseFragmentos(rawJson, FuenteTipo.INVESTIGACION_IA)

        // ── Paso 4: validación cruzada ───────────────────────────────────────
        val fragmentosValidados = validarContraRoom(fragmentos)

        // ── Paso 5: guardar en cuarentena ────────────────────────────────────
        cuarentenaDao.insertAll(fragmentosValidados.map { it.toEntity() })

        return ResultadoInvestigacion(
            fragmentos = fragmentosValidados,
            tokensUsados = 0, // se puede enriquecer si la API lo devuelve
            modeloUsado = prefs.activeProvider.name,
        )
    }

    override suspend fun ingerirDocumento(texto: String, fuente: String): ResultadoInvestigacion {
        val prompt = buildDocumentExtractionPrompt(texto, fuente)
        val prefs = preferencesDataSource.observeData().first()
        val apiKey = preferencesDataSource.getApiKey(prefs.activeProvider) ?: ""

        val result = llmDataSource.sendMessage(apiKey, prompt)
        val rawJson = result.getOrNull() ?: return ResultadoInvestigacion(
            fragmentos = emptyList(), tokensUsados = 0, modeloUsado = "unknown"
        )

        val fragmentos = parseFragmentos(rawJson, FuenteTipo.DOCUMENTO_USUARIO)
        val validados = validarContraRoom(fragmentos)

        // Documentos del usuario van a cuarentena con certeza ALTA pero el usuario igual revisa
        cuarentenaDao.insertAll(validados.map { it.toEntity() })

        return ResultadoInvestigacion(validados, 0, prefs.activeProvider.name)
    }

    override fun observarCuarentena(): Flow<List<FragmentoCuarentena>> =
        cuarentenaDao.observePendientes().map { list -> list.map { it.toDomain() } }

    override suspend fun aprobar(fragmentoId: Long) {
        val fragmento = cuarentenaDao.getById(fragmentoId) ?: return
        cuarentenaDao.updateEstado(fragmentoId, "APROBADO")
        // Promover a normativa canónica en Room — mapeo a NormativeEntity pendiente
    }

    override suspend fun rechazar(fragmentoId: Long) =
        cuarentenaDao.updateEstado(fragmentoId, "RECHAZADO")

    override suspend fun limpiarAprobados() = cuarentenaDao.deletAprobados()

    // ── Helpers ──────────────────────────────────────────────────────────────

    private suspend fun validarContraRoom(
        fragmentos: List<FragmentoCuarentena>,
    ): List<FragmentoCuarentena> = fragmentos.map { fragmento ->
        val existentes = normativeDao.searchByKeyword(fragmento.fuente)
        val conflicto = existentes.firstOrNull { existente ->
            existente.content.contains(fragmento.fuente, ignoreCase = true) &&
                !existente.content.contains(
                    fragmento.contenido.take(40),
                    ignoreCase = true
                )
        }
        if (conflicto != null) {
            fragmento.copy(
                estado = CuarentenaEstado.CONFLICTO,
                conflictoConId = conflicto.id,
                conflictoDescripcion = "Contradice fragmento existente: ${conflicto.content.take(80)}...",
            )
        } else {
            fragmento
        }
    }

    private fun parseFragmentos(raw: String, tipo: FuenteTipo): List<FragmentoCuarentena> {
        return try {
            val clean = raw.replace("```json", "").replace("```", "").trim()
            val array = Json.parseToJsonElement(clean).jsonArray
            array.mapNotNull { element ->
                val obj = element.jsonObject
                val fuente = obj["fuente"]?.jsonPrimitive?.content ?: return@mapNotNull null
                val contenido = obj["contenido"]?.jsonPrimitive?.content ?: return@mapNotNull null
                val certezaRaw = obj["certeza"]?.jsonPrimitive?.content ?: "BAJA"
                val certeza = when (certezaRaw.uppercase()) {
                    "ALTA" -> CertezaNivel.ALTA
                    "MEDIA" -> CertezaNivel.MEDIA
                    else -> CertezaNivel.BAJA
                }
                FragmentoCuarentena(
                    contenido = contenido,
                    fuente = fuente,
                    fuenteTipo = tipo,
                    certeza = certeza,
                    areaExamen = obj["area"]?.jsonPrimitive?.content,
                    conflictoConId = null,
                    conflictoDescripcion = null,
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun buildExtractionPrompt(tema: String, contextoExistente: String): String = """
        Eres un extractor de información jurídico-electoral especializado en normativa del INE México.
        Tu tarea es extraer información estructurada sobre el siguiente tema: "$tema"

        CONTEXTO YA EXISTENTE EN LA BASE (no repitas esto):
        $contextoExistente

        REGLAS OBLIGATORIAS:
        - Solo incluye información de: LEGIPE, Reglamento de Elecciones INE, Acuerdos del Consejo General INE, TEPJF, Reglamento Interior INE.
        - Si no tienes certeza de la fuente exacta, asigna certeza BAJA.
        - NO incluyas información de Wikipedia, blogs, o fuentes no oficiales.
        - NO repitas información del contexto existente.

        Responde ÚNICAMENTE con un arreglo JSON, sin texto adicional, sin markdown:
        [
          {
            "fuente": "LEGIPE Art. 72",
            "contenido": "texto exacto o paráfrasis fiel",
            "certeza": "ALTA|MEDIA|BAJA",
            "area": "TECNICO|SISTEMA|null"
          }
        ]
    """.trimIndent()

    private fun buildDocumentExtractionPrompt(texto: String, fuente: String): String = """
        Eres un extractor de información jurídico-electoral.
        Extrae los fragmentos de conocimiento del siguiente documento oficial: "$fuente"

        DOCUMENTO:
        $texto

        Responde ÚNICAMENTE con un arreglo JSON, sin texto adicional:
        [
          {
            "fuente": "$fuente, sección/artículo específico",
            "contenido": "fragmento relevante",
            "certeza": "ALTA",
            "area": "TECNICO|SISTEMA|null"
          }
        ]
    """.trimIndent()
}

private fun FragmentoCuarentena.toEntity() = CuarentenaFragmentoEntity(
    contenido = contenido,
    fuente = fuente,
    fuenteTipo = fuenteTipo.name,
    certeza = certeza.name,
    areaExamen = areaExamen,
    conflictoConId = conflictoConId,
    conflictoDescripcion = conflictoDescripcion,
    estado = estado.name,
    creadoEn = creadoEn,
)

private fun CuarentenaFragmentoEntity.toDomain() = FragmentoCuarentena(
    id = id,
    contenido = contenido,
    fuente = fuente,
    fuenteTipo = FuenteTipo.valueOf(fuenteTipo),
    certeza = CertezaNivel.valueOf(certeza),
    areaExamen = areaExamen,
    conflictoConId = conflictoConId,
    conflictoDescripcion = conflictoDescripcion,
    estado = CuarentenaEstado.valueOf(estado),
    creadoEn = creadoEn,
)
