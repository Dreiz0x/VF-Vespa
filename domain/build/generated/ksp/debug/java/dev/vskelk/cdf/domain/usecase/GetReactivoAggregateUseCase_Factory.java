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
public final class GetReactivoAggregateUseCase_Factory implements Factory<GetReactivoAggregateUseCase> {
  private final Provider<ReactivoRepository> repositoryProvider;

  public GetReactivoAggregateUseCase_Factory(Provider<ReactivoRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetReactivoAggregateUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetReactivoAggregateUseCase_Factory create(
      Provider<ReactivoRepository> repositoryProvider) {
    return new GetReactivoAggregateUseCase_Factory(repositoryProvider);
  }

  public static GetReactivoAggregateUseCase newInstance(ReactivoRepository repository) {
    return new GetReactivoAggregateUseCase(repository);
  }
}
