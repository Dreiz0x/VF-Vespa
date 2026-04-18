package dev.vskelk.cdf.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.vskelk.cdf.core.network.api.AnthropicApi
import dev.vskelk.cdf.core.network.datasource.AnthropicRemoteDataSource
import dev.vskelk.cdf.core.network.datasource.LlmRemoteDataSource
import dev.vskelk.cdf.core.network.interceptor.AuthInterceptor
import dev.vskelk.cdf.core.network.interceptor.DebugLoggingInterceptorFactory
import dev.vskelk.cdf.core.network.interceptor.RetryBackoffInterceptor
import dev.vskelk.cdf.core.network.security.CertificatePinsProvider
import kotlinx.serialization.json.Json
import okhttp3.CertificatePinner
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        isLenient = false
    }

    @Provides
    @Singleton
    fun provideCertificatePinner(
        pinsProvider: CertificatePinsProvider,
    ): CertificatePinner = CertificatePinner.Builder().apply {
        pinsProvider.pins().forEach { add("api.anthropic.com", it) }
    }.build()

    @Provides
    @Singleton
    fun provideOkHttp(
        authInterceptor: AuthInterceptor,
        retryBackoffInterceptor: RetryBackoffInterceptor,
        debugLoggingInterceptorFactory: DebugLoggingInterceptorFactory,
        certificatePinner: CertificatePinner,
    ): OkHttpClient = OkHttpClient.Builder()
        .certificatePinner(certificatePinner)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .writeTimeout(40, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(retryBackoffInterceptor)
        .addInterceptor(debugLoggingInterceptorFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAnthropicApi(client: OkHttpClient, json: Json): AnthropicApi =
        Retrofit.Builder()
            .baseUrl("https://api.anthropic.com/")
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(AnthropicApi::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindingModule {
    @Binds
    abstract fun bindLlmRemoteDataSource(
        impl: AnthropicRemoteDataSource,
    ): LlmRemoteDataSource
}
