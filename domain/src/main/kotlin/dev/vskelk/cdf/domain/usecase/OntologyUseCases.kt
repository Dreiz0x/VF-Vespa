package dev.vskelk.cdf.domain.usecase

import dev.vskelk.cdf.domain.model.ReactivoDiagnostic
import dev.vskelk.cdf.domain.repository.NormativeRepository
import dev.vskelk.cdf.domain.repository.OntologyRepository
import dev.vskelk.cdf.domain.repository.ReactivoRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveNormSourcesUseCase @Inject constructor(
    private val repository: NormativeRepository,
) {
    operator fun invoke() = repository.observeSources()
}

class ObserveCurrentFragmentsUseCase @Inject constructor(
    private val repository: NormativeRepository,
) {
    operator fun invoke(sourceCode: String) = repository.observeCurrentFragments(sourceCode)
}

class ObserveOntologyNodesUseCase @Inject constructor(
    private val repository: OntologyRepository,
) {
    operator fun invoke(nodeType: String? = null) = repository.observeActiveNodes(nodeType)
}

class GetNodeWithFragmentsUseCase @Inject constructor(
    private val repository: OntologyRepository,
) {
    suspend operator fun invoke(nodeId: Long) = repository.getNodeWithFragments(nodeId)
}

class ObserveActiveReactivosUseCase @Inject constructor(
    private val repository: ReactivoRepository,
) {
    operator fun invoke(examArea: String? = null) = repository.observeActiveReactivos(examArea)
}

class GetReactivoAggregateUseCase @Inject constructor(
    private val repository: ReactivoRepository,
) {
    suspend operator fun invoke(reactivoId: Long) = repository.getReactivoAggregate(reactivoId)
}

class GetSimulationReactivosUseCase @Inject constructor(
    private val repository: ReactivoRepository,
) {
    suspend operator fun invoke(examArea: String?, limit: Int) =
        repository.getSimulationReactivos(examArea, limit)
}

class InvalidateReactivosByNormVersionUseCase @Inject constructor(
    private val repository: ReactivoRepository,
) {
    suspend operator fun invoke(normVersionId: Long, reason: String): Int =
        repository.invalidateByNormVersion(normVersionId, reason)
}

class ObserveReactivoDiagnosticsUseCase @Inject constructor(
    private val repository: ReactivoRepository,
) {
    operator fun invoke() = repository.observeActiveReactivos().map { reactivos ->
        reactivos
            .groupBy { it.examArea }
            .map { (area, items) ->
                ReactivoDiagnostic(
                    examArea = area,
                    count = items.size,
                    byCognitiveLevel = items.groupingBy { it.cognitiveLevel }.eachCount(),
                )
            }
            .sortedBy { it.examArea }
    }
}

class ObserveInterviewNodesUseCase @Inject constructor(
    private val repository: OntologyRepository,
) {
    operator fun invoke() = repository.observeActiveNodes().map { nodes ->
        nodes.filter { node ->
            node.nodeType.equals("COMPETENCIA", ignoreCase = true) ||
                node.nodeType.equals("BEHAVIOR", ignoreCase = true) ||
                node.label.contains("entrevista", ignoreCase = true)
        }
    }
}
