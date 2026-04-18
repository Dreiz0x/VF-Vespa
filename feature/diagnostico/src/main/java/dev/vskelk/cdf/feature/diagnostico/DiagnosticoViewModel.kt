package dev.vskelk.cdf.feature.diagnostico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vskelk.cdf.domain.model.ReactivoDiagnostic
import dev.vskelk.cdf.domain.usecase.ObserveReactivoDiagnosticsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class DiagnosticoUiState(val diagnostics: List<ReactivoDiagnostic> = emptyList())

@HiltViewModel
class DiagnosticoViewModel @Inject constructor(
    observeReactivoDiagnosticsUseCase: ObserveReactivoDiagnosticsUseCase,
) : ViewModel() {

    val uiState: StateFlow<DiagnosticoUiState> = observeReactivoDiagnosticsUseCase()
        .map { DiagnosticoUiState(diagnostics = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DiagnosticoUiState(),
        )
}
