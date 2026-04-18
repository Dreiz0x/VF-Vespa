package dev.vskelk.cdf.feature.diagnostico

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
fun DiagnosticoScreen(viewModel: DiagnosticoViewModel, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        containerColor = VespaColors.Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "DIAGNÓSTICO",
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
            items(state.diagnostics, key = { it.examArea }) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = VespaShapes.Card,
                    colors = CardDefaults.cardColors(
                        containerColor = VespaColors.SurfaceContainer,
                    ),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "${item.examArea}: ${item.count} reactivos",
                            style = MaterialTheme.typography.titleMedium,
                            color = VespaColors.TextPrimary,
                            fontWeight = FontWeight.SemiBold,
                        )
                        item.byCognitiveLevel.forEach { (level, count) ->
                            Text(
                                text = "  $level → $count",
                                modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = VespaColors.TextSecondary,
                            )
                        }
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
