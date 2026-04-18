package dev.vskelk.cdf.core.network.security

fun interface CertificatePinsProvider {
    fun pins(): Set<String>
}
