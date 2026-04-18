package dev.vskelk.cdf.feature.entrevista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vskelk.cdf.domain.model.OntologyNode
import dev.vskelk.cdf.domain.usecase.ObserveInterviewNodesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class EntrevistaUiState(val competencies: List<OntologyNode> = emptyList())

@HiltViewModel
class EntrevistaViewModel @Inject constructor(
    observeInterviewNodesUseCase: ObserveInterviewNodesUseCase,
) : ViewModel() {

    val uiState: StateFlow<EntrevistaUiState> = observeInterviewNodesUseCase()
        .map { nodes -> EntrevistaUiState(competencies = nodes.sortedBy { it.label }) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = EntrevistaUiState(),
        )
}
