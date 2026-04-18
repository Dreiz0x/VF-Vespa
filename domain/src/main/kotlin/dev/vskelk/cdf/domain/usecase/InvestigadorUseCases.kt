package dev.vskelk.cdf.domain.usecase

import dev.vskelk.cdf.domain.model.SolicitudInvestigacion
import dev.vskelk.cdf.domain.repository.InvestigadorRepository
import javax.inject.Inject

class InvestigarTemaUseCase @Inject constructor(
    private val repository: InvestigadorRepository,
) {
    suspend operator fun invoke(tema: String, contexto: String? = null) =
        repository.investigar(SolicitudInvestigacion(tema, contexto))
}

class IngerirDocumentoUseCase @Inject constructor(
    private val repository: InvestigadorRepository,
) {
    suspend operator fun invoke(texto: String, fuente: String) =
        repository.ingerirDocumento(texto, fuente)
}

class ObservarCuarentenaUseCase @Inject constructor(
    private val repository: InvestigadorRepository,
) {
    operator fun invoke() = repository.observarCuarentena()
}

class AprobarFragmentoUseCase @Inject constructor(
    private val repository: InvestigadorRepository,
) {
    suspend operator fun invoke(fragmentoId: Long) = repository.aprobar(fragmentoId)
}

class RechazarFragmentoUseCase @Inject constructor(
    private val repository: InvestigadorRepository,
) {
    suspend operator fun invoke(fragmentoId: Long) = repository.rechazar(fragmentoId)
}
