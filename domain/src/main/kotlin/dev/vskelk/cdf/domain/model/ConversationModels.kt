package dev.vskelk.cdf.domain.model

enum class MessageRole { USER, ASSISTANT, SYSTEM }

data class Message(
    val id: Long = 0,
    val sessionId: String,
    val role: MessageRole,
    val content: String,
    val createdAt: Long,
    val pending: Boolean = false,
    val failed: Boolean = false,
)

data class AppSettings(
    val offlineMode: Boolean,
    val apiKeyConfigured: Boolean,
    val decisionEngineEnabled: Boolean,
)

data class ChaosStatus(
    val online: Boolean,
    val breakerOpen: Boolean,
    val cachedMessages: Int,
    val pendingMessages: Int,
)

enum class DecisionPath {
    BLOCK_MISSING_KEY,
    SERVE_CACHE,
    SEND_REMOTE,
    QUEUE_AND_DEFER,
}

data class DecisionInput(
    val online: Boolean,
    val breakerOpen: Boolean,
    val hasApiKey: Boolean,
    val hasCache: Boolean,
    val offlineMode: Boolean,
    val decisionEngineEnabled: Boolean,
)
