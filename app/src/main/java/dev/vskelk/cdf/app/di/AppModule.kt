package dev.vskelk.cdf.app.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.vskelk.cdf.core.common.ConnectivityNetworkMonitor
import dev.vskelk.cdf.core.common.NetworkMonitor
import dev.vskelk.cdf.core.network.security.CertificatePinsProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCertificatePinsProvider(
        @ApplicationContext context: Context,
    ): CertificatePinsProvider = CertificatePinsProvider {
        val pin1Id = context.resources.getIdentifier("anthropic_pin_1", "string", context.packageName)
        val pin2Id = context.resources.getIdentifier("anthropic_pin_2", "string", context.packageName)
        listOfNotNull(
            if (pin1Id != 0) context.getString(pin1Id).takeIf { it.isNotBlank() } else null,
            if (pin2Id != 0) context.getString(pin2Id).takeIf { it.isNotBlank() } else null,
        ).toSet()
    }

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context,
    ): WorkManager = WorkManager.getInstance(context)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindingModule {

    @Binds
    @Singleton
    abstract fun bindNetworkMonitor(
        impl: ConnectivityNetworkMonitor,
    ): NetworkMonitor
}
