package dev.vskelk.cdf.domain.decision

import dev.vskelk.cdf.domain.model.DecisionInput
import dev.vskelk.cdf.domain.model.DecisionPath
import javax.inject.Inject

class DecisionEngine @Inject constructor() {

    fun decide(input: DecisionInput): DecisionPath {
        if (!input.hasApiKey) return DecisionPath.BLOCK_MISSING_KEY
        return when {
            !input.decisionEngineEnabled -> remoteOrDefer(input.online)
            input.offlineMode -> cacheOrDefer(input.hasCache)
            input.breakerOpen -> cacheOrDefer(input.hasCache)
            !input.online -> cacheOrDefer(input.hasCache)
            else -> DecisionPath.SEND_REMOTE
        }
    }

    private fun cacheOrDefer(hasCache: Boolean): DecisionPath =
        if (hasCache) DecisionPath.SERVE_CACHE else DecisionPath.QUEUE_AND_DEFER

    private fun remoteOrDefer(online: Boolean): DecisionPath =
        if (online) DecisionPath.SEND_REMOTE else DecisionPath.QUEUE_AND_DEFER
}
