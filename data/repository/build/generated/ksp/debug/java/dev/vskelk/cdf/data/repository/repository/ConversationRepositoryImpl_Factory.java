package dev.vskelk.cdf.data.repository.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import dev.vskelk.cdf.core.database.dao.MessageDao;
import dev.vskelk.cdf.core.database.dao.SessionDao;
import dev.vskelk.cdf.core.datastore.datasource.PreferencesDataSource;
import dev.vskelk.cdf.core.network.datasource.LlmRemoteDataSource;
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
public final class ConversationRepositoryImpl_Factory implements Factory<ConversationRepositoryImpl> {
  private final Provider<MessageDao> messageDaoProvider;

  private final Provider<SessionDao> sessionDaoProvider;

  private final Provider<PreferencesDataSource> preferencesProvider;

  private final Provider<LlmRemoteDataSource> llmRemoteDataSourceProvider;

  public ConversationRepositoryImpl_Factory(Provider<MessageDao> messageDaoProvider,
      Provider<SessionDao> sessionDaoProvider, Provider<PreferencesDataSource> preferencesProvider,
      Provider<LlmRemoteDataSource> llmRemoteDataSourceProvider) {
    this.messageDaoProvider = messageDaoProvider;
    this.sessionDaoProvider = sessionDaoProvider;
    this.preferencesProvider = preferencesProvider;
    this.llmRemoteDataSourceProvider = llmRemoteDataSourceProvider;
  }

  @Override
  public ConversationRepositoryImpl get() {
    return newInstance(messageDaoProvider.get(), sessionDaoProvider.get(), preferencesProvider.get(), llmRemoteDataSourceProvider.get());
  }

  public static ConversationRepositoryImpl_Factory create(Provider<MessageDao> messageDaoProvider,
      Provider<SessionDao> sessionDaoProvider, Provider<PreferencesDataSource> preferencesProvider,
      Provider<LlmRemoteDataSource> llmRemoteDataSourceProvider) {
    return new ConversationRepositoryImpl_Factory(messageDaoProvider, sessionDaoProvider, preferencesProvider, llmRemoteDataSourceProvider);
  }

  public static ConversationRepositoryImpl newInstance(MessageDao messageDao, SessionDao sessionDao,
      PreferencesDataSource preferences, LlmRemoteDataSource llmRemoteDataSource) {
    return new ConversationRepositoryImpl(messageDao, sessionDao, preferences, llmRemoteDataSource);
  }
}
