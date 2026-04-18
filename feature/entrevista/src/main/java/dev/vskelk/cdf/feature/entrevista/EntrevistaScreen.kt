package dev.vskelk.cdf.feature.entrevista

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
fun EntrevistaScreen(viewModel: EntrevistaViewModel, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        containerColor = VespaColors.Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ENTREVISTA",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(state.competencies, key = { it.id }) { node ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = VespaShapes.Card,
                    colors = CardDefaults.cardColors(
                        containerColor = VespaColors.SurfaceContainer,
                    ),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = node.label,
                            style = MaterialTheme.typography.titleMedium,
                            color = VespaColors.TextPrimary,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = node.description,
                            modifier = Modifier.padding(top = 4.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = VespaColors.TextSecondary,
                        )
                        Text(
                            text = "Tipo: ${node.nodeType} · Confianza: ${"%.0f".format(node.confidence * 100)}%",
                            modifier = Modifier.padding(top = 4.dp),
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
