package dev.vskelk.cdf.core.database.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.database.AppDatabase;
import dev.vskelk.cdf.core.database.dao.OntologyDao;
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
public final class DatabaseModule_ProvideOntologyDaoFactory implements Factory<OntologyDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideOntologyDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public OntologyDao get() {
    return provideOntologyDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideOntologyDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideOntologyDaoFactory(dbProvider);
  }

  public static OntologyDao provideOntologyDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideOntologyDao(db));
  }
}
