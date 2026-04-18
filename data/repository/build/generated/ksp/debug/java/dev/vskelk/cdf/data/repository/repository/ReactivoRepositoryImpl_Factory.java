package dev.vskelk.cdf.data.repository.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.database.dao.ReactivoDao;
import javax.annotation.processing.Generated;

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
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class ReactivoRepositoryImpl_Factory implements Factory<ReactivoRepositoryImpl> {
  private final Provider<ReactivoDao> daoProvider;

  public ReactivoRepositoryImpl_Factory(Provider<ReactivoDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public ReactivoRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static ReactivoRepositoryImpl_Factory create(Provider<ReactivoDao> daoProvider) {
    return new ReactivoRepositoryImpl_Factory(daoProvider);
  }

  public static ReactivoRepositoryImpl newInstance(ReactivoDao dao) {
    return new ReactivoRepositoryImpl(dao);
  }
}
