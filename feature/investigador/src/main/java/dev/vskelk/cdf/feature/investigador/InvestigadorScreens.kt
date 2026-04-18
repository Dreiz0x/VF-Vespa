package dev.vskelk.cdf.feature.investigador

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.vskelk.cdf.domain.model.CertezaNivel
import dev.vskelk.cdf.domain.model.CuarentenaEstado
import dev.vskelk.cdf.domain.model.FragmentoCuarentena

// ─── Pantalla principal del investigador ────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionNaming")
fun InvestigadorScreen(
    viewModel: InvestigadorViewModel,
    onVerCuarentena: () -> Unit,
    onBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val docPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.onEvent(InvestigadorEvent.DocumentoSeleccionado(it.toString())) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Auto-Investigador") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } },
                actions = {
                    if (state.pendientesCount > 0) {
                        BadgedBox(badge = { Badge { Text("${state.pendientesCount}") } }) {
                            TextButton(onClick = onVerCuarentena) { Text("Cuarentena") }
                        }
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Campo de tema para investigar
            OutlinedTextField(
                value = state.tema,
                onValueChange = { viewModel.onEvent(InvestigadorEvent.TemaChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Tema a investigar") },
                placeholder = { Text("Ej: Integración de Mesas Directivas de Casilla") },
                maxLines = 3,
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { viewModel.onEvent(InvestigadorEvent.InvestigarClicked) },
                    enabled = state.tema.isNotBlank() && !state.isBusy,
                    modifier = Modifier.weight(1f),
                ) {
                    Text(if (state.isBusy) "Investigando..." else "Investigar")
                }
                OutlinedButton(
                    onClick = { docPickerLauncher.launch("application/pdf") },
                    enabled = !state.isBusy,
                ) {
                    Text("Subir doc")
                }
            }

            if (state.isBusy) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                Text(
                    text = state.statusMessage,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            state.error?.let { error ->
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                    Text(
                        text = error,
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                    )
                }
            }

            if (state.ultimoResultado != null) {
                Card {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Resultado", style = MaterialTheme.typography.titleSmall)
                        Text("${state.ultimoResultado!!.fragmentos.size} fragmentos en cuarentena")
                        Text("Modelo: ${state.ultimoResultado!!.modeloUsado}")
                    }
                }
            }
        }
    }
}

// ─── Pantalla de cuarentena ──────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionNaming")
fun CuarentenaScreen(
    viewModel: CuarentenaViewModel,
    onBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cuarentena (${state.fragmentos.size})") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } },
            )
        }
    ) { padding ->
        if (state.fragmentos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Text("Sin fragmentos pendientes", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(state.fragmentos, key = { it.id }) { fragmento ->
                    FragmentoCard(
                        fragmento = fragmento,
                        onAprobar = { viewModel.onEvent(CuarentenaEvent.Aprobar(fragmento.id)) },
                        onRechazar = { viewModel.onEvent(CuarentenaEvent.Rechazar(fragmento.id)) },
                    )
                }
            }
        }
    }
}

@Composable
private fun FragmentoCard(
    fragmento: FragmentoCuarentena,
    onAprobar: () -> Unit,
    onRechazar: () -> Unit,
) {
    val conflicto = fragmento.estado == CuarentenaEstado.CONFLICTO
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (conflicto)
                MaterialTheme.colorScheme.errorContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = fragmento.fuente,
                    style = MaterialTheme.typography.labelMedium,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    CertezaChip(fragmento.certeza)
                    if (conflicto) Icon(Icons.Default.Warning, contentDescription = "Conflicto", tint = MaterialTheme.colorScheme.error)
                }
            }

            Text(
                text = fragmento.contenido,
                style = MaterialTheme.typography.bodyMedium,
            )

            fragmento.conflictoDescripcion?.let { desc ->
                Text(
                    text = "⚠ $desc",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            fragmento.areaExamen?.let { area ->
                Text("Área: $area", style = MaterialTheme.typography.labelSmall)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(onClick = onRechazar) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Rechazar")
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = onAprobar) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Aprobar")
                }
            }
        }
    }
}

@Composable
private fun CertezaChip(nivel: CertezaNivel) {
    val (color, label) = when (nivel) {
        CertezaNivel.ALTA -> MaterialTheme.colorScheme.primary to "ALTA"
        CertezaNivel.MEDIA -> MaterialTheme.colorScheme.tertiary to "MEDIA"
        CertezaNivel.BAJA -> MaterialTheme.colorScheme.error to "BAJA"
    }
    Surface(
        shape = MaterialTheme.shapes.small,
        color = color.copy(alpha = 0.15f),
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color,
        )
    }
}
