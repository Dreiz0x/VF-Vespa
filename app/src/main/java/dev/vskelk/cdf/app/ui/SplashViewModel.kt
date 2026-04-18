package dev.vskelk.cdf.app.ui

// ARCHIVO NUEVO en app/ui
// SplashViewModel — invoca InitializeAppUseCase al arrancar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vskelk.cdf.domain.model.BootstrapState
import dev.vskelk.cdf.domain.repository.BootstrapRepository
import dev.vskelk.cdf.domain.usecase.InitializeAppUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val initializeApp: InitializeAppUseCase,
    bootstrapRepository: BootstrapRepository,
) : ViewModel() {

    val bootstrapState: StateFlow<BootstrapState> = bootstrapRepository
        .observe()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BootstrapState.Checking,
        )

    init {
        viewModelScope.launch { initializeApp() }
    }

    fun retry() {
        viewModelScope.launch { initializeApp() }
    }
}
