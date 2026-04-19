package dev.vskelk.cdf.data.repository.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.usecase.SyncPendingMessagesUseCase;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class PendingMessagesSyncWorker_Factory {
  private final Provider<SyncPendingMessagesUseCase> syncPendingMessagesUseCaseProvider;

  public PendingMessagesSyncWorker_Factory(
      Provider<SyncPendingMessagesUseCase> syncPendingMessagesUseCaseProvider) {
    this.syncPendingMessagesUseCaseProvider = syncPendingMessagesUseCaseProvider;
  }

  public PendingMessagesSyncWorker get(Context appContext, WorkerParameters params) {
    return newInstance(appContext, params, syncPendingMessagesUseCaseProvider.get());
  }

  public static PendingMessagesSyncWorker_Factory create(
      Provider<SyncPendingMessagesUseCase> syncPendingMessagesUseCaseProvider) {
    return new PendingMessagesSyncWorker_Factory(syncPendingMessagesUseCaseProvider);
  }

  public static PendingMessagesSyncWorker newInstance(Context appContext, WorkerParameters params,
      SyncPendingMessagesUseCase syncPendingMessagesUseCase) {
    return new PendingMessagesSyncWorker(appContext, params, syncPendingMessagesUseCase);
  }
}
