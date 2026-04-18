package dev.vskelk.cdf.feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.vskelk.cdf.domain.model.MessageRole

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionNaming")
fun MainScreen(
    viewModel: MainViewModel,
    onOpenChaos: () -> Unit,
    onOpenSimulador: () -> Unit,
    onOpenDiagnostico: () -> Unit,
    onOpenEntrevista: () -> Unit,
    onOpenInvestigador: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.effects.collect { } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Vespa") },
                actions = { TextButton(onClick = onOpenChaos) { Text("Chaos") } },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Offline")
                Switch(
                    checked = state.settings?.offlineMode == true,
                    onCheckedChange = { viewModel.onEvent(MainEvent.OfflineModeChanged(it)) },
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Button(onClick = onOpenSimulador) { Text("Simulador") }
                Button(onClick = onOpenDiagnostico) { Text("Diagnóstico") }
                Button(onClick = onOpenEntrevista) { Text("Entrevista") }
            }

            Button(
                onClick = onOpenInvestigador,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {
                Text("Investigador")
            }

            OutlinedTextField(
                value = state.input,
                onValueChange = { viewModel.onEvent(MainEvent.InputChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                label = { Text("Prompt") },
            )

            Button(
                onClick = { viewModel.onEvent(MainEvent.SendClicked) },
                enabled = !state.isSending,
                modifier = Modifier.padding(top = 8.dp),
            ) {
                Text(if (state.isSending) "Enviando..." else "Enviar")
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            ) {
                items(items = state.messages, key = { it.id }) { message ->
                    val role = when (message.role) {
                        MessageRole.USER -> "USER"
                        MessageRole.ASSISTANT -> "AI"
                        MessageRole.SYSTEM -> "SYSTEM"
                    }
                    Text(
                        text = "$role: ${message.content}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 6.dp),
                    )
                }
            }
        }
    }
}
