package dev.vskelk.cdf.domain.repository

import dev.vskelk.cdf.domain.model.NodeWithNormativeFragments
import dev.vskelk.cdf.domain.model.NormFragment
import dev.vskelk.cdf.domain.model.NormSource
import dev.vskelk.cdf.domain.model.OntologyNode
import dev.vskelk.cdf.domain.model.Reactivo
import dev.vskelk.cdf.domain.model.ReactivoAggregateModel
import kotlinx.coroutines.flow.Flow

interface NormativeRepository {
    fun observeSources(): Flow<List<NormSource>>
    fun observeCurrentFragments(sourceCode: String): Flow<List<NormFragment>>
}

interface OntologyRepository {
    fun observeActiveNodes(nodeType: String? = null): Flow<List<OntologyNode>>
    suspend fun getNodeWithFragments(nodeId: Long): NodeWithNormativeFragments?
}

interface ReactivoRepository {
    fun observeActiveReactivos(examArea: String? = null): Flow<List<Reactivo>>
    suspend fun getReactivoAggregate(reactivoId: Long): ReactivoAggregateModel?
    suspend fun getSimulationReactivos(examArea: String?, limit: Int): List<ReactivoAggregateModel>
    suspend fun invalidateByNormVersion(normVersionId: Long, reason: String): Int
}
