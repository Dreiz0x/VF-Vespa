package dev.vskelk.cdf.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.vskelk.cdf.app.navigation.AppNavGraph
import dev.vskelk.cdf.app.work.PendingSyncScheduler
import dev.vskelk.cdf.core.database.di.DatabaseSeeder
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var pendingSyncScheduler: PendingSyncScheduler
    @Inject lateinit var databaseSeeder: DatabaseSeeder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            LaunchedEffect(Unit) { 
                databaseSeeder.seed()
                pendingSyncScheduler.schedulePeriodic() 
            }
            MaterialTheme {
                AppNavGraph(navController = navController)
            }
        }
    }
}
