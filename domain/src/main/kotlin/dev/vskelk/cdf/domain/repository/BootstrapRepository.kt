package dev.vskelk.cdf.domain.repository

import dev.vskelk.cdf.domain.model.BootstrapState
import kotlinx.coroutines.flow.Flow

interface BootstrapRepository {
    fun observe(): Flow<BootstrapState>
    suspend fun initialize()
}
