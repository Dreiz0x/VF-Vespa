package dev.vskelk.cdf.app.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.vskelk.cdf.app.ui.VespaSplashScreen
import dev.vskelk.cdf.feature.chaos.ChaosScreen
import dev.vskelk.cdf.feature.chaos.ChaosViewModel
import dev.vskelk.cdf.feature.diagnostico.DiagnosticoScreen
import dev.vskelk.cdf.feature.diagnostico.DiagnosticoViewModel
import dev.vskelk.cdf.feature.entrevista.EntrevistaScreen
import dev.vskelk.cdf.feature.entrevista.EntrevistaViewModel
import dev.vskelk.cdf.feature.main.MainScreen
import dev.vskelk.cdf.feature.main.MainViewModel
import dev.vskelk.cdf.feature.simulador.SimuladorScreen
import dev.vskelk.cdf.feature.simulador.SimuladorViewModel
import dev.vskelk.cdf.feature.investigador.InvestigadorScreen
import dev.vskelk.cdf.feature.investigador.InvestigadorViewModel
import dev.vskelk.cdf.feature.investigador.CuarentenaScreen
import dev.vskelk.cdf.feature.investigador.CuarentenaViewModel

@Composable
@Suppress("FunctionNaming")
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Splash,
    ) {
        composable(Routes.Splash) {
            VespaSplashScreen(
                onStart = {
                    navController.navigate(Routes.Main)
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.Main) {
            val viewModel: MainViewModel = hiltViewModel()
            MainScreen(
                viewModel = viewModel,
                onOpenChaos = { navController.navigate(Routes.Chaos) },
                onOpenSimulador = { navController.navigate(Routes.Simulador) },
                onOpenDiagnostico = { navController.navigate(Routes.Diagnostico) },
                onOpenEntrevista = { navController.navigate(Routes.Entrevista) },
                onOpenInvestigador = { navController.navigate(Routes.Investigador) },
            )
        }

        composable(Routes.Chaos) {
            val viewModel: ChaosViewModel = hiltViewModel()
            ChaosScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }

        composable(Routes.Simulador) {
            val viewModel: SimuladorViewModel = hiltViewModel()
            SimuladorScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }

        composable(Routes.Diagnostico) {
            val viewModel: DiagnosticoViewModel = hiltViewModel()
            DiagnosticoScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }

        composable(Routes.Entrevista) {
            val viewModel: EntrevistaViewModel = hiltViewModel()
            EntrevistaScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }

        composable(Routes.Investigador) {
            val viewModel: InvestigadorViewModel = hiltViewModel()
            InvestigadorScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onVerCuarentena = { navController.navigate(Routes.Cuarentena) }
            )
        }

        composable(Routes.Cuarentena) {
            val viewModel: CuarentenaViewModel = hiltViewModel()
            CuarentenaScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
