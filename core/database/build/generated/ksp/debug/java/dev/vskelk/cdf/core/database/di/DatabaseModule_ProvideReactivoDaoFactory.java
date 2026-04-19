package dev.vskelk.cdf.core.database.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.database.AppDatabase;
import dev.vskelk.cdf.core.database.dao.ReactivoDao;
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
public final class DatabaseModule_ProvideReactivoDaoFactory implements Factory<ReactivoDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideReactivoDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ReactivoDao get() {
    return provideReactivoDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideReactivoDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideReactivoDaoFactory(dbProvider);
  }

  public static ReactivoDao provideReactivoDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideReactivoDao(db));
  }
}
