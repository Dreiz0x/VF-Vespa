package dev.vskelk.cdf.feature.simulador

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.vskelk.cdf.core.common.ui.theme.VespaColors
import dev.vskelk.cdf.core.common.ui.theme.VespaShapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionNaming")
fun SimuladorScreen(viewModel: SimuladorViewModel, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        containerColor = VespaColors.Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "SIMULADOR",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                    )
                },
                navigationIcon = {
                    TextButton(
                        onClick = onBack,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = VespaColors.TextSecondary,
                        ),
                    ) {
                        Text("Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VespaColors.Background,
                    titleContentColor = VespaColors.TextPrimary,
                ),
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
            OutlinedTextField(
                value = state.examArea.orEmpty(),
                onValueChange = { viewModel.onEvent(SimuladorEvent.AreaChanged(it.ifBlank { null })) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Área (TECNICO, LENGUAJE, MATEMATICA, SISTEMA)") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = VespaColors.TextPrimary,
                    unfocusedTextColor = VespaColors.TextPrimary,
                    focusedBorderColor = VespaColors.BorderMedium,
                    unfocusedBorderColor = VespaColors.BorderSubtle,
                    focusedContainerColor = VespaColors.SurfaceContainer,
                    unfocusedContainerColor = VespaColors.SurfaceContainer,
                    focusedLabelColor = VespaColors.TextSecondary,
                    unfocusedLabelColor = VespaColors.TextMuted,
                ),
                shape = VespaShapes.InputField,
            )
            OutlinedTextField(
                value = state.limit.toString(),
                onValueChange = { viewModel.onEvent(SimuladorEvent.LimitChanged(it.toIntOrNull() ?: state.limit)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Cantidad (máx 25)") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = VespaColors.TextPrimary,
                    unfocusedTextColor = VespaColors.TextPrimary,
                    focusedBorderColor = VespaColors.BorderMedium,
                    unfocusedBorderColor = VespaColors.BorderSubtle,
                    focusedContainerColor = VespaColors.SurfaceContainer,
                    unfocusedContainerColor = VespaColors.SurfaceContainer,
                    focusedLabelColor = VespaColors.TextSecondary,
                    unfocusedLabelColor = VespaColors.TextMuted,
                ),
                shape = VespaShapes.InputField,
            )
            Button(
                onClick = { viewModel.onEvent(SimuladorEvent.Load) },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth(),
                shape = VespaShapes.ButtonLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = VespaColors.SurfaceVariant,
                    contentColor = VespaColors.TextPrimary,
                ),
            ) {
                Text(
                    text = if (state.isLoading) "Cargando..." else "Generar sesión",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.items, key = { it.reactivo.id }) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = VespaShapes.Card,
                        colors = CardDefaults.cardColors(
                            containerColor = VespaColors.SurfaceContainer,
                        ),
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = item.reactivo.stem,
                                style = MaterialTheme.typography.bodyLarge,
                                color = VespaColors.TextPrimary,
                            )
                            item.options.forEach { option ->
                                Text(
                                    text = "${option.label}. ${option.text}",
                                    modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = VespaColors.TextSecondary,
                                )
                            }
                            Text(
                                text = "Área: ${item.reactivo.examArea} " +
                                    "· Vigente: ${item.validity?.isCurrent ?: false} " +
                                    "· Dificultad: ${item.metadata?.difficulty ?: 0.0}",
                                modifier = Modifier.padding(top = 6.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = VespaColors.TextMuted,
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(top = 8.dp),
                                color = VespaColors.BorderSubtle,
                            )
                        }
                    }
                }
            }
        }
    }
}
