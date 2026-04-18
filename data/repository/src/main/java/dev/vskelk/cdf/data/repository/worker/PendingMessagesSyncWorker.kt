package dev.vskelk.cdf.data.repository.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.vskelk.cdf.core.common.AppResult
import dev.vskelk.cdf.domain.usecase.SyncPendingMessagesUseCase

@HiltWorker
class PendingMessagesSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val syncPendingMessagesUseCase: SyncPendingMessagesUseCase,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return when (syncPendingMessagesUseCase(DEFAULT_SESSION_ID)) {
            is AppResult.Success -> Result.success()
            is AppResult.Failure -> if (runAttemptCount >= MAX_ATTEMPTS) Result.failure() else Result.retry()
        }
    }

    companion object {
        const val UNIQUE_WORK_NAME = "pending_messages_sync"
        const val DEFAULT_SESSION_ID = "default"
        private const val MAX_ATTEMPTS = 5
    }
}
