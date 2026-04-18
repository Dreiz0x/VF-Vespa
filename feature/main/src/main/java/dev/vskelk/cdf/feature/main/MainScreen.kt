package dev.vskelk.cdf.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.vskelk.cdf.core.common.ui.theme.VespaColors
import dev.vskelk.cdf.core.common.ui.theme.VespaShapes
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
        containerColor = VespaColors.Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "VESPA",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 4.sp,
                    )
                },
                actions = {
                    TextButton(
                        onClick = onOpenChaos,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = VespaColors.TextSecondary,
                        ),
                    ) {
                        Text("Chaos")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VespaColors.Background,
                    titleContentColor = VespaColors.TextPrimary,
                ),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            // Status Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = VespaShapes.Card,
                colors = CardDefaults.cardColors(
                    containerColor = VespaColors.SurfaceContainer,
                ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (state.settings?.offlineMode == true)
                                        VespaColors.Offline
                                    else
                                        VespaColors.Online
                                )
                        )
                        Text(
                            text = if (state.settings?.offlineMode == true) "Offline" else "Online",
                            style = MaterialTheme.typography.bodyMedium,
                            color = VespaColors.TextPrimary,
                        )
                    }
                    Switch(
                        checked = state.settings?.offlineMode == true,
                        onCheckedChange = { viewModel.onEvent(MainEvent.OfflineModeChanged(it)) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = VespaColors.Primary,
                            checkedTrackColor = VespaColors.SurfaceVariant,
                            uncheckedThumbColor = VespaColors.TextMuted,
                            uncheckedTrackColor = VespaColors.SurfaceVariant,
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                MainActionButton(
                    text = "Simulador",
                    onClick = onOpenSimulador,
                    modifier = Modifier.weight(1f),
                )
                MainActionButton(
                    text = "Diagnóstico",
                    onClick = onOpenDiagnostico,
                    modifier = Modifier.weight(1f),
                )
                MainActionButton(
                    text = "Entrevista",
                    onClick = onOpenEntrevista,
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            MainActionButton(
                text = "Investigador",
                onClick = onOpenInvestigador,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input Section
            OutlinedTextField(
                value = state.input,
                onValueChange = { viewModel.onEvent(MainEvent.InputChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Prompt") },
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
                onClick = { viewModel.onEvent(MainEvent.SendClicked) },
                enabled = !state.isSending,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                shape = VespaShapes.ButtonLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = VespaColors.SurfaceVariant,
                    contentColor = VespaColors.TextPrimary,
                ),
            ) {
                Text(
                    text = if (state.isSending) "Enviando..." else "Enviar",
                    style = MaterialTheme.typography.labelLarge,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Messages List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(items = state.messages, key = { it.id }) { message ->
                    val (bgColor, labelColor, label) = when (message.role) {
                        MessageRole.USER -> Triple(
                            VespaColors.SurfaceVariant,
                            VespaColors.AccentBlue,
                            "USER"
                        )
                        MessageRole.ASSISTANT -> Triple(
                            VespaColors.SurfaceContainer,
                            VespaColors.AccentGreen,
                            "AI"
                        )
                        MessageRole.SYSTEM -> Triple(
                            VespaColors.PrimaryContainer,
                            VespaColors.TextMuted,
                            "SYSTEM"
                        )
                    }
                    MessageBubble(
                        role = label,
                        content = message.content,
                        roleColor = labelColor,
                        backgroundColor = bgColor,
                    )
                }
            }
        }
    }
}

@Composable
private fun MainActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = VespaShapes.Button,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = VespaColors.TextPrimary,
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = SolidColor(VespaColors.BorderSubtle),
        ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun MessageBubble(
    role: String,
    content: String,
    roleColor: androidx.compose.ui.graphics.Color,
    backgroundColor: androidx.compose.ui.graphics.Color,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(12.dp),
    ) {
        Text(
            text = role,
            style = MaterialTheme.typography.labelSmall,
            color = roleColor,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = VespaColors.TextPrimary,
        )
    }
}
