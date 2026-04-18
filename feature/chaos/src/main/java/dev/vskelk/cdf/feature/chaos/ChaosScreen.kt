package dev.vskelk.cdf.feature.chaos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
fun ChaosScreen(viewModel: ChaosViewModel, onBack: () -> Unit) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chaos Monitor") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Online: ${state.status?.online ?: false}")
            Text("Circuit Open: ${state.status?.breakerOpen ?: false}")
            Text("Cached Messages: ${state.status?.cachedMessages ?: 0}")
            Text("Pending Messages: ${state.status?.pendingMessages ?: 0}")
            Button(onClick = { viewModel.onEvent(ChaosEvent.Refresh) }, enabled = !state.isRefreshing) {
                Text(if (state.isRefreshing) "Refreshing..." else "Refresh")
            }
        }
    }
}
