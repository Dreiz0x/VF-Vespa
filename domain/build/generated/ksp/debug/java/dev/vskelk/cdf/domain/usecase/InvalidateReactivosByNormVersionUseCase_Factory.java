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
public final class InvalidateReactivosByNormVersionUseCase_Factory implements Factory<InvalidateReactivosByNormVersionUseCase> {
  private final Provider<ReactivoRepository> repositoryProvider;

  public InvalidateReactivosByNormVersionUseCase_Factory(
      Provider<ReactivoRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public InvalidateReactivosByNormVersionUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static InvalidateReactivosByNormVersionUseCase_Factory create(
      Provider<ReactivoRepository> repositoryProvider) {
    return new InvalidateReactivosByNormVersionUseCase_Factory(repositoryProvider);
  }

  public static InvalidateReactivosByNormVersionUseCase newInstance(ReactivoRepository repository) {
    return new InvalidateReactivosByNormVersionUseCase(repository);
  }
}
