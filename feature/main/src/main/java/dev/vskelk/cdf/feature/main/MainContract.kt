package dev.vskelk.cdf.feature.main

import dev.vskelk.cdf.domain.model.AppSettings
import dev.vskelk.cdf.domain.model.Message

data class MainUiState(
    val messages: List<Message> = emptyList(),
    val input: String = "",
    val settings: AppSettings? = null,
    val isSending: Boolean = false,
)

sealed interface MainEvent {
    data class InputChanged(val value: String) : MainEvent
    data object SendClicked : MainEvent
    data class OfflineModeChanged(val value: Boolean) : MainEvent
    data class ApiKeyChanged(val value: String) : MainEvent
}

sealed interface MainEffect {
    data class ShowError(val message: String) : MainEffect
}
