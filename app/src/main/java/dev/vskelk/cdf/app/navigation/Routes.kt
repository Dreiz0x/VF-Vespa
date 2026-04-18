package dev.vskelk.cdf.app.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable data object Splash : Routes
    @Serializable data object Main : Routes
    @Serializable data object Chaos : Routes
    @Serializable data object Simulador : Routes
    @Serializable data object Diagnostico : Routes
    @Serializable data object Entrevista : Routes
    @Serializable data object Investigador : Routes
    @Serializable data object Cuarentena : Routes
}
