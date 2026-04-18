package dev.vskelk.cdf.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.repository.ReactivoRepository;
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
public final class ObserveReactivoDiagnosticsUseCase_Factory implements Factory<ObserveReactivoDiagnosticsUseCase> {
  private final Provider<ReactivoRepository> repositoryProvider;

  public ObserveReactivoDiagnosticsUseCase_Factory(
      Provider<ReactivoRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ObserveReactivoDiagnosticsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ObserveReactivoDiagnosticsUseCase_Factory create(
      Provider<ReactivoRepository> repositoryProvider) {
    return new ObserveReactivoDiagnosticsUseCase_Factory(repositoryProvider);
  }

  public static ObserveReactivoDiagnosticsUseCase newInstance(ReactivoRepository repository) {
    return new ObserveReactivoDiagnosticsUseCase(repository);
  }
}
