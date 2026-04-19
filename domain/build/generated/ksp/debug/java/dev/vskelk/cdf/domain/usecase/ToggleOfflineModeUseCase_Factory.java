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
public final class ToggleOfflineModeUseCase_Factory implements Factory<ToggleOfflineModeUseCase> {
  private final Provider<SettingsRepository> repositoryProvider;

  public ToggleOfflineModeUseCase_Factory(Provider<SettingsRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ToggleOfflineModeUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ToggleOfflineModeUseCase_Factory create(
      Provider<SettingsRepository> repositoryProvider) {
    return new ToggleOfflineModeUseCase_Factory(repositoryProvider);
  }

  public static ToggleOfflineModeUseCase newInstance(SettingsRepository repository) {
    return new ToggleOfflineModeUseCase(repository);
  }
}
