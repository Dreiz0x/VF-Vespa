package dev.vskelk.cdf.feature.chaos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vskelk.cdf.domain.model.ChaosStatus
import dev.vskelk.cdf.domain.usecase.ObserveChaosStatusUseCase
import dev.vskelk.cdf.domain.usecase.RefreshChaosStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChaosUiState(
    val status: ChaosStatus? = null,
    val isRefreshing: Boolean = false,
)

sealed interface ChaosEvent {
    data object Refresh : ChaosEvent
}

@HiltViewModel
class ChaosViewModel @Inject constructor(
    observeChaosStatusUseCase: ObserveChaosStatusUseCase,
    private val refreshChaosStatusUseCase: RefreshChaosStatusUseCase,
) : ViewModel() {

    private val refreshing = MutableStateFlow(false)

    val uiState: StateFlow<ChaosUiState> = combine(
        observeChaosStatusUseCase(),
        refreshing,
    ) { status, isRefreshing ->
        ChaosUiState(status = status, isRefreshing = isRefreshing)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ChaosUiState(),
    )

    fun onEvent(event: ChaosEvent) {
        when (event) {
            ChaosEvent.Refresh -> viewModelScope.launch {
                refreshing.value = true
                refreshChaosStatusUseCase()
                refreshing.value = false
            }
        }
    }
}
