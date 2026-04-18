package dev.vskelk.cdf.domain.decision

import com.google.common.truth.Truth.assertThat
import dev.vskelk.cdf.domain.model.DecisionInput
import dev.vskelk.cdf.domain.model.DecisionPath
import org.junit.Test

class DecisionEngineTest {

    private val engine = DecisionEngine()

    private fun input(
        online: Boolean = true,
        breakerOpen: Boolean = false,
        hasApiKey: Boolean = true,
        hasCache: Boolean = false,
        offlineMode: Boolean = false,
        engineEnabled: Boolean = true,
    ) = DecisionInput(
        online = online,
        breakerOpen = breakerOpen,
        hasApiKey = hasApiKey,
        hasCache = hasCache,
        offlineMode = offlineMode,
        decisionEngineEnabled = engineEnabled,
    )

    @Test
    fun returnsMissingKeyWhenNoApiKey() {
        assertThat(engine.decide(input(hasApiKey = false)))
            .isEqualTo(DecisionPath.BLOCK_MISSING_KEY)
    }

    @Test
    fun returnsServeCacheWhenOfflineAndHasCache() {
        assertThat(engine.decide(input(online = false, hasCache = true)))
            .isEqualTo(DecisionPath.SERVE_CACHE)
    }

    @Test
    fun returnsQueueAndDeferWhenOfflineWithoutCache() {
        assertThat(engine.decide(input(online = false, hasCache = false)))
            .isEqualTo(DecisionPath.QUEUE_AND_DEFER)
    }

    @Test
    fun returnsSendRemoteWhenHealthyOnline() {
        assertThat(engine.decide(input(hasCache = true)))
            .isEqualTo(DecisionPath.SEND_REMOTE)
    }

    @Test
    fun returnsServeCacheWhenBreakerOpenAndHasCache() {
        assertThat(engine.decide(input(breakerOpen = true, hasCache = true)))
            .isEqualTo(DecisionPath.SERVE_CACHE)
    }
}
