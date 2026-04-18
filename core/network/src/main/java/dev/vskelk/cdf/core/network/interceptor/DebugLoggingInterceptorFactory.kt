package dev.vskelk.cdf.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DebugLoggingInterceptorFactory @Inject constructor() {
    fun create(): Interceptor = HttpLoggingInterceptor().apply {
        level = if (dev.vskelk.cdf.core.network.BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }
}
