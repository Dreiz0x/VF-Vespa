package dev.vskelk.cdf.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.repository.OntologyRepository;
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
public final class GetNodeWithFragmentsUseCase_Factory implements Factory<GetNodeWithFragmentsUseCase> {
  private final Provider<OntologyRepository> repositoryProvider;

  public GetNodeWithFragmentsUseCase_Factory(Provider<OntologyRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetNodeWithFragmentsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetNodeWithFragmentsUseCase_Factory create(
      Provider<OntologyRepository> repositoryProvider) {
    return new GetNodeWithFragmentsUseCase_Factory(repositoryProvider);
  }

  public static GetNodeWithFragmentsUseCase newInstance(OntologyRepository repository) {
    return new GetNodeWithFragmentsUseCase(repository);
  }
}
