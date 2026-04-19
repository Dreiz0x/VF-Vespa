package dev.vskelk.cdf.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.repository.ReactivoRepository;
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
public final class ObserveActiveReactivosUseCase_Factory implements Factory<ObserveActiveReactivosUseCase> {
  private final Provider<ReactivoRepository> repositoryProvider;

  public ObserveActiveReactivosUseCase_Factory(Provider<ReactivoRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ObserveActiveReactivosUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ObserveActiveReactivosUseCase_Factory create(
      Provider<ReactivoRepository> repositoryProvider) {
    return new ObserveActiveReactivosUseCase_Factory(repositoryProvider);
  }

  public static ObserveActiveReactivosUseCase newInstance(ReactivoRepository repository) {
    return new ObserveActiveReactivosUseCase(repository);
  }
}
