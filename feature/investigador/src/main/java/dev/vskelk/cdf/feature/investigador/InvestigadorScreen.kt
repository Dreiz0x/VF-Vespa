package dev.vskelk.cdf.feature.investigador

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.vskelk.cdf.core.common.ui.theme.VespaColors
import dev.vskelk.cdf.core.common.ui.theme.VespaShapes
import dev.vskelk.cdf.domain.model.CertezaNivel
import dev.vskelk.cdf.domain.model.CuarentenaEstado
import dev.vskelk.cdf.domain.model.FragmentoCuarentena

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionNaming")
fun InvestigadorScreen(
    viewModel: InvestigadorViewModel,
    onVerCuarentena: () -> Unit,
    onBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val docPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.onEvent(InvestigadorEvent.DocumentoSeleccionado(it.toString())) }
    }

    Scaffold(
        containerColor = VespaColors.Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "AUTO-INVESTIGADOR",
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
                actions = {
                    if (state.pendientesCount > 0) {
                        BadgedBox(badge = { Badge { Text("${state.pendientesCount}") } }) {
                            TextButton(
                                onClick = onVerCuarentena,
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = VespaColors.TextSecondary,
                                ),
                            ) {
                                Text("Cuarentena")
                            }
                        }
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
                value = state.tema,
                onValueChange = { viewModel.onEvent(InvestigadorEvent.TemaChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Tema a investigar") },
                placeholder = { Text("Ej: Integración de Mesas Directivas de Casilla") },
                maxLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = VespaColors.TextPrimary,
                    unfocusedTextColor = VespaColors.TextPrimary,
                    focusedBorderColor = VespaColors.BorderMedium,
                    unfocusedBorderColor = VespaColors.BorderSubtle,
                    focusedContainerColor = VespaColors.SurfaceContainer,
                    unfocusedContainerColor = VespaColors.SurfaceContainer,
                    focusedLabelColor = VespaColors.TextSecondary,
                    unfocusedLabelColor = VespaColors.TextMuted,
                    focusedPlaceholderColor = VespaColors.TextMuted,
                    unfocusedPlaceholderColor = VespaColors.TextMuted,
                ),
                shape = VespaShapes.InputField,
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { viewModel.onEvent(InvestigadorEvent.InvestigarClicked) },
                    enabled = state.tema.isNotBlank() && !state.isBusy,
                    modifier = Modifier.weight(1f),
                    shape = VespaShapes.ButtonLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VespaColors.SurfaceVariant,
                        contentColor = VespaColors.TextPrimary,
                    ),
                ) {
                    Text(
                        text = if (state.isBusy) "Investigando..." else "Investigar",
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
                OutlinedButton(
                    onClick = { docPickerLauncher.launch("application/pdf") },
                    enabled = !state.isBusy,
                    shape = VespaShapes.Button,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = VespaColors.TextSecondary,
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = SolidColor(VespaColors.BorderSubtle),
                    ),
                ) {
                    Text("Subir doc")
                }
            }

            if (state.isBusy) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = VespaColors.TextPrimary,
                    trackColor = VespaColors.SurfaceVariant,
                )
                Text(
                    text = state.statusMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = VespaColors.TextSecondary,
                )
            }

            state.error?.let { error ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = VespaColors.Error.copy(alpha = 0.1f),
                    ),
                    shape = VespaShapes.Card,
                ) {
                    Text(
                        text = error,
                        modifier = Modifier.padding(12.dp),
                        color = VespaColors.AccentRed,
                    )
                }
            }

            if (state.ultimoResultado != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = VespaShapes.Card,
                    colors = CardDefaults.cardColors(
                        containerColor = VespaColors.SurfaceContainer,
                    ),
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Resultado",
                            style = MaterialTheme.typography.titleSmall,
                            color = VespaColors.TextPrimary,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = "${state.ultimoResultado!!.fragmentos.size} fragmentos en cuarentena",
                            style = MaterialTheme.typography.bodyMedium,
                            color = VespaColors.TextSecondary,
                        )
                        Text(
                            text = "Modelo: ${state.ultimoResultado!!.modeloUsado}",
                            style = MaterialTheme.typography.labelSmall,
                            color = VespaColors.TextMuted,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionNaming")
fun CuarentenaScreen(
    viewModel: CuarentenaViewModel,
    onBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = VespaColors.Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CUARENTENA (${state.fragmentos.size})",
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
        if (state.fragmentos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Sin fragmentos pendientes",
                    style = MaterialTheme.typography.bodyLarge,
                    color = VespaColors.TextSecondary,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
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
        modifier = Modifier.fillMaxWidth(),
        shape = VespaShapes.Card,
        colors = CardDefaults.cardColors(
            containerColor = if (conflicto)
                VespaColors.Error.copy(alpha = 0.1f)
            else
                VespaColors.SurfaceContainer,
        ),
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
                    color = VespaColors.TextSecondary,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    CertezaChip(fragmento.certeza)
                    if (conflicto) Icon(
                        Icons.Default.Warning,
                        contentDescription = "Conflicto",
                        tint = VespaColors.AccentRed,
                    )
                }
            }

            Text(
                text = fragmento.contenido,
                style = MaterialTheme.typography.bodyMedium,
                color = VespaColors.TextPrimary,
            )

            fragmento.conflictoDescripcion?.let { desc ->
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall,
                    color = VespaColors.AccentRed,
                )
            }

            fragmento.areaExamen?.let { area ->
                Text(
                    text = "Área: $area",
                    style = MaterialTheme.typography.labelSmall,
                    color = VespaColors.TextMuted,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(
                    onClick = onRechazar,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = VespaColors.AccentRed,
                    ),
                ) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Rechazar")
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = onAprobar,
                    shape = VespaShapes.Button,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VespaColors.SurfaceVariant,
                        contentColor = VespaColors.TextPrimary,
                    ),
                ) {
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
        CertezaNivel.ALTA -> VespaColors.AccentGreen to "ALTA"
        CertezaNivel.MEDIA -> VespaColors.AccentOrange to "MEDIA"
        CertezaNivel.BAJA -> VespaColors.AccentRed to "BAJA"
    }
    Surface(
        shape = VespaShapes.Chip,
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
