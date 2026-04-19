package dev.vskelk.cdf.app;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import dev.vskelk.cdf.app.work.PendingSyncScheduler;
import dev.vskelk.cdf.core.database.di.DatabaseSeeder;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<PendingSyncScheduler> pendingSyncSchedulerProvider;

  private final Provider<DatabaseSeeder> databaseSeederProvider;

  public MainActivity_MembersInjector(Provider<PendingSyncScheduler> pendingSyncSchedulerProvider,
      Provider<DatabaseSeeder> databaseSeederProvider) {
    this.pendingSyncSchedulerProvider = pendingSyncSchedulerProvider;
    this.databaseSeederProvider = databaseSeederProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<PendingSyncScheduler> pendingSyncSchedulerProvider,
      Provider<DatabaseSeeder> databaseSeederProvider) {
    return new MainActivity_MembersInjector(pendingSyncSchedulerProvider, databaseSeederProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectPendingSyncScheduler(instance, pendingSyncSchedulerProvider.get());
    injectDatabaseSeeder(instance, databaseSeederProvider.get());
  }

  @InjectedFieldSignature("dev.vskelk.cdf.app.MainActivity.pendingSyncScheduler")
  public static void injectPendingSyncScheduler(MainActivity instance,
      PendingSyncScheduler pendingSyncScheduler) {
    instance.pendingSyncScheduler = pendingSyncScheduler;
  }

  @InjectedFieldSignature("dev.vskelk.cdf.app.MainActivity.databaseSeeder")
  public static void injectDatabaseSeeder(MainActivity instance, DatabaseSeeder databaseSeeder) {
    instance.databaseSeeder = databaseSeeder;
  }
}
