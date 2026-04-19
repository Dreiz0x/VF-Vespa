package dev.vskelk.cdf.core.network.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.network.interceptor.AuthInterceptor;
import dev.vskelk.cdf.core.network.interceptor.DebugLoggingInterceptorFactory;
import dev.vskelk.cdf.core.network.interceptor.RetryBackoffInterceptor;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;

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
public final class NetworkModule_ProvideOkHttpFactory implements Factory<OkHttpClient> {
  private final Provider<AuthInterceptor> authInterceptorProvider;

  private final Provider<RetryBackoffInterceptor> retryBackoffInterceptorProvider;

  private final Provider<DebugLoggingInterceptorFactory> debugLoggingInterceptorFactoryProvider;

  private final Provider<CertificatePinner> certificatePinnerProvider;

  public NetworkModule_ProvideOkHttpFactory(Provider<AuthInterceptor> authInterceptorProvider,
      Provider<RetryBackoffInterceptor> retryBackoffInterceptorProvider,
      Provider<DebugLoggingInterceptorFactory> debugLoggingInterceptorFactoryProvider,
      Provider<CertificatePinner> certificatePinnerProvider) {
    this.authInterceptorProvider = authInterceptorProvider;
    this.retryBackoffInterceptorProvider = retryBackoffInterceptorProvider;
    this.debugLoggingInterceptorFactoryProvider = debugLoggingInterceptorFactoryProvider;
    this.certificatePinnerProvider = certificatePinnerProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttp(authInterceptorProvider.get(), retryBackoffInterceptorProvider.get(), debugLoggingInterceptorFactoryProvider.get(), certificatePinnerProvider.get());
  }

  public static NetworkModule_ProvideOkHttpFactory create(
      Provider<AuthInterceptor> authInterceptorProvider,
      Provider<RetryBackoffInterceptor> retryBackoffInterceptorProvider,
      Provider<DebugLoggingInterceptorFactory> debugLoggingInterceptorFactoryProvider,
      Provider<CertificatePinner> certificatePinnerProvider) {
    return new NetworkModule_ProvideOkHttpFactory(authInterceptorProvider, retryBackoffInterceptorProvider, debugLoggingInterceptorFactoryProvider, certificatePinnerProvider);
  }

  public static OkHttpClient provideOkHttp(AuthInterceptor authInterceptor,
      RetryBackoffInterceptor retryBackoffInterceptor,
      DebugLoggingInterceptorFactory debugLoggingInterceptorFactory,
      CertificatePinner certificatePinner) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOkHttp(authInterceptor, retryBackoffInterceptor, debugLoggingInterceptorFactory, certificatePinner));
  }
}
