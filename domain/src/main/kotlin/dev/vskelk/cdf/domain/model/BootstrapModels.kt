package dev.vskelk.cdf.domain.model

sealed interface BootstrapState {
    data object Checking : BootstrapState
    data class Seeding(val message: String) : BootstrapState
    data object Ready : BootstrapState
    data class Error(val cause: String) : BootstrapState
}

data class SeedManifest(
    val version: String,
    val minReactivos: Int,
    val minNormativa: Int,
    val minOntologia: Int,
)
