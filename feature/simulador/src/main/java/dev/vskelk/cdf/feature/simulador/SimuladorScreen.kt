package dev.vskelk.cdf.feature.simulador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionNaming")
fun SimuladorScreen(viewModel: SimuladorViewModel, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Simulador") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                value = state.examArea.orEmpty(),
                onValueChange = { viewModel.onEvent(SimuladorEvent.AreaChanged(it.ifBlank { null })) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Área (TECNICO, LENGUAJE, MATEMATICA, SISTEMA)") },
            )
            OutlinedTextField(
                value = state.limit.toString(),
                onValueChange = { viewModel.onEvent(SimuladorEvent.LimitChanged(it.toIntOrNull() ?: state.limit)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Cantidad (máx 25)") },
            )
            Button(
                onClick = { viewModel.onEvent(SimuladorEvent.Load) },
                enabled = !state.isLoading,
            ) {
                Text(if (state.isLoading) "Cargando..." else "Generar sesión")
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.items, key = { it.reactivo.id }) { item ->
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Text(text = item.reactivo.stem, style = MaterialTheme.typography.bodyLarge)
                        item.options.forEach { option ->
                            Text(
                                text = "${option.label}. ${option.text}",
                                modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Text(
                            text = "Área: ${item.reactivo.examArea} " +
                                "· Vigente: ${item.validity?.isCurrent ?: false} " +
                                "· Dificultad: ${item.metadata?.difficulty ?: 0.0}",
                            modifier = Modifier.padding(top = 6.dp),
                            style = MaterialTheme.typography.labelSmall,
                        )
                        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
    }
}
