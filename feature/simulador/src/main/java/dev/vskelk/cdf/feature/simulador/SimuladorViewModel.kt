package dev.vskelk.cdf.feature.simulador

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vskelk.cdf.domain.model.ReactivoAggregateModel
import dev.vskelk.cdf.domain.usecase.GetSimulationReactivosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SimuladorUiState(
    val examArea: String? = "TECNICO",
    val limit: Int = 10,
    val items: List<ReactivoAggregateModel> = emptyList(),
    val isLoading: Boolean = false,
)

sealed interface SimuladorEvent {
    data class AreaChanged(val value: String?) : SimuladorEvent
    data class LimitChanged(val value: Int) : SimuladorEvent
    data object Load : SimuladorEvent
}

@HiltViewModel
class SimuladorViewModel @Inject constructor(
    private val getSimulationReactivosUseCase: GetSimulationReactivosUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SimuladorUiState())
    val uiState: StateFlow<SimuladorUiState> = _uiState

    init { onEvent(SimuladorEvent.Load) }

    fun onEvent(event: SimuladorEvent) {
        when (event) {
            is SimuladorEvent.AreaChanged -> _uiState.value = _uiState.value.copy(examArea = event.value)
            is SimuladorEvent.LimitChanged -> _uiState.value = _uiState.value.copy(limit = event.value.coerceIn(1, 25))
            SimuladorEvent.Load -> load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val items = getSimulationReactivosUseCase(_uiState.value.examArea, _uiState.value.limit)
            _uiState.value = _uiState.value.copy(items = items, isLoading = false)
        }
    }
}
