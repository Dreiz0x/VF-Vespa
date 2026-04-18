package dev.vskelk.cdf.core.network.datasource

import dev.vskelk.cdf.core.network.api.AnthropicApi
import dev.vskelk.cdf.core.network.dto.AnthropicMessagePayload
import dev.vskelk.cdf.core.network.dto.AnthropicMessagesRequest
import javax.inject.Inject

private const val SYSTEM_PROMPT =
    "Eres Vespa, un asistente experto en normativa electoral mexicana " +
        "y preparación para el Servicio Profesional Electoral Nacional. " +
        "Responde de forma precisa, trazable y con fundamento normativo."

class AnthropicRemoteDataSource @Inject constructor(
    private val api: AnthropicApi,
) : LlmRemoteDataSource {

    override suspend fun sendMessage(
        apiKey: String,
        prompt: String,
        history: List<Pair<String, String>>
    ): Result<String> = runCatching {
        val request = AnthropicMessagesRequest(
            model = "claude-sonnet-4-20250514",
            maxTokens = 1024,
            system = SYSTEM_PROMPT,
            messages = history.map { AnthropicMessagePayload(it.first, it.second) } +
                AnthropicMessagePayload("user", prompt),
        )
        // Inyectamos la API Key directamente en el header para evitar runBlocking en el interceptor
        val response = api.createMessage(apiKey = apiKey, request = request)
        if (!response.isSuccessful) {
            error("HTTP ${response.code()} ${response.errorBody()?.string()}")
        }
        response.body()?.content?.firstOrNull { it.type == "text" }?.text.orEmpty()
    }
}
