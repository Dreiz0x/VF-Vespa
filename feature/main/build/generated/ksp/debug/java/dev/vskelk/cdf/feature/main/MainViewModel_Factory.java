package dev.vskelk.cdf.feature.main;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.usecase.ObserveConversationUseCase;
import dev.vskelk.cdf.domain.usecase.ObserveSettingsUseCase;
import dev.vskelk.cdf.domain.usecase.SaveApiKeyUseCase;
import dev.vskelk.cdf.domain.usecase.SendMessageUseCase;
import dev.vskelk.cdf.domain.usecase.ToggleOfflineModeUseCase;
import javax.annotation.processing.Generated;

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
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<ObserveConversationUseCase> observeConversationUseCaseProvider;

  private final Provider<ObserveSettingsUseCase> observeSettingsUseCaseProvider;

  private final Provider<SendMessageUseCase> sendMessageUseCaseProvider;

  private final Provider<SaveApiKeyUseCase> saveApiKeyUseCaseProvider;

  private final Provider<ToggleOfflineModeUseCase> toggleOfflineModeUseCaseProvider;

  public MainViewModel_Factory(
      Provider<ObserveConversationUseCase> observeConversationUseCaseProvider,
      Provider<ObserveSettingsUseCase> observeSettingsUseCaseProvider,
      Provider<SendMessageUseCase> sendMessageUseCaseProvider,
      Provider<SaveApiKeyUseCase> saveApiKeyUseCaseProvider,
      Provider<ToggleOfflineModeUseCase> toggleOfflineModeUseCaseProvider) {
    this.observeConversationUseCaseProvider = observeConversationUseCaseProvider;
    this.observeSettingsUseCaseProvider = observeSettingsUseCaseProvider;
    this.sendMessageUseCaseProvider = sendMessageUseCaseProvider;
    this.saveApiKeyUseCaseProvider = saveApiKeyUseCaseProvider;
    this.toggleOfflineModeUseCaseProvider = toggleOfflineModeUseCaseProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(observeConversationUseCaseProvider.get(), observeSettingsUseCaseProvider.get(), sendMessageUseCaseProvider.get(), saveApiKeyUseCaseProvider.get(), toggleOfflineModeUseCaseProvider.get());
  }

  public static MainViewModel_Factory create(
      Provider<ObserveConversationUseCase> observeConversationUseCaseProvider,
      Provider<ObserveSettingsUseCase> observeSettingsUseCaseProvider,
      Provider<SendMessageUseCase> sendMessageUseCaseProvider,
      Provider<SaveApiKeyUseCase> saveApiKeyUseCaseProvider,
      Provider<ToggleOfflineModeUseCase> toggleOfflineModeUseCaseProvider) {
    return new MainViewModel_Factory(observeConversationUseCaseProvider, observeSettingsUseCaseProvider, sendMessageUseCaseProvider, saveApiKeyUseCaseProvider, toggleOfflineModeUseCaseProvider);
  }

  public static MainViewModel newInstance(ObserveConversationUseCase observeConversationUseCase,
      ObserveSettingsUseCase observeSettingsUseCase, SendMessageUseCase sendMessageUseCase,
      SaveApiKeyUseCase saveApiKeyUseCase, ToggleOfflineModeUseCase toggleOfflineModeUseCase) {
    return new MainViewModel(observeConversationUseCase, observeSettingsUseCase, sendMessageUseCase, saveApiKeyUseCase, toggleOfflineModeUseCase);
  }
}
