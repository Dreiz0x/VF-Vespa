package dev.vskelk.cdf.feature.investigador

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.vskelk.cdf.domain.model.ResultadoInvestigacion
import dev.vskelk.cdf.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─── InvestigadorViewModel ───────────────────────────────────────────────────

data class InvestigadorUiState(
    val tema: String = "",
    val isBusy: Boolean = false,
    val statusMessage: String = "",
    val ultimoResultado: ResultadoInvestigacion? = null,
    val pendientesCount: Int = 0,
    val error: String? = null,
)

sealed interface InvestigadorEvent {
    data class TemaChanged(val value: String) : InvestigadorEvent
    data object InvestigarClicked : InvestigadorEvent
    data class DocumentoSeleccionado(val uri: String) : InvestigadorEvent
}

@HiltViewModel
class InvestigadorViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val investigarTema: InvestigarTemaUseCase,
    private val ingerirDocumento: IngerirDocumentoUseCase,
    private val observarCuarentena: ObservarCuarentenaUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(InvestigadorUiState())
    val uiState: StateFlow<InvestigadorUiState> = _uiState

    init {
        viewModelScope.launch {
            observarCuarentena().collect { lista ->
                _uiState.value = _uiState.value.copy(pendientesCount = lista.size)
            }
        }
    }

    fun onEvent(event: InvestigadorEvent) {
        when (event) {
            is InvestigadorEvent.TemaChanged ->
                _uiState.value = _uiState.value.copy(tema = event.value, error = null)

            InvestigadorEvent.InvestigarClicked -> investigar()

            is InvestigadorEvent.DocumentoSeleccionado -> ingerirDoc(event.uri)
        }
    }

    private fun investigar() {
        val tema = _uiState.value.tema.trim()
        if (tema.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isBusy = true,
                statusMessage = "Paso 1: Consultando conocimiento existente...",
                error = null,
            )
            try {
                _uiState.value = _uiState.value.copy(statusMessage = "Paso 2: Formulando consulta estructurada...")
                val resultado = investigarTema(tema)

                _uiState.value = _uiState.value.copy(statusMessage = "Paso 3: Validando contra base de conocimiento...")
                // La validación cruzada ocurre dentro del repositorio

                _uiState.value = _uiState.value.copy(
                    isBusy = false,
                    ultimoResultado = resultado,
                    statusMessage = "",
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isBusy = false,
                    statusMessage = "",
                    error = e.message ?: "Error al investigar",
                )
            }
        }
    }

    private fun ingerirDoc(uriString: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isBusy = true,
                statusMessage = "Extrayendo texto del documento...",
                error = null,
            )
            try {
                val uri = Uri.parse(uriString)
                val texto = context.contentResolver.openInputStream(uri)
                    ?.bufferedReader()?.readText() ?: ""
                val fuente = uri.lastPathSegment ?: "documento_usuario"

                _uiState.value = _uiState.value.copy(statusMessage = "Estructurando fragmentos...")
                val resultado = ingerirDocumento(texto, fuente)

                _uiState.value = _uiState.value.copy(
                    isBusy = false,
                    ultimoResultado = resultado,
                    statusMessage = "",
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isBusy = false,
                    statusMessage = "",
                    error = e.message ?: "Error al leer documento",
                )
            }
        }
    }
}

// ─── CuarentenaViewModel ─────────────────────────────────────────────────────

data class CuarentenaUiState(
    val fragmentos: List<dev.vskelk.cdf.domain.model.FragmentoCuarentena> = emptyList(),
)

sealed interface CuarentenaEvent {
    data class Aprobar(val id: Long) : CuarentenaEvent
    data class Rechazar(val id: Long) : CuarentenaEvent
}

@HiltViewModel
class CuarentenaViewModel @Inject constructor(
    private val observarCuarentena: ObservarCuarentenaUseCase,
    private val aprobar: AprobarFragmentoUseCase,
    private val rechazar: RechazarFragmentoUseCase,
) : ViewModel() {

    val uiState: StateFlow<CuarentenaUiState> = observarCuarentena()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), CuarentenaUiState())
        .let {
            // Wrap the flow to map List<Fragmento> → CuarentenaUiState
            MutableStateFlow(CuarentenaUiState()).also { state ->
                viewModelScope.launch {
                    observarCuarentena().collect { lista ->
                        state.value = CuarentenaUiState(fragmentos = lista)
                    }
                }
            }
        }

    fun onEvent(event: CuarentenaEvent) {
        viewModelScope.launch {
            when (event) {
                is CuarentenaEvent.Aprobar -> aprobar(event.id)
                is CuarentenaEvent.Rechazar -> rechazar(event.id)
            }
        }
    }
}
