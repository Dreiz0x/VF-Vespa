package dev.vskelk.cdf.data.repository.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.database.dao.NormativeDao;
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
public final class NormativeRepositoryImpl_Factory implements Factory<NormativeRepositoryImpl> {
  private final Provider<NormativeDao> daoProvider;

  public NormativeRepositoryImpl_Factory(Provider<NormativeDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public NormativeRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static NormativeRepositoryImpl_Factory create(Provider<NormativeDao> daoProvider) {
    return new NormativeRepositoryImpl_Factory(daoProvider);
  }

  public static NormativeRepositoryImpl newInstance(NormativeDao dao) {
    return new NormativeRepositoryImpl(dao);
  }
}
