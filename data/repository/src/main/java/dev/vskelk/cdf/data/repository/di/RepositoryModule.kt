package dev.vskelk.cdf.data.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.vskelk.cdf.data.repository.repository.ChaosRepositoryImpl
import dev.vskelk.cdf.data.repository.repository.ConversationRepositoryImpl
import dev.vskelk.cdf.data.repository.repository.NormativeRepositoryImpl
import dev.vskelk.cdf.data.repository.repository.OntologyRepositoryImpl
import dev.vskelk.cdf.data.repository.repository.ReactivoRepositoryImpl
import dev.vskelk.cdf.data.repository.repository.SettingsRepositoryImpl
import dev.vskelk.cdf.data.repository.repository.BootstrapRepositoryImpl
import dev.vskelk.cdf.data.repository.repository.InvestigadorRepositoryImpl
import dev.vskelk.cdf.domain.repository.ChaosRepository
import dev.vskelk.cdf.domain.repository.ConversationRepository
import dev.vskelk.cdf.domain.repository.NormativeRepository
import dev.vskelk.cdf.domain.repository.OntologyRepository
import dev.vskelk.cdf.domain.repository.ReactivoRepository
import dev.vskelk.cdf.domain.repository.SettingsRepository
import dev.vskelk.cdf.domain.repository.BootstrapRepository
import dev.vskelk.cdf.domain.repository.InvestigadorRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindConversationRepository(impl: ConversationRepositoryImpl): ConversationRepository

    @Binds @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds @Singleton
    abstract fun bindChaosRepository(impl: ChaosRepositoryImpl): ChaosRepository

    @Binds @Singleton
    abstract fun bindNormativeRepository(impl: NormativeRepositoryImpl): NormativeRepository

    @Binds @Singleton
    abstract fun bindOntologyRepository(impl: OntologyRepositoryImpl): OntologyRepository

    @Binds @Singleton
    abstract fun bindReactivoRepository(impl: ReactivoRepositoryImpl): ReactivoRepository

    @Binds @Singleton
    abstract fun bindBootstrapRepository(impl: BootstrapRepositoryImpl): BootstrapRepository

    @Binds @Singleton
    abstract fun bindInvestigadorRepository(impl: InvestigadorRepositoryImpl): InvestigadorRepository
}
