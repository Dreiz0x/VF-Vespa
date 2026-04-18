package dev.vskelk.cdf.feature.chaos;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.usecase.ObserveChaosStatusUseCase;
import dev.vskelk.cdf.domain.usecase.RefreshChaosStatusUseCase;
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
public final class ChaosViewModel_Factory implements Factory<ChaosViewModel> {
  private final Provider<ObserveChaosStatusUseCase> observeChaosStatusUseCaseProvider;

  private final Provider<RefreshChaosStatusUseCase> refreshChaosStatusUseCaseProvider;

  public ChaosViewModel_Factory(
      Provider<ObserveChaosStatusUseCase> observeChaosStatusUseCaseProvider,
      Provider<RefreshChaosStatusUseCase> refreshChaosStatusUseCaseProvider) {
    this.observeChaosStatusUseCaseProvider = observeChaosStatusUseCaseProvider;
    this.refreshChaosStatusUseCaseProvider = refreshChaosStatusUseCaseProvider;
  }

  @Override
  public ChaosViewModel get() {
    return newInstance(observeChaosStatusUseCaseProvider.get(), refreshChaosStatusUseCaseProvider.get());
  }

  public static ChaosViewModel_Factory create(
      Provider<ObserveChaosStatusUseCase> observeChaosStatusUseCaseProvider,
      Provider<RefreshChaosStatusUseCase> refreshChaosStatusUseCaseProvider) {
    return new ChaosViewModel_Factory(observeChaosStatusUseCaseProvider, refreshChaosStatusUseCaseProvider);
  }

  public static ChaosViewModel newInstance(ObserveChaosStatusUseCase observeChaosStatusUseCase,
      RefreshChaosStatusUseCase refreshChaosStatusUseCase) {
    return new ChaosViewModel(observeChaosStatusUseCase, refreshChaosStatusUseCase);
  }
}
