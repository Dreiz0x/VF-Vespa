package dev.vskelk.cdf.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.repository.NormativeRepository;
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
public final class ObserveNormSourcesUseCase_Factory implements Factory<ObserveNormSourcesUseCase> {
  private final Provider<NormativeRepository> repositoryProvider;

  public ObserveNormSourcesUseCase_Factory(Provider<NormativeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ObserveNormSourcesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ObserveNormSourcesUseCase_Factory create(
      Provider<NormativeRepository> repositoryProvider) {
    return new ObserveNormSourcesUseCase_Factory(repositoryProvider);
  }

  public static ObserveNormSourcesUseCase newInstance(NormativeRepository repository) {
    return new ObserveNormSourcesUseCase(repository);
  }
}
