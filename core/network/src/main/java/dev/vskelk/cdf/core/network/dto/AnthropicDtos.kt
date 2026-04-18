package dev.vskelk.cdf.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnthropicMessagesRequest(
    @SerialName("model") val model: String,
    @SerialName("max_tokens") val maxTokens: Int,
    @SerialName("system") val system: String,
    @SerialName("messages") val messages: List<AnthropicMessagePayload>,
)

@Serializable
data class AnthropicMessagePayload(
    @SerialName("role") val role: String,
    @SerialName("content") val content: String,
)

@Serializable
data class AnthropicMessagesResponse(
    @SerialName("id") val id: String,
    @SerialName("content") val content: List<AnthropicContentBlock>,
    @SerialName("stop_reason") val stopReason: String? = null,
)

@Serializable
data class AnthropicContentBlock(
    @SerialName("type") val type: String,
    @SerialName("text") val text: String? = null,
)
