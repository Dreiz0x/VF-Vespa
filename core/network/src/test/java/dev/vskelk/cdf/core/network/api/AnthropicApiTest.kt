package dev.vskelk.cdf.core.network.api

import com.google.common.truth.Truth.assertThat
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.vskelk.cdf.core.network.dto.AnthropicMessagePayload
import dev.vskelk.cdf.core.network.dto.AnthropicMessagesRequest
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class AnthropicApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: AnthropicApi

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        val json = Json { ignoreUnknownKeys = true; explicitNulls = false }
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(OkHttpClient())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(AnthropicApi::class.java)
    }

    @After
    fun tearDown() { server.shutdown() }

    @Test
    fun createMessageParsesResponse() = runTest {
        server.enqueue(
            MockResponse().setResponseCode(200).setBody(
                """{"id":"msg_1","content":[{"type":"text","text":"respuesta de prueba"}],"stop_reason":"end_turn"}"""
            )
        )
        val response = api.createMessage(
            AnthropicMessagesRequest(
                model = "claude-test",
                maxTokens = 128,
                system = "test",
                messages = listOf(AnthropicMessagePayload(role = "user", content = "hola")),
            )
        )
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()?.id).isEqualTo("msg_1")
        assertThat(response.body()?.content?.firstOrNull()?.text).isEqualTo("respuesta de prueba")
    }
}
