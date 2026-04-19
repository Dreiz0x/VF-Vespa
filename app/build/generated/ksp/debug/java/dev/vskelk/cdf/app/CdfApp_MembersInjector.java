package dev.vskelk.cdf.app;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class CdfApp_MembersInjector implements MembersInjector<CdfApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public CdfApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<CdfApp> create(Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new CdfApp_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(CdfApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("dev.vskelk.cdf.app.CdfApp.workerFactory")
  public static void injectWorkerFactory(CdfApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
