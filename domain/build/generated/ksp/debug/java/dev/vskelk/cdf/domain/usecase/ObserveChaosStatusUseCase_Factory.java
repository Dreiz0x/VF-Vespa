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
public final class ObserveChaosStatusUseCase_Factory implements Factory<ObserveChaosStatusUseCase> {
  private final Provider<ChaosRepository> repositoryProvider;

  public ObserveChaosStatusUseCase_Factory(Provider<ChaosRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ObserveChaosStatusUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ObserveChaosStatusUseCase_Factory create(
      Provider<ChaosRepository> repositoryProvider) {
    return new ObserveChaosStatusUseCase_Factory(repositoryProvider);
  }

  public static ObserveChaosStatusUseCase newInstance(ChaosRepository repository) {
    return new ObserveChaosStatusUseCase(repository);
  }
}
