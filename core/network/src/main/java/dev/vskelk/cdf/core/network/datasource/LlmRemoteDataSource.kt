package dev.vskelk.cdf.core.network.datasource

interface LlmRemoteDataSource {
    suspend fun sendMessage(
        apiKey: String,
        prompt: String,
        history: List<Pair<String, String>> = emptyList()
    ): Result<String>
}
