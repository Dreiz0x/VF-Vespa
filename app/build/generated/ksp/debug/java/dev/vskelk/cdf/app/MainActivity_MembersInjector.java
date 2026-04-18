package dev.vskelk.cdf.app;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dev.vskelk.cdf.app.work.PendingSyncScheduler;
import javax.annotation.processing.Generated;

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
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<PendingSyncScheduler> pendingSyncSchedulerProvider;

  public MainActivity_MembersInjector(Provider<PendingSyncScheduler> pendingSyncSchedulerProvider) {
    this.pendingSyncSchedulerProvider = pendingSyncSchedulerProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<PendingSyncScheduler> pendingSyncSchedulerProvider) {
    return new MainActivity_MembersInjector(pendingSyncSchedulerProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectPendingSyncScheduler(instance, pendingSyncSchedulerProvider.get());
  }

  @InjectedFieldSignature("dev.vskelk.cdf.app.MainActivity.pendingSyncScheduler")
  public static void injectPendingSyncScheduler(MainActivity instance,
      PendingSyncScheduler pendingSyncScheduler) {
    instance.pendingSyncScheduler = pendingSyncScheduler;
  }
}
