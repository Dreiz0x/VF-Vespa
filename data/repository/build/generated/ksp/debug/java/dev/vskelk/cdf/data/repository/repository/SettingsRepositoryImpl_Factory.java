package dev.vskelk.cdf.data.repository.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.datastore.datasource.PreferencesDataSource;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class SettingsRepositoryImpl_Factory implements Factory<SettingsRepositoryImpl> {
  private final Provider<PreferencesDataSource> preferencesProvider;

  public SettingsRepositoryImpl_Factory(Provider<PreferencesDataSource> preferencesProvider) {
    this.preferencesProvider = preferencesProvider;
  }

  @Override
  public SettingsRepositoryImpl get() {
    return newInstance(preferencesProvider.get());
  }

  public static SettingsRepositoryImpl_Factory create(
      Provider<PreferencesDataSource> preferencesProvider) {
    return new SettingsRepositoryImpl_Factory(preferencesProvider);
  }

  public static SettingsRepositoryImpl newInstance(PreferencesDataSource preferences) {
    return new SettingsRepositoryImpl(preferences);
  }
}
