package dev.vskelk.cdf.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.repository.ConversationRepository;
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
public final class SyncPendingMessagesUseCase_Factory implements Factory<SyncPendingMessagesUseCase> {
  private final Provider<ConversationRepository> repositoryProvider;

  public SyncPendingMessagesUseCase_Factory(Provider<ConversationRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SyncPendingMessagesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SyncPendingMessagesUseCase_Factory create(
      Provider<ConversationRepository> repositoryProvider) {
    return new SyncPendingMessagesUseCase_Factory(repositoryProvider);
  }

  public static SyncPendingMessagesUseCase newInstance(ConversationRepository repository) {
    return new SyncPendingMessagesUseCase(repository);
  }
}
