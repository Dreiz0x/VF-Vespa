package dev.vskelk.cdf.core.network.resilience

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CircuitBreaker @Inject constructor() {

    private val failures = AtomicInteger(0)
    private val openedAt = AtomicLong(0)
    private val threshold = 3
    private val resetTimeoutMs = 30_000L

    fun isOpen(now: Long = System.currentTimeMillis()): Boolean {
        val overThreshold = failures.get() >= threshold
        val timedOut = now - openedAt.get() >= resetTimeoutMs
        if (overThreshold && timedOut) reset()
        return overThreshold && !timedOut
    }

    fun recordSuccess() = reset()

    fun recordFailure(now: Long = System.currentTimeMillis()) {
        if (failures.incrementAndGet() == threshold) openedAt.set(now)
    }

    private fun reset() {
        failures.set(0)
        openedAt.set(0)
    }
}
