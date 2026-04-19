package dev.vskelk.cdf.data.repository.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.database.dao.OntologyDao;
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
public final class OntologyRepositoryImpl_Factory implements Factory<OntologyRepositoryImpl> {
  private final Provider<OntologyDao> daoProvider;

  public OntologyRepositoryImpl_Factory(Provider<OntologyDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public OntologyRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static OntologyRepositoryImpl_Factory create(Provider<OntologyDao> daoProvider) {
    return new OntologyRepositoryImpl_Factory(daoProvider);
  }

  public static OntologyRepositoryImpl newInstance(OntologyDao dao) {
    return new OntologyRepositoryImpl(dao);
  }
}
