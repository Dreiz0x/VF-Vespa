package dev.vskelk.cdf.core.network.datasource;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.network.api.AnthropicApi;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class AnthropicRemoteDataSource_Factory implements Factory<AnthropicRemoteDataSource> {
  private final Provider<AnthropicApi> apiProvider;

  public AnthropicRemoteDataSource_Factory(Provider<AnthropicApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public AnthropicRemoteDataSource get() {
    return newInstance(apiProvider.get());
  }

  public static AnthropicRemoteDataSource_Factory create(Provider<AnthropicApi> apiProvider) {
    return new AnthropicRemoteDataSource_Factory(apiProvider);
  }

  public static AnthropicRemoteDataSource newInstance(AnthropicApi api) {
    return new AnthropicRemoteDataSource(api);
  }
}
