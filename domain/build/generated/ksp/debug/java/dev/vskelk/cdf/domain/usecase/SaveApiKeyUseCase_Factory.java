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
public final class SaveApiKeyUseCase_Factory implements Factory<SaveApiKeyUseCase> {
  private final Provider<SettingsRepository> repositoryProvider;

  public SaveApiKeyUseCase_Factory(Provider<SettingsRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SaveApiKeyUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SaveApiKeyUseCase_Factory create(Provider<SettingsRepository> repositoryProvider) {
    return new SaveApiKeyUseCase_Factory(repositoryProvider);
  }

  public static SaveApiKeyUseCase newInstance(SettingsRepository repository) {
    return new SaveApiKeyUseCase(repository);
  }
}
