package dev.vskelk.cdf.core.common

sealed interface AppError {
    data object MissingApiKey : AppError
    data object Offline : AppError
    data object CircuitOpen : AppError
    data class Http(val code: Int, val body: String?) : AppError
    data class Serialization(val cause: Throwable) : AppError
    data class Unknown(val cause: Throwable) : AppError
}
