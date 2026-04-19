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
public final class ObserveOntologyNodesUseCase_Factory implements Factory<ObserveOntologyNodesUseCase> {
  private final Provider<OntologyRepository> repositoryProvider;

  public ObserveOntologyNodesUseCase_Factory(Provider<OntologyRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ObserveOntologyNodesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ObserveOntologyNodesUseCase_Factory create(
      Provider<OntologyRepository> repositoryProvider) {
    return new ObserveOntologyNodesUseCase_Factory(repositoryProvider);
  }

  public static ObserveOntologyNodesUseCase newInstance(OntologyRepository repository) {
    return new ObserveOntologyNodesUseCase(repository);
  }
}
