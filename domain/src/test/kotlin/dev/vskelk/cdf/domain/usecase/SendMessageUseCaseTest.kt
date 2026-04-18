package dev.vskelk.cdf.domain.usecase

import com.google.common.truth.Truth.assertThat
import dev.vskelk.cdf.core.common.AppResult
import dev.vskelk.cdf.domain.decision.DecisionEngine
import dev.vskelk.cdf.domain.fakes.FakeChaosRepository
import dev.vskelk.cdf.domain.fakes.FakeConversationRepository
import dev.vskelk.cdf.domain.fakes.FakeSettingsRepository
import dev.vskelk.cdf.domain.model.AppSettings
import dev.vskelk.cdf.domain.model.ChaosStatus
import dev.vskelk.cdf.domain.model.DecisionPath
import dev.vskelk.cdf.domain.model.Message
import dev.vskelk.cdf.domain.model.MessageRole
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SendMessageUseCaseTest {

    @Test
    fun sendsRemoteWhenOnlineAndConfigured() = runTest {
        val conversation = FakeConversationRepository()
        val useCase = buildUseCase(conversation = conversation, online = true, apiKey = true)
        useCase("default", "hola")
        assertThat(conversation.lastDecisionPath).isEqualTo(DecisionPath.SEND_REMOTE)
    }

    @Test
    fun queuesWhenOfflineWithoutCache() = runTest {
        val conversation = FakeConversationRepository()
        val useCase = buildUseCase(conversation = conversation, online = false, apiKey = true)
        useCase("default", "hola")
        assertThat(conversation.lastDecisionPath).isEqualTo(DecisionPath.QUEUE_AND_DEFER)
    }

    @Test
    fun blocksWhenMissingApiKey() = runTest {
        val conversation = FakeConversationRepository()
        val useCase = buildUseCase(conversation = conversation, online = true, apiKey = false)
        val result = useCase("default", "hola")
        assertThat(result is AppResult.Failure).isTrue()
        assertThat(conversation.lastDecisionPath).isNull()
    }

    @Test
    fun servesCacheWhenOfflineAndCacheExists() = runTest {
        val conversation = FakeConversationRepository().apply {
            seed(
                listOf(
                    Message(
                        id = 1L,
                        sessionId = "default",
                        role = MessageRole.ASSISTANT,
                        content = "respuesta previa",
                        createdAt = 1L,
                    ),
                ),
            )
        }
        val useCase = buildUseCase(conversation = conversation, online = false, apiKey = true)
        useCase("default", "hola")
        assertThat(conversation.lastDecisionPath).isEqualTo(DecisionPath.SERVE_CACHE)
    }

    private fun buildUseCase(
        conversation: FakeConversationRepository,
        online: Boolean,
        apiKey: Boolean,
    ) = SendMessageUseCase(
        conversationRepository = conversation,
        settingsRepository = FakeSettingsRepository(
            AppSettings(
                offlineMode = false,
                apiKeyConfigured = apiKey,
                decisionEngineEnabled = true,
            ),
        ),
        chaosRepository = FakeChaosRepository(
            ChaosStatus(
                online = online,
                breakerOpen = false,
                cachedMessages = 0,
                pendingMessages = 0,
            ),
        ),
        decisionEngine = DecisionEngine(),
    )
}
