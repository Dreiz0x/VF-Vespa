package dev.vskelk.cdf.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.decision.DecisionEngine;
import dev.vskelk.cdf.domain.repository.ChaosRepository;
import dev.vskelk.cdf.domain.repository.ConversationRepository;
import dev.vskelk.cdf.domain.repository.SettingsRepository;
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
public final class SendMessageUseCase_Factory implements Factory<SendMessageUseCase> {
  private final Provider<ConversationRepository> conversationRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<ChaosRepository> chaosRepositoryProvider;

  private final Provider<DecisionEngine> decisionEngineProvider;

  public SendMessageUseCase_Factory(Provider<ConversationRepository> conversationRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ChaosRepository> chaosRepositoryProvider,
      Provider<DecisionEngine> decisionEngineProvider) {
    this.conversationRepositoryProvider = conversationRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.chaosRepositoryProvider = chaosRepositoryProvider;
    this.decisionEngineProvider = decisionEngineProvider;
  }

  @Override
  public SendMessageUseCase get() {
    return newInstance(conversationRepositoryProvider.get(), settingsRepositoryProvider.get(), chaosRepositoryProvider.get(), decisionEngineProvider.get());
  }

  public static SendMessageUseCase_Factory create(
      Provider<ConversationRepository> conversationRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ChaosRepository> chaosRepositoryProvider,
      Provider<DecisionEngine> decisionEngineProvider) {
    return new SendMessageUseCase_Factory(conversationRepositoryProvider, settingsRepositoryProvider, chaosRepositoryProvider, decisionEngineProvider);
  }

  public static SendMessageUseCase newInstance(ConversationRepository conversationRepository,
      SettingsRepository settingsRepository, ChaosRepository chaosRepository,
      DecisionEngine decisionEngine) {
    return new SendMessageUseCase(conversationRepository, settingsRepository, chaosRepository, decisionEngine);
  }
}
