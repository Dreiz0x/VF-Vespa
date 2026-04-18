package dev.vskelk.cdf.domain.model

enum class CuarentenaEstado { PENDIENTE, APROBADO, RECHAZADO, CONFLICTO }
enum class FuenteTipo { DOCUMENTO_USUARIO, INVESTIGACION_IA }
enum class CertezaNivel { ALTA, MEDIA, BAJA }

data class FragmentoCuarentena(
    val id: Long = 0,
    val contenido: String,
    val fuente: String,           // "LEGIPE Art. 72" / "Acuerdo INE/CG123/2024" / etc.
    val fuenteTipo: FuenteTipo,
    val certeza: CertezaNivel,
    val areaExamen: String?,      // TECNICO / SISTEMA / null
    val conflictoConId: Long?,    // id del fragmento canónico que contradice
    val conflictoDescripcion: String?,
    val estado: CuarentenaEstado = CuarentenaEstado.PENDIENTE,
    val creadoEn: Long = System.currentTimeMillis(),
)

data class SolicitudInvestigacion(
    val tema: String,
    val contexto: String? = null, // qué hay ya en Room sobre este tema
)

data class ResultadoInvestigacion(
    val fragmentos: List<FragmentoCuarentena>,
    val tokensUsados: Int,
    val modeloUsado: String,
)
