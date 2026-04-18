package dev.vskelk.cdf.domain.repository

import dev.vskelk.cdf.core.common.AppResult
import dev.vskelk.cdf.domain.model.AppSettings
import dev.vskelk.cdf.domain.model.ChaosStatus
import dev.vskelk.cdf.domain.model.DecisionPath
import dev.vskelk.cdf.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ConversationRepository {
    fun observe(sessionId: String): Flow<List<Message>>
    suspend fun send(sessionId: String, prompt: String, decisionPath: DecisionPath): AppResult<Message>
    suspend fun flushPending(sessionId: String): AppResult<Unit>
}

interface SettingsRepository {
    fun observe(): Flow<AppSettings>
    suspend fun saveApiKey(value: String)
    suspend fun setOfflineMode(value: Boolean)
    suspend fun getApiKey(): String?
    suspend fun current(): AppSettings
}

interface ChaosRepository {
    fun observe(): Flow<ChaosStatus>
    suspend fun refresh(): ChaosStatus
}
