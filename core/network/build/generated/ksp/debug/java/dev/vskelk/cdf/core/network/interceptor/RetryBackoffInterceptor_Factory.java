package dev.vskelk.cdf.core.network.interceptor;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.network.resilience.CircuitBreaker;
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
public final class RetryBackoffInterceptor_Factory implements Factory<RetryBackoffInterceptor> {
  private final Provider<CircuitBreaker> circuitBreakerProvider;

  public RetryBackoffInterceptor_Factory(Provider<CircuitBreaker> circuitBreakerProvider) {
    this.circuitBreakerProvider = circuitBreakerProvider;
  }

  @Override
  public RetryBackoffInterceptor get() {
    return newInstance(circuitBreakerProvider.get());
  }

  public static RetryBackoffInterceptor_Factory create(
      Provider<CircuitBreaker> circuitBreakerProvider) {
    return new RetryBackoffInterceptor_Factory(circuitBreakerProvider);
  }

  public static RetryBackoffInterceptor newInstance(CircuitBreaker circuitBreaker) {
    return new RetryBackoffInterceptor(circuitBreaker);
  }
}
