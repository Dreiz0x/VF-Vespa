package dev.vskelk.cdf.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.repository.SettingsRepository;
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
public final class ObserveSettingsUseCase_Factory implements Factory<ObserveSettingsUseCase> {
  private final Provider<SettingsRepository> repositoryProvider;

  public ObserveSettingsUseCase_Factory(Provider<SettingsRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ObserveSettingsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ObserveSettingsUseCase_Factory create(
      Provider<SettingsRepository> repositoryProvider) {
    return new ObserveSettingsUseCase_Factory(repositoryProvider);
  }

  public static ObserveSettingsUseCase newInstance(SettingsRepository repository) {
    return new ObserveSettingsUseCase(repository);
  }
}
