package dev.vskelk.cdf.data.repository.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.common.NetworkMonitor;
import dev.vskelk.cdf.core.database.dao.MessageDao;
import dev.vskelk.cdf.core.network.resilience.CircuitBreaker;
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
public final class ChaosRepositoryImpl_Factory implements Factory<ChaosRepositoryImpl> {
  private final Provider<NetworkMonitor> networkMonitorProvider;

  private final Provider<MessageDao> messageDaoProvider;

  private final Provider<CircuitBreaker> circuitBreakerProvider;

  public ChaosRepositoryImpl_Factory(Provider<NetworkMonitor> networkMonitorProvider,
      Provider<MessageDao> messageDaoProvider, Provider<CircuitBreaker> circuitBreakerProvider) {
    this.networkMonitorProvider = networkMonitorProvider;
    this.messageDaoProvider = messageDaoProvider;
    this.circuitBreakerProvider = circuitBreakerProvider;
  }

  @Override
  public ChaosRepositoryImpl get() {
    return newInstance(networkMonitorProvider.get(), messageDaoProvider.get(), circuitBreakerProvider.get());
  }

  public static ChaosRepositoryImpl_Factory create(Provider<NetworkMonitor> networkMonitorProvider,
      Provider<MessageDao> messageDaoProvider, Provider<CircuitBreaker> circuitBreakerProvider) {
    return new ChaosRepositoryImpl_Factory(networkMonitorProvider, messageDaoProvider, circuitBreakerProvider);
  }

  public static ChaosRepositoryImpl newInstance(NetworkMonitor networkMonitor,
      MessageDao messageDao, CircuitBreaker circuitBreaker) {
    return new ChaosRepositoryImpl(networkMonitor, messageDao, circuitBreaker);
  }
}
