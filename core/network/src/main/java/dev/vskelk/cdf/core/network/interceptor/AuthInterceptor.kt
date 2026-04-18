package dev.vskelk.cdf.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // Si el request ya tiene x-api-key (inyectado en el DataSource), lo usamos.
        // Si no, procedemos sin modificarlo (para evitar runBlocking aquí).
        return chain.proceed(request)
    }
}
