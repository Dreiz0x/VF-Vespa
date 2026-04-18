package dev.vskelk.cdf.core.datastore.datasource

import androidx.datastore.core.DataStore
import dev.vskelk.cdf.core.datastore.ProviderProto
import dev.vskelk.cdf.core.datastore.UserPreferences
import dev.vskelk.cdf.core.datastore.crypto.CipherService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

enum class LlmProvider {
    UNSPECIFIED, ANTHROPIC, GEMINI, OPENAI
}

data class PreferencesData(
    val offlineMode: Boolean,
    val decisionEngineEnabled: Boolean,
    val activeProvider: LlmProvider,
    val apiKeys: Map<String, String>,
    val seedVersionApplied: String
)

@Singleton
class PreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<UserPreferences>,
    private val cipherService: CipherService,
) {
    fun observe(): Flow<UserPreferences> = dataStore.data

    fun observeData(): Flow<PreferencesData> = dataStore.data.map { proto ->
        PreferencesData(
            offlineMode = proto.offlineMode,
            decisionEngineEnabled = proto.decisionEngineEnabled,
            activeProvider = when (proto.activeProvider) {
                ProviderProto.PROVIDER_ANTHROPIC -> LlmProvider.ANTHROPIC
                ProviderProto.PROVIDER_GEMINI -> LlmProvider.GEMINI
                ProviderProto.PROVIDER_OPENAI -> LlmProvider.OPENAI
                else -> LlmProvider.UNSPECIFIED
            },
            apiKeys = proto.apiKeysMap,
            seedVersionApplied = proto.seedVersionApplied
        )
    }

    fun observeOfflineMode(): Flow<Boolean> = dataStore.data.map { it.offlineMode }

    fun observeDecisionEngineEnabled(): Flow<Boolean> =
        dataStore.data.map { it.decisionEngineEnabled }

    fun observeActiveProvider(): Flow<LlmProvider> = dataStore.data.map {
        when (it.activeProvider) {
            ProviderProto.PROVIDER_ANTHROPIC -> LlmProvider.ANTHROPIC
            ProviderProto.PROVIDER_GEMINI -> LlmProvider.GEMINI
            ProviderProto.PROVIDER_OPENAI -> LlmProvider.OPENAI
            else -> LlmProvider.UNSPECIFIED
        }
    }

    fun observeSeedVersionApplied(): Flow<String> = dataStore.data.map { it.seedVersionApplied }

    suspend fun saveApiKey(provider: LlmProvider, value: String) {
        val providerKey = provider.name.lowercase()
        dataStore.updateData { current ->
            current.toBuilder()
                .putApiKeys(providerKey, cipherService.encrypt(value))
                .build()
        }
    }

    suspend fun getApiKey(provider: LlmProvider): String? {
        val providerKey = provider.name.lowercase()
        val preferences = dataStore.data.first()
        val ciphertext = preferences.apiKeysMap[providerKey]
        return ciphertext
            ?.takeIf { it.isNotBlank() }
            ?.let(cipherService::decrypt)
    }

    suspend fun setActiveProvider(provider: LlmProvider) {
        val proto = when (provider) {
            LlmProvider.ANTHROPIC -> ProviderProto.PROVIDER_ANTHROPIC
            LlmProvider.GEMINI -> ProviderProto.PROVIDER_GEMINI
            LlmProvider.OPENAI -> ProviderProto.PROVIDER_OPENAI
            else -> ProviderProto.PROVIDER_UNSPECIFIED
        }
        dataStore.updateData { it.toBuilder().setActiveProvider(proto).build() }
    }

    suspend fun setOfflineMode(value: Boolean) {
        dataStore.updateData { current ->
            current.toBuilder().setOfflineMode(value).build()
        }
    }

    suspend fun setSeedVersionApplied(version: String) {
        dataStore.updateData { current ->
            current.toBuilder().setSeedVersionApplied(version).build()
        }
    }
}
