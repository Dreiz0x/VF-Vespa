package dev.vskelk.cdf.feature.simulador;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.usecase.GetSimulationReactivosUseCase;
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
public final class SimuladorViewModel_Factory implements Factory<SimuladorViewModel> {
  private final Provider<GetSimulationReactivosUseCase> getSimulationReactivosUseCaseProvider;

  public SimuladorViewModel_Factory(
      Provider<GetSimulationReactivosUseCase> getSimulationReactivosUseCaseProvider) {
    this.getSimulationReactivosUseCaseProvider = getSimulationReactivosUseCaseProvider;
  }

  @Override
  public SimuladorViewModel get() {
    return newInstance(getSimulationReactivosUseCaseProvider.get());
  }

  public static SimuladorViewModel_Factory create(
      Provider<GetSimulationReactivosUseCase> getSimulationReactivosUseCaseProvider) {
    return new SimuladorViewModel_Factory(getSimulationReactivosUseCaseProvider);
  }

  public static SimuladorViewModel newInstance(
      GetSimulationReactivosUseCase getSimulationReactivosUseCase) {
    return new SimuladorViewModel(getSimulationReactivosUseCase);
  }
}
