package dev.vskelk.cdf.core.network.interceptor

import dev.vskelk.cdf.core.network.resilience.CircuitBreaker
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.pow

@Singleton
class RetryBackoffInterceptor @Inject constructor(
    private val circuitBreaker: CircuitBreaker,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        if (circuitBreaker.isOpen()) buildCircuitOpenResponse(chain) else executeWithRetry(chain)

    private fun buildCircuitOpenResponse(chain: Interceptor.Chain): Response =
        Response.Builder()
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .code(503)
            .message("Circuit breaker open")
            .body("""{"error":"circuit_open"}""".toResponseBody(null))
            .build()

    private fun executeWithRetry(chain: Interceptor.Chain): Response {
        var attempt = 0
        var lastResponse: Response? = null
        var lastError: IOException? = null

        while (attempt < MAX_RETRIES) {
            val result = attemptRequest(chain, lastResponse)
            lastResponse = result.response
            lastError = result.error ?: lastError
            result.response?.let { response ->
                if (response.isSuccessful || response.code in NON_RETRYABLE_CODES) {
                    val success = response.isSuccessful
                    if (success) circuitBreaker.recordSuccess() else circuitBreaker.recordFailure()
                    return response
                }
            }
            attempt++
            if (attempt < MAX_RETRIES) Thread.sleep((BASE_DELAY_MS * 2.0.pow(attempt)).toLong())
        }
        circuitBreaker.recordFailure()
        return lastResponse ?: throw lastError ?: IOException("Retries exhausted")
    }

    private data class AttemptResult(val response: Response?, val error: IOException?)

    private fun attemptRequest(chain: Interceptor.Chain, previous: Response?): AttemptResult {
        previous?.close()
        return runCatching { chain.proceed(chain.request()) }.fold(
            onSuccess = { AttemptResult(it, null) },
            onFailure = { e ->
                if (e is IOException) {
                    circuitBreaker.recordFailure()
                    AttemptResult(null, e)
                } else {
                    AttemptResult(null, null)
                }
            },
        )
    }

    private companion object {
        const val MAX_RETRIES = 3
        const val BASE_DELAY_MS = 500L
        val NON_RETRYABLE_CODES = setOf(400, 401, 403, 404, 422)
    }
}
