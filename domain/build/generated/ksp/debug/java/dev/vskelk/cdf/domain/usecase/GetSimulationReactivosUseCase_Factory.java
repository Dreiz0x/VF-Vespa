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
public final class GetSimulationReactivosUseCase_Factory implements Factory<GetSimulationReactivosUseCase> {
  private final Provider<ReactivoRepository> repositoryProvider;

  public GetSimulationReactivosUseCase_Factory(Provider<ReactivoRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetSimulationReactivosUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetSimulationReactivosUseCase_Factory create(
      Provider<ReactivoRepository> repositoryProvider) {
    return new GetSimulationReactivosUseCase_Factory(repositoryProvider);
  }

  public static GetSimulationReactivosUseCase newInstance(ReactivoRepository repository) {
    return new GetSimulationReactivosUseCase(repository);
  }
}
