package dev.vskelk.cdf.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.repository.OntologyRepository;
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
public final class ObserveInterviewNodesUseCase_Factory implements Factory<ObserveInterviewNodesUseCase> {
  private final Provider<OntologyRepository> repositoryProvider;

  public ObserveInterviewNodesUseCase_Factory(Provider<OntologyRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ObserveInterviewNodesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ObserveInterviewNodesUseCase_Factory create(
      Provider<OntologyRepository> repositoryProvider) {
    return new ObserveInterviewNodesUseCase_Factory(repositoryProvider);
  }

  public static ObserveInterviewNodesUseCase newInstance(OntologyRepository repository) {
    return new ObserveInterviewNodesUseCase(repository);
  }
}
