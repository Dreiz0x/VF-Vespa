package dev.vskelk.cdf.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.repository.ChaosRepository;
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
public final class RefreshChaosStatusUseCase_Factory implements Factory<RefreshChaosStatusUseCase> {
  private final Provider<ChaosRepository> repositoryProvider;

  public RefreshChaosStatusUseCase_Factory(Provider<ChaosRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public RefreshChaosStatusUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static RefreshChaosStatusUseCase_Factory create(
      Provider<ChaosRepository> repositoryProvider) {
    return new RefreshChaosStatusUseCase_Factory(repositoryProvider);
  }

  public static RefreshChaosStatusUseCase newInstance(ChaosRepository repository) {
    return new RefreshChaosStatusUseCase(repository);
  }
}
