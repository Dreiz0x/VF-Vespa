package dev.vskelk.cdf.core.database.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.database.AppDatabase;
import dev.vskelk.cdf.core.database.dao.NormativeDao;
import javax.annotation.processing.Generated;

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
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class DatabaseModule_ProvideNormativeDaoFactory implements Factory<NormativeDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideNormativeDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public NormativeDao get() {
    return provideNormativeDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideNormativeDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideNormativeDaoFactory(dbProvider);
  }

  public static NormativeDao provideNormativeDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideNormativeDao(db));
  }
}
