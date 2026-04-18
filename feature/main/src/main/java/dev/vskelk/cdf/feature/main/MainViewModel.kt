package dev.vskelk.cdf.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vskelk.cdf.core.common.AppResult
import dev.vskelk.cdf.domain.usecase.ObserveConversationUseCase
import dev.vskelk.cdf.domain.usecase.ObserveSettingsUseCase
import dev.vskelk.cdf.domain.usecase.SaveApiKeyUseCase
import dev.vskelk.cdf.domain.usecase.SendMessageUseCase
import dev.vskelk.cdf.domain.usecase.ToggleOfflineModeUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    observeConversationUseCase: ObserveConversationUseCase,
    observeSettingsUseCase: ObserveSettingsUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val saveApiKeyUseCase: SaveApiKeyUseCase,
    private val toggleOfflineModeUseCase: ToggleOfflineModeUseCase,
) : ViewModel() {

    private val sessionId = "default"
    private val input = MutableStateFlow("")
    private val isSending = MutableStateFlow(false)
    val effects = MutableSharedFlow<MainEffect>()

    val uiState: StateFlow<MainUiState> = combine(
        observeConversationUseCase(sessionId),
        observeSettingsUseCase(),
        input,
        isSending,
    ) { messages, settings, currentInput, sending ->
        MainUiState(messages = messages, input = currentInput, settings = settings, isSending = sending)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainUiState(),
    )

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.InputChanged -> input.value = event.value
            is MainEvent.OfflineModeChanged -> viewModelScope.launch { toggleOfflineModeUseCase(event.value) }
            is MainEvent.ApiKeyChanged -> viewModelScope.launch { saveApiKeyUseCase(event.value) }
            MainEvent.SendClicked -> send()
        }
    }

    private fun send() {
        val prompt = input.value.trim()
        if (prompt.isBlank() || isSending.value) return
        viewModelScope.launch {
            isSending.value = true
            when (val result = sendMessageUseCase(sessionId, prompt)) {
                is AppResult.Success -> input.value = ""
                is AppResult.Failure -> effects.emit(MainEffect.ShowError(result.error.toString()))
            }
            isSending.value = false
        }
    }
}
