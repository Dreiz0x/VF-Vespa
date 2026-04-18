package dev.vskelk.cdf.feature.diagnostico;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.usecase.ObserveReactivoDiagnosticsUseCase;
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
public final class DiagnosticoViewModel_Factory implements Factory<DiagnosticoViewModel> {
  private final Provider<ObserveReactivoDiagnosticsUseCase> observeReactivoDiagnosticsUseCaseProvider;

  public DiagnosticoViewModel_Factory(
      Provider<ObserveReactivoDiagnosticsUseCase> observeReactivoDiagnosticsUseCaseProvider) {
    this.observeReactivoDiagnosticsUseCaseProvider = observeReactivoDiagnosticsUseCaseProvider;
  }

  @Override
  public DiagnosticoViewModel get() {
    return newInstance(observeReactivoDiagnosticsUseCaseProvider.get());
  }

  public static DiagnosticoViewModel_Factory create(
      Provider<ObserveReactivoDiagnosticsUseCase> observeReactivoDiagnosticsUseCaseProvider) {
    return new DiagnosticoViewModel_Factory(observeReactivoDiagnosticsUseCaseProvider);
  }

  public static DiagnosticoViewModel newInstance(
      ObserveReactivoDiagnosticsUseCase observeReactivoDiagnosticsUseCase) {
    return new DiagnosticoViewModel(observeReactivoDiagnosticsUseCase);
  }
}
