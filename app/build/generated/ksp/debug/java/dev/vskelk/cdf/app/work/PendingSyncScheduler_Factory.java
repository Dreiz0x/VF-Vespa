package dev.vskelk.cdf.app.work;

import androidx.work.WorkManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class PendingSyncScheduler_Factory implements Factory<PendingSyncScheduler> {
  private final Provider<WorkManager> workManagerProvider;

  public PendingSyncScheduler_Factory(Provider<WorkManager> workManagerProvider) {
    this.workManagerProvider = workManagerProvider;
  }

  @Override
  public PendingSyncScheduler get() {
    return newInstance(workManagerProvider.get());
  }

  public static PendingSyncScheduler_Factory create(Provider<WorkManager> workManagerProvider) {
    return new PendingSyncScheduler_Factory(workManagerProvider);
  }

  public static PendingSyncScheduler newInstance(WorkManager workManager) {
    return new PendingSyncScheduler(workManager);
  }
}
