package dev.vskelk.cdf.domain.repository

import dev.vskelk.cdf.domain.model.FragmentoCuarentena
import dev.vskelk.cdf.domain.model.ResultadoInvestigacion
import dev.vskelk.cdf.domain.model.SolicitudInvestigacion
import kotlinx.coroutines.flow.Flow

interface InvestigadorRepository {
    // Solicitar investigación — regresa fragmentos en cuarentena
    suspend fun investigar(solicitud: SolicitudInvestigacion): ResultadoInvestigacion

    // Cuarentena
    fun observarCuarentena(): Flow<List<FragmentoCuarentena>>
    suspend fun aprobar(fragmentoId: Long)
    suspend fun rechazar(fragmentoId: Long)
    suspend fun limpiarAprobados()

    // Ingesta de documento del usuario (texto ya extraído)
    suspend fun ingerirDocumento(texto: String, fuente: String): ResultadoInvestigacion
}
