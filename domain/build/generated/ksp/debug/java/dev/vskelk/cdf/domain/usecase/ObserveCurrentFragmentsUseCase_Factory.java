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
public final class ObserveCurrentFragmentsUseCase_Factory implements Factory<ObserveCurrentFragmentsUseCase> {
  private final Provider<NormativeRepository> repositoryProvider;

  public ObserveCurrentFragmentsUseCase_Factory(Provider<NormativeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ObserveCurrentFragmentsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ObserveCurrentFragmentsUseCase_Factory create(
      Provider<NormativeRepository> repositoryProvider) {
    return new ObserveCurrentFragmentsUseCase_Factory(repositoryProvider);
  }

  public static ObserveCurrentFragmentsUseCase newInstance(NormativeRepository repository) {
    return new ObserveCurrentFragmentsUseCase(repository);
  }
}
