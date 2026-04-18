package dev.vskelk.cdf.core.datastore.datasource;

import androidx.datastore.core.DataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.datastore.UserPreferences;
import dev.vskelk.cdf.core.datastore.crypto.CipherService;
import javax.annotation.processing.Generated;

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
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class PreferencesDataSource_Factory implements Factory<PreferencesDataSource> {
  private final Provider<DataStore<UserPreferences>> dataStoreProvider;

  private final Provider<CipherService> cipherServiceProvider;

  public PreferencesDataSource_Factory(Provider<DataStore<UserPreferences>> dataStoreProvider,
      Provider<CipherService> cipherServiceProvider) {
    this.dataStoreProvider = dataStoreProvider;
    this.cipherServiceProvider = cipherServiceProvider;
  }

  @Override
  public PreferencesDataSource get() {
    return newInstance(dataStoreProvider.get(), cipherServiceProvider.get());
  }

  public static PreferencesDataSource_Factory create(
      Provider<DataStore<UserPreferences>> dataStoreProvider,
      Provider<CipherService> cipherServiceProvider) {
    return new PreferencesDataSource_Factory(dataStoreProvider, cipherServiceProvider);
  }

  public static PreferencesDataSource newInstance(DataStore<UserPreferences> dataStore,
      CipherService cipherService) {
    return new PreferencesDataSource(dataStore, cipherService);
  }
}
