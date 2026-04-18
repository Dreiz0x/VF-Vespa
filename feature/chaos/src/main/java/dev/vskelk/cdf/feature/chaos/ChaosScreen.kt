package dev.vskelk.cdf.feature.chaos

import androidx.compose.foundation.layout.*
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
fun ChaosScreen(viewModel: ChaosViewModel, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        containerColor = VespaColors.Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CHAOS MONITOR",
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
            StatusItem("Online", state.status?.online ?: false)
            StatusItem("Circuit Open", state.status?.breakerOpen ?: false)
            StatusItem("Cached Messages", (state.status?.cachedMessages ?: 0).toString())
            StatusItem("Pending Messages", (state.status?.pendingMessages ?: 0).toString())
            Button(
                onClick = { viewModel.onEvent(ChaosEvent.Refresh) },
                enabled = !state.isRefreshing,
                modifier = Modifier.fillMaxWidth(),
                shape = VespaShapes.ButtonLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = VespaColors.SurfaceVariant,
                    contentColor = VespaColors.TextPrimary,
                ),
            ) {
                Text(
                    text = if (state.isRefreshing) "Refreshing..." else "Refresh",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun StatusItem(label: String, value: Any) {
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
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = VespaColors.TextSecondary,
            )
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = VespaColors.TextPrimary,
            )
        }
    }
}
