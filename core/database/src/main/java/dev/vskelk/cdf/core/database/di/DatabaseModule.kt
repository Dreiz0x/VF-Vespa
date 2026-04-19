package dev.vskelk.cdf.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.vskelk.cdf.core.database.AppDatabase
import dev.vskelk.cdf.core.database.dao.MessageDao
import dev.vskelk.cdf.core.database.dao.NormativeDao
import dev.vskelk.cdf.core.database.dao.OntologyDao
import dev.vskelk.cdf.core.database.dao.ReactivoDao
import dev.vskelk.cdf.core.database.dao.SessionDao
import dev.vskelk.cdf.core.database.dao.CuarentenaDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "vespa.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideMessageDao(db: AppDatabase): MessageDao = db.messageDao()
    @Provides fun provideSessionDao(db: AppDatabase): SessionDao = db.sessionDao()
    @Provides fun provideNormativeDao(db: AppDatabase): NormativeDao = db.normativeDao()
    @Provides fun provideOntologyDao(db: AppDatabase): OntologyDao = db.ontologyDao()
    @Provides fun provideReactivoDao(db: AppDatabase): ReactivoDao = db.reactivoDao()
    @Provides fun provideCuarentenaDao(db: AppDatabase): CuarentenaDao = db.cuarentenaDao()
}
