package dev.vskelk.cdf.feature.entrevista;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.usecase.ObserveInterviewNodesUseCase;
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
public final class EntrevistaViewModel_Factory implements Factory<EntrevistaViewModel> {
  private final Provider<ObserveInterviewNodesUseCase> observeInterviewNodesUseCaseProvider;

  public EntrevistaViewModel_Factory(
      Provider<ObserveInterviewNodesUseCase> observeInterviewNodesUseCaseProvider) {
    this.observeInterviewNodesUseCaseProvider = observeInterviewNodesUseCaseProvider;
  }

  @Override
  public EntrevistaViewModel get() {
    return newInstance(observeInterviewNodesUseCaseProvider.get());
  }

  public static EntrevistaViewModel_Factory create(
      Provider<ObserveInterviewNodesUseCase> observeInterviewNodesUseCaseProvider) {
    return new EntrevistaViewModel_Factory(observeInterviewNodesUseCaseProvider);
  }

  public static EntrevistaViewModel newInstance(
      ObserveInterviewNodesUseCase observeInterviewNodesUseCase) {
    return new EntrevistaViewModel(observeInterviewNodesUseCase);
  }
}
