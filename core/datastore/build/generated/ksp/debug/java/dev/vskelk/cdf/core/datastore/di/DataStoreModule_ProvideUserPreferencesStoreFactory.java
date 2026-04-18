package dev.vskelk.cdf.core.datastore.di;

import android.content.Context;
import androidx.datastore.core.DataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.datastore.UserPreferences;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DataStoreModule_ProvideUserPreferencesStoreFactory implements Factory<DataStore<UserPreferences>> {
  private final Provider<Context> contextProvider;

  public DataStoreModule_ProvideUserPreferencesStoreFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public DataStore<UserPreferences> get() {
    return provideUserPreferencesStore(contextProvider.get());
  }

  public static DataStoreModule_ProvideUserPreferencesStoreFactory create(
      Provider<Context> contextProvider) {
    return new DataStoreModule_ProvideUserPreferencesStoreFactory(contextProvider);
  }

  public static DataStore<UserPreferences> provideUserPreferencesStore(Context context) {
    return Preconditions.checkNotNullFromProvides(DataStoreModule.INSTANCE.provideUserPreferencesStore(context));
  }
}
