package dev.vskelk.cdf.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.domain.repository.ConversationRepository;
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
public final class ObserveConversationUseCase_Factory implements Factory<ObserveConversationUseCase> {
  private final Provider<ConversationRepository> repositoryProvider;

  public ObserveConversationUseCase_Factory(Provider<ConversationRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ObserveConversationUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ObserveConversationUseCase_Factory create(
      Provider<ConversationRepository> repositoryProvider) {
    return new ObserveConversationUseCase_Factory(repositoryProvider);
  }

  public static ObserveConversationUseCase newInstance(ConversationRepository repository) {
    return new ObserveConversationUseCase(repository);
  }
}
