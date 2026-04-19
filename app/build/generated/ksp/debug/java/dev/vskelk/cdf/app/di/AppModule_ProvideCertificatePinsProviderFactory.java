package dev.vskelk.cdf.app.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.network.security.CertificatePinsProvider;
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
public final class AppModule_ProvideCertificatePinsProviderFactory implements Factory<CertificatePinsProvider> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideCertificatePinsProviderFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public CertificatePinsProvider get() {
    return provideCertificatePinsProvider(contextProvider.get());
  }

  public static AppModule_ProvideCertificatePinsProviderFactory create(
      Provider<Context> contextProvider) {
    return new AppModule_ProvideCertificatePinsProviderFactory(contextProvider);
  }

  public static CertificatePinsProvider provideCertificatePinsProvider(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideCertificatePinsProvider(context));
  }
}
