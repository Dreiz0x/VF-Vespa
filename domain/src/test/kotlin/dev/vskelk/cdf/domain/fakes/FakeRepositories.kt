package dev.vskelk.cdf.domain.fakes

import dev.vskelk.cdf.core.common.AppResult
import dev.vskelk.cdf.domain.model.AppSettings
import dev.vskelk.cdf.domain.model.ChaosStatus
import dev.vskelk.cdf.domain.model.DecisionPath
import dev.vskelk.cdf.domain.model.Message
import dev.vskelk.cdf.domain.model.MessageRole
import dev.vskelk.cdf.domain.model.NodeWithNormativeFragments
import dev.vskelk.cdf.domain.model.NormFragment
import dev.vskelk.cdf.domain.model.NormSource
import dev.vskelk.cdf.domain.model.OntologyNode
import dev.vskelk.cdf.domain.model.Reactivo
import dev.vskelk.cdf.domain.model.ReactivoAggregateModel
import dev.vskelk.cdf.domain.model.ReactivoMetadata
import dev.vskelk.cdf.domain.model.ReactivoOption
import dev.vskelk.cdf.domain.model.ReactivoValidity
import dev.vskelk.cdf.domain.repository.ChaosRepository
import dev.vskelk.cdf.domain.repository.ConversationRepository
import dev.vskelk.cdf.domain.repository.NormativeRepository
import dev.vskelk.cdf.domain.repository.OntologyRepository
import dev.vskelk.cdf.domain.repository.ReactivoRepository
import dev.vskelk.cdf.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class FakeConversationRepository : ConversationRepository {
    private val state = MutableStateFlow<List<Message>>(emptyList())
    var nextSendResult: AppResult<Message>? = null
    var lastDecisionPath: DecisionPath? = null

    override fun observe(sessionId: String): Flow<List<Message>> = state

    override suspend fun send(
        sessionId: String,
        prompt: String,
        decisionPath: DecisionPath,
    ): AppResult<Message> {
        lastDecisionPath = decisionPath
        nextSendResult?.let { return it }
        val message = Message(
            id = 1L,
            sessionId = sessionId,
            role = MessageRole.USER,
            content = prompt,
            createdAt = 1L,
            pending = decisionPath == DecisionPath.QUEUE_AND_DEFER,
        )
        state.value = state.value + message
        return AppResult.Success(message)
    }

    override suspend fun flushPending(sessionId: String): AppResult<Unit> =
        AppResult.Success(Unit)

    fun seed(messages: List<Message>) { state.value = messages }
}

class FakeSettingsRepository(
    private var settings: AppSettings = AppSettings(
        offlineMode = false,
        apiKeyConfigured = true,
        decisionEngineEnabled = true,
    ),
) : SettingsRepository {
    override fun observe(): Flow<AppSettings> = flowOf(settings)
    override suspend fun saveApiKey(value: String) {
        settings = settings.copy(apiKeyConfigured = value.isNotBlank())
    }
    override suspend fun setOfflineMode(value: Boolean) {
        settings = settings.copy(offlineMode = value)
    }
    override suspend fun getApiKey(): String? =
        if (settings.apiKeyConfigured) "test-key" else null
    override suspend fun current(): AppSettings = settings
}

class FakeChaosRepository(
    private var status: ChaosStatus = ChaosStatus(
        online = true,
        breakerOpen = false,
        cachedMessages = 0,
        pendingMessages = 0,
    ),
) : ChaosRepository {
    override fun observe(): Flow<ChaosStatus> = flowOf(status)
    override suspend fun refresh(): ChaosStatus = status
    fun setStatus(value: ChaosStatus) { status = value }
}

class FakeNormativeRepository(
    private val sources: List<NormSource> = emptyList(),
    private val fragments: List<NormFragment> = emptyList(),
) : NormativeRepository {
    override fun observeSources(): Flow<List<NormSource>> = flowOf(sources)
    override fun observeCurrentFragments(sourceCode: String): Flow<List<NormFragment>> =
        flowOf(fragments.filter {
            it.fragmentKey.contains(sourceCode, ignoreCase = true) || sourceCode.isBlank()
        })
}

class FakeOntologyRepository(
    private val nodes: List<OntologyNode> = emptyList(),
    private val nodeWithFragments: NodeWithNormativeFragments? = null,
) : OntologyRepository {
    override fun observeActiveNodes(nodeType: String?): Flow<List<OntologyNode>> =
        flowOf(nodes.filter { nodeType == null || it.nodeType == nodeType })
    override suspend fun getNodeWithFragments(nodeId: Long): NodeWithNormativeFragments? =
        nodeWithFragments?.takeIf { it.node.id == nodeId }
}

class FakeReactivoRepository(
    private val reactivos: List<Reactivo> = emptyList(),
    private val aggregates: List<ReactivoAggregateModel> = emptyList(),
) : ReactivoRepository {
    override fun observeActiveReactivos(examArea: String?): Flow<List<Reactivo>> =
        flowOf(reactivos.filter { examArea == null || it.examArea == examArea })
    override suspend fun getReactivoAggregate(reactivoId: Long): ReactivoAggregateModel? =
        aggregates.firstOrNull { it.reactivo.id == reactivoId }
    override suspend fun getSimulationReactivos(
        examArea: String?,
        limit: Int,
    ): List<ReactivoAggregateModel> =
        aggregates.filter { examArea == null || it.reactivo.examArea == examArea }.take(limit)
    override suspend fun invalidateByNormVersion(normVersionId: Long, reason: String): Int = 1
}

fun sampleReactivoAggregate(id: Long = 1L): ReactivoAggregateModel = ReactivoAggregateModel(
    reactivo = Reactivo(
        id = id,
        reactivoKey = "r_$id",
        primaryNodeId = 10L,
        stem = "¿Cuál es el plazo correcto?",
        formatType = "DIRECTO",
        examArea = "TECNICO",
        cognitiveLevel = "RECALL",
        status = "ACTIVE",
        sourceMode = "HUMAN",
        createdAt = 1L,
        updatedAt = 1L,
    ),
    options = listOf(
        ReactivoOption(
            id = 1L,
            position = 1,
            label = "A",
            text = "3 días",
            isCorrect = true,
            distractorType = null,
            rationale = null,
        ),
    ),
    metadata = ReactivoMetadata(
        difficulty = 0.4,
        discrimination = 0.2,
        estimatedTimeSec = 30,
        reviewState = "APPROVED",
        reviewerNotes = null,
        commonErrorPattern = null,
        blueprintWeight = 1.0,
        lastReviewedAt = 1L,
    ),
    validity = ReactivoValidity(
        normVersionId = 1L,
        validFrom = 1L,
        validTo = null,
        isCurrent = true,
        invalidationReason = null,
        supersededByReactivoId = null,
    ),
    nodes = listOf(
        OntologyNode(
            id = 10L,
            nodeKey = "plazo",
            nodeType = "PLAZO",
            label = "Plazo legal",
            description = "Plazo aplicable",
            status = "ACTIVE",
            confidence = 1.0,
        ),
    ),
    fragments = listOf(
        NormFragment(
            id = 20L,
            versionId = 1L,
            fragmentKey = "art_1",
            parentFragmentId = null,
            fragmentType = "ARTICLE",
            ordinal = "1",
            heading = "Artículo 1",
            body = "Texto",
            normalizedBody = "texto",
        ),
    ),
)
