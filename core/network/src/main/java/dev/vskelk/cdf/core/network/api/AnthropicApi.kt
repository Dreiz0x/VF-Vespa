package dev.vskelk.cdf.core.network.api

import dev.vskelk.cdf.core.network.dto.AnthropicMessagesRequest
import dev.vskelk.cdf.core.network.dto.AnthropicMessagesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AnthropicApi {
    @Headers("anthropic-version: 2023-06-01", "content-type: application/json")
    @POST("v1/messages")
    suspend fun createMessage(
        @Header("x-api-key") apiKey: String,
        @Body request: AnthropicMessagesRequest,
    ): Response<AnthropicMessagesResponse>
}
