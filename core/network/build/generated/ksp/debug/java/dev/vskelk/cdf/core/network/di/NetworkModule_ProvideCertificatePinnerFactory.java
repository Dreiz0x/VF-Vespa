package dev.vskelk.cdf.core.network.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.network.security.CertificatePinsProvider;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.CertificatePinner;

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
public final class NetworkModule_ProvideCertificatePinnerFactory implements Factory<CertificatePinner> {
  private final Provider<CertificatePinsProvider> pinsProvider;

  public NetworkModule_ProvideCertificatePinnerFactory(
      Provider<CertificatePinsProvider> pinsProvider) {
    this.pinsProvider = pinsProvider;
  }

  @Override
  public CertificatePinner get() {
    return provideCertificatePinner(pinsProvider.get());
  }

  public static NetworkModule_ProvideCertificatePinnerFactory create(
      Provider<CertificatePinsProvider> pinsProvider) {
    return new NetworkModule_ProvideCertificatePinnerFactory(pinsProvider);
  }

  public static CertificatePinner provideCertificatePinner(CertificatePinsProvider pinsProvider) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideCertificatePinner(pinsProvider));
  }
}
