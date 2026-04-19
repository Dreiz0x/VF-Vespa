package dev.vskelk.cdf.core.network.interceptor;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
    "cast"
})
public final class DebugLoggingInterceptorFactory_Factory implements Factory<DebugLoggingInterceptorFactory> {
  @Override
  public DebugLoggingInterceptorFactory get() {
    return newInstance();
  }

  public static DebugLoggingInterceptorFactory_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DebugLoggingInterceptorFactory newInstance() {
    return new DebugLoggingInterceptorFactory();
  }

  private static final class InstanceHolder {
    private static final DebugLoggingInterceptorFactory_Factory INSTANCE = new DebugLoggingInterceptorFactory_Factory();
  }
}
