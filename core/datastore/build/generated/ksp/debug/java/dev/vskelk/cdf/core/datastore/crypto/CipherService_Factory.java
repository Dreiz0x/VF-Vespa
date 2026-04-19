package dev.vskelk.cdf.core.datastore.crypto;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
    "cast"
})
public final class CipherService_Factory implements Factory<CipherService> {
  private final Provider<Context> contextProvider;

  public CipherService_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public CipherService get() {
    return newInstance(contextProvider.get());
  }

  public static CipherService_Factory create(Provider<Context> contextProvider) {
    return new CipherService_Factory(contextProvider);
  }

  public static CipherService newInstance(Context context) {
    return new CipherService(context);
  }
}
