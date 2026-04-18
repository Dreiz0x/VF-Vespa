package dev.vskelk.cdf.domain.usecase

import dev.vskelk.cdf.core.common.AppResult
import dev.vskelk.cdf.domain.decision.DecisionEngine
import dev.vskelk.cdf.domain.model.DecisionInput
import dev.vskelk.cdf.domain.model.Message
import dev.vskelk.cdf.domain.repository.ChaosRepository
import dev.vskelk.cdf.domain.repository.ConversationRepository
import dev.vskelk.cdf.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ObserveConversationUseCase @Inject constructor(
    private val repository: ConversationRepository,
) {
    operator fun invoke(sessionId: String) = repository.observe(sessionId)
}

class SendMessageUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val settingsRepository: SettingsRepository,
    private val chaosRepository: ChaosRepository,
    private val decisionEngine: DecisionEngine,
) {
    suspend operator fun invoke(sessionId: String, prompt: String): AppResult<Message> {
        val settings = settingsRepository.current()
        val chaos = chaosRepository.refresh()
        val hasCache = conversationRepository.observe(sessionId).first().isNotEmpty()
        val decision = decisionEngine.decide(
            DecisionInput(
                online = chaos.online,
                breakerOpen = chaos.breakerOpen,
                hasApiKey = settings.apiKeyConfigured,
                hasCache = hasCache,
                offlineMode = settings.offlineMode,
                decisionEngineEnabled = settings.decisionEngineEnabled,
            )
        )
        return conversationRepository.send(sessionId, prompt, decision)
    }
}

class SyncPendingMessagesUseCase @Inject constructor(
    private val repository: ConversationRepository,
) {
    suspend operator fun invoke(sessionId: String) = repository.flushPending(sessionId)
}

class SaveApiKeyUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke(value: String) = repository.saveApiKey(value)
}

class ObserveSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    operator fun invoke() = repository.observe()
}

class ToggleOfflineModeUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke(value: Boolean) = repository.setOfflineMode(value)
}

class ObserveChaosStatusUseCase @Inject constructor(
    private val repository: ChaosRepository,
) {
    operator fun invoke() = repository.observe()
}

class RefreshChaosStatusUseCase @Inject constructor(
    private val repository: ChaosRepository,
) {
    suspend operator fun invoke() = repository.refresh()
}
