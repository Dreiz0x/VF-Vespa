package dev.vskelk.cdf.data.repository.repository

import dev.vskelk.cdf.core.common.AppError
import dev.vskelk.cdf.core.common.AppResult
import dev.vskelk.cdf.core.common.NetworkMonitor
import dev.vskelk.cdf.core.database.dao.MessageDao
import dev.vskelk.cdf.core.database.dao.NormativeDao
import dev.vskelk.cdf.core.database.dao.OntologyDao
import dev.vskelk.cdf.core.database.dao.ReactivoDao
import dev.vskelk.cdf.core.database.dao.SessionDao
import dev.vskelk.cdf.core.database.entity.MessageEntity
import dev.vskelk.cdf.core.database.entity.SessionEntity
import dev.vskelk.cdf.core.datastore.datasource.LlmProvider
import dev.vskelk.cdf.core.datastore.datasource.PreferencesDataSource
import dev.vskelk.cdf.core.network.datasource.LlmRemoteDataSource
import dev.vskelk.cdf.core.network.resilience.CircuitBreaker
import dev.vskelk.cdf.data.repository.mapper.toDomain
import dev.vskelk.cdf.data.repository.mapper.toEntity
import dev.vskelk.cdf.domain.model.AppSettings
import dev.vskelk.cdf.domain.model.ChaosStatus
import dev.vskelk.cdf.domain.model.DecisionPath
import dev.vskelk.cdf.domain.model.Message
import dev.vskelk.cdf.domain.model.MessageRole
import dev.vskelk.cdf.domain.repository.ChaosRepository
import dev.vskelk.cdf.domain.repository.ConversationRepository
import dev.vskelk.cdf.domain.repository.NormativeRepository
import dev.vskelk.cdf.domain.repository.OntologyRepository
import dev.vskelk.cdf.domain.repository.ReactivoRepository
import dev.vskelk.cdf.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao,
    private val sessionDao: SessionDao,
    private val preferences: PreferencesDataSource,
    private val llmRemoteDataSource: LlmRemoteDataSource,
) : ConversationRepository {

    override fun observe(sessionId: String): Flow<List<Message>> =
        messageDao.observeBySession(sessionId).map { it.map { e -> e.toDomain() } }

    override suspend fun send(
        sessionId: String,
        prompt: String,
        decisionPath: DecisionPath,
    ): AppResult<Message> {
        upsertSession(sessionId)
        return when (decisionPath) {
            DecisionPath.BLOCK_MISSING_KEY -> AppResult.Failure(AppError.MissingApiKey)
            DecisionPath.QUEUE_AND_DEFER ->
                AppResult.Success(insertMessage(sessionId, MessageRole.USER, prompt, true, false))
            DecisionPath.SERVE_CACHE -> serveFromCache(sessionId, prompt)
            DecisionPath.SEND_REMOTE -> sendRemote(sessionId, prompt)
        }
    }

    private suspend fun serveFromCache(sessionId: String, prompt: String): AppResult<Message> {
        val user = insertMessage(sessionId, MessageRole.USER, prompt, false, false)
        val cached = messageDao.latest(sessionId, 50)
            .firstOrNull { it.role == MessageRole.ASSISTANT.name && !it.pending }
        val content = cached?.content ?: "Modo caché activo."
        val assistant = insertMessage(sessionId, MessageRole.ASSISTANT, content, false, false)
        return AppResult.Success(assistant.copy(sessionId = user.sessionId))
    }

    private suspend fun sendRemote(sessionId: String, prompt: String): AppResult<Message> {
        val user = insertMessage(sessionId, MessageRole.USER, prompt, false, false)
        val history = buildHistory(sessionId)
        
        val provider = preferences.observeActiveProvider().first()
        val apiKey = preferences.getApiKey(provider) ?: return AppResult.Failure(AppError.MissingApiKey)

        return llmRemoteDataSource.sendMessage(apiKey = apiKey, prompt = prompt, history = history)
            .fold(
                onSuccess = { response ->
                    AppResult.Success(
                        insertMessage(sessionId, MessageRole.ASSISTANT, response, false, false),
                    )
                },
                onFailure = { throwable ->
                    messageDao.upsert(user.toEntity().copy(pending = true, failed = true))
                    AppResult.Failure(AppError.Unknown(throwable))
                },
            )
    }

    override suspend fun flushPending(sessionId: String): AppResult<Unit> {
        upsertSession(sessionId)
        val pending = messageDao.pending()
        return pending
            .map { syncPendingMessage(it) }
            .firstOrNull { it is AppResult.Failure }
            ?: AppResult.Success(Unit)
    }

    private suspend fun syncPendingMessage(pending: MessageEntity): AppResult<Unit> {
        val history = messageDao.latest(pending.sessionId, HISTORY_LIMIT).asReversed()
            .filter { !it.pending || it.id == pending.id }
            .map { e -> roleLabel(e.role) to e.content }
            
        val provider = preferences.observeActiveProvider().first()
        val apiKey = preferences.getApiKey(provider) ?: return AppResult.Failure(AppError.MissingApiKey)

        return llmRemoteDataSource.sendMessage(apiKey = apiKey, prompt = pending.content, history = history)
            .fold(
                onSuccess = { response ->
                    messageDao.markState(pending.id, pending = false, failed = false)
                    insertMessage(pending.sessionId, MessageRole.ASSISTANT, response, false, false)
                    AppResult.Success(Unit)
                },
                onFailure = {
                    messageDao.markState(pending.id, pending = true, failed = true)
                    AppResult.Failure(AppError.Unknown(it))
                },
            )
    }

    private fun roleLabel(role: String): String = when (role) {
        MessageRole.USER.name -> "user"
        MessageRole.ASSISTANT.name -> "assistant"
        else -> "system"
    }

    private suspend fun buildHistory(sessionId: String) =
        messageDao.latest(sessionId, HISTORY_LIMIT).asReversed()
            .filterNot { it.pending }
            .map { e -> roleLabel(e.role) to e.content }

    private suspend fun upsertSession(sessionId: String) {
        sessionDao.upsert(
            SessionEntity(
                id = sessionId,
                title = "Session $sessionId",
                updatedAt = System.currentTimeMillis(),
            ),
        )
    }

    private suspend fun insertMessage(
        sessionId: String,
        role: MessageRole,
        content: String,
        pending: Boolean,
        failed: Boolean,
    ): Message {
        val createdAt = System.currentTimeMillis()
        val id = messageDao.upsert(
            MessageEntity(
                sessionId = sessionId,
                role = role.name,
                content = content,
                createdAt = createdAt,
                pending = pending,
                failed = failed,
            ),
        )
        return Message(
            id = id,
            sessionId = sessionId,
            role = role,
            content = content,
            createdAt = createdAt,
            pending = pending,
            failed = failed,
        )
    }

    private companion object { const val HISTORY_LIMIT = 20 }
}

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val preferences: PreferencesDataSource,
) : SettingsRepository {
    override fun observe(): Flow<AppSettings> = preferences.observeData().map { data ->
        AppSettings(
            offlineMode = data.offlineMode,
            apiKeyConfigured = data.apiKeys.isNotEmpty(),
            decisionEngineEnabled = data.decisionEngineEnabled,
        )
    }
    override suspend fun saveApiKey(value: String) = preferences.saveApiKey(LlmProvider.ANTHROPIC, value)
    override suspend fun setOfflineMode(value: Boolean) = preferences.setOfflineMode(value)
    override suspend fun getApiKey(): String? = preferences.getApiKey(LlmProvider.ANTHROPIC)
    override suspend fun current(): AppSettings = observe().first()
}

@Singleton
class ChaosRepositoryImpl @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val messageDao: MessageDao,
    private val circuitBreaker: CircuitBreaker,
) : ChaosRepository {
    override fun observe(): Flow<ChaosStatus> = combine(
        networkMonitor.observe(),
        messageDao.observeTotalCount(),
        messageDao.observePendingCount(),
    ) { online, total, pending ->
        ChaosStatus(
            online = online,
            breakerOpen = circuitBreaker.isOpen(),
            cachedMessages = total,
            pendingMessages = pending,
        )
    }
    override suspend fun refresh(): ChaosStatus = ChaosStatus(
        online = networkMonitor.isOnline(),
        breakerOpen = circuitBreaker.isOpen(),
        cachedMessages = messageDao.observeTotalCount().first(),
        pendingMessages = messageDao.observePendingCount().first(),
    )
}

@Singleton
class NormativeRepositoryImpl @Inject constructor(private val dao: NormativeDao) :
    NormativeRepository {
    override fun observeSources() = dao.observeSources().map { it.map { e -> e.toDomain() } }
    override fun observeCurrentFragments(sourceCode: String) =
        dao.observeCurrentFragmentsBySource(sourceCode).map { it.map { e -> e.toDomain() } }
}

@Singleton
class OntologyRepositoryImpl @Inject constructor(private val dao: OntologyDao) :
    OntologyRepository {
    override fun observeActiveNodes(nodeType: String?) = dao.observeActiveNodes().map { list ->
        list.filter { nodeType == null || it.nodeType == nodeType }.map { it.toDomain() }
    }
    override suspend fun getNodeWithFragments(nodeId: Long) =
        dao.getNodeWithFragments(nodeId)?.toDomain()
}

@Singleton
class ReactivoRepositoryImpl @Inject constructor(private val dao: ReactivoDao) :
    ReactivoRepository {
    override fun observeActiveReactivos(examArea: String?) =
        dao.observeActiveReactivos(examArea).map { it.map { a -> a.reactivo.toDomain() } }
    override suspend fun getReactivoAggregate(reactivoId: Long) =
        dao.getReactivoAggregate(reactivoId)?.toDomain()
    override suspend fun getSimulationReactivos(examArea: String?, limit: Int) =
        dao.getRandomActiveReactivoAggregates(examArea, limit).map { it.toDomain() }
    override suspend fun invalidateByNormVersion(normVersionId: Long, reason: String): Int =
        dao.invalidateByNormVersion(normVersionId, reason, System.currentTimeMillis())
}
