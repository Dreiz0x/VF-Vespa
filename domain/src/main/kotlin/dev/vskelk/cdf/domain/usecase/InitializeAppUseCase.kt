package dev.vskelk.cdf.domain.usecase

import dev.vskelk.cdf.domain.repository.BootstrapRepository
import javax.inject.Inject

class InitializeAppUseCase @Inject constructor(
    private val repository: BootstrapRepository,
) {
    suspend operator fun invoke() = repository.initialize()
}
