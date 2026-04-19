package dev.vskelk.cdf.app;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.datastore.core.DataStore;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkManager;
import androidx.work.WorkerParameters;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import dev.vskelk.cdf.app.di.AppModule_ProvideCertificatePinsProviderFactory;
import dev.vskelk.cdf.app.di.AppModule_ProvideWorkManagerFactory;
import dev.vskelk.cdf.app.ui.SplashViewModel;
import dev.vskelk.cdf.app.ui.SplashViewModel_HiltModules;
import dev.vskelk.cdf.app.work.PendingSyncScheduler;
import dev.vskelk.cdf.core.common.ConnectivityNetworkMonitor;
import dev.vskelk.cdf.core.database.AppDatabase;
import dev.vskelk.cdf.core.database.dao.CuarentenaDao;
import dev.vskelk.cdf.core.database.dao.MessageDao;
import dev.vskelk.cdf.core.database.dao.NormativeDao;
import dev.vskelk.cdf.core.database.dao.OntologyDao;
import dev.vskelk.cdf.core.database.dao.ReactivoDao;
import dev.vskelk.cdf.core.database.dao.SessionDao;
import dev.vskelk.cdf.core.database.di.DatabaseModule_ProvideCuarentenaDaoFactory;
import dev.vskelk.cdf.core.database.di.DatabaseModule_ProvideDbFactory;
import dev.vskelk.cdf.core.database.di.DatabaseModule_ProvideMessageDaoFactory;
import dev.vskelk.cdf.core.database.di.DatabaseModule_ProvideNormativeDaoFactory;
import dev.vskelk.cdf.core.database.di.DatabaseModule_ProvideOntologyDaoFactory;
import dev.vskelk.cdf.core.database.di.DatabaseModule_ProvideReactivoDaoFactory;
import dev.vskelk.cdf.core.database.di.DatabaseModule_ProvideSessionDaoFactory;
import dev.vskelk.cdf.core.database.di.DatabaseSeeder;
import dev.vskelk.cdf.core.datastore.UserPreferences;
import dev.vskelk.cdf.core.datastore.crypto.CipherService;
import dev.vskelk.cdf.core.datastore.datasource.PreferencesDataSource;
import dev.vskelk.cdf.core.datastore.di.DataStoreModule_ProvideUserPreferencesStoreFactory;
import dev.vskelk.cdf.core.network.api.AnthropicApi;
import dev.vskelk.cdf.core.network.datasource.AnthropicRemoteDataSource;
import dev.vskelk.cdf.core.network.di.NetworkModule_ProvideAnthropicApiFactory;
import dev.vskelk.cdf.core.network.di.NetworkModule_ProvideCertificatePinnerFactory;
import dev.vskelk.cdf.core.network.di.NetworkModule_ProvideJsonFactory;
import dev.vskelk.cdf.core.network.di.NetworkModule_ProvideOkHttpFactory;
import dev.vskelk.cdf.core.network.interceptor.AuthInterceptor;
import dev.vskelk.cdf.core.network.interceptor.DebugLoggingInterceptorFactory;
import dev.vskelk.cdf.core.network.interceptor.RetryBackoffInterceptor;
import dev.vskelk.cdf.core.network.resilience.CircuitBreaker;
import dev.vskelk.cdf.core.network.security.CertificatePinsProvider;
import dev.vskelk.cdf.data.repository.repository.BootstrapRepositoryImpl;
import dev.vskelk.cdf.data.repository.repository.ChaosRepositoryImpl;
import dev.vskelk.cdf.data.repository.repository.ConversationRepositoryImpl;
import dev.vskelk.cdf.data.repository.repository.InvestigadorRepositoryImpl;
import dev.vskelk.cdf.data.repository.repository.OntologyRepositoryImpl;
import dev.vskelk.cdf.data.repository.repository.ReactivoRepositoryImpl;
import dev.vskelk.cdf.data.repository.repository.SettingsRepositoryImpl;
import dev.vskelk.cdf.data.repository.worker.PendingMessagesSyncWorker;
import dev.vskelk.cdf.data.repository.worker.PendingMessagesSyncWorker_AssistedFactory;
import dev.vskelk.cdf.domain.decision.DecisionEngine;
import dev.vskelk.cdf.domain.usecase.AprobarFragmentoUseCase;
import dev.vskelk.cdf.domain.usecase.GetSimulationReactivosUseCase;
import dev.vskelk.cdf.domain.usecase.IngerirDocumentoUseCase;
import dev.vskelk.cdf.domain.usecase.InitializeAppUseCase;
import dev.vskelk.cdf.domain.usecase.InvestigarTemaUseCase;
import dev.vskelk.cdf.domain.usecase.ObservarCuarentenaUseCase;
import dev.vskelk.cdf.domain.usecase.ObserveChaosStatusUseCase;
import dev.vskelk.cdf.domain.usecase.ObserveConversationUseCase;
import dev.vskelk.cdf.domain.usecase.ObserveInterviewNodesUseCase;
import dev.vskelk.cdf.domain.usecase.ObserveReactivoDiagnosticsUseCase;
import dev.vskelk.cdf.domain.usecase.ObserveSettingsUseCase;
import dev.vskelk.cdf.domain.usecase.RechazarFragmentoUseCase;
import dev.vskelk.cdf.domain.usecase.RefreshChaosStatusUseCase;
import dev.vskelk.cdf.domain.usecase.SaveApiKeyUseCase;
import dev.vskelk.cdf.domain.usecase.SendMessageUseCase;
import dev.vskelk.cdf.domain.usecase.SyncPendingMessagesUseCase;
import dev.vskelk.cdf.domain.usecase.ToggleOfflineModeUseCase;
import dev.vskelk.cdf.feature.chaos.ChaosViewModel;
import dev.vskelk.cdf.feature.chaos.ChaosViewModel_HiltModules;
import dev.vskelk.cdf.feature.diagnostico.DiagnosticoViewModel;
import dev.vskelk.cdf.feature.diagnostico.DiagnosticoViewModel_HiltModules;
import dev.vskelk.cdf.feature.entrevista.EntrevistaViewModel;
import dev.vskelk.cdf.feature.entrevista.EntrevistaViewModel_HiltModules;
import dev.vskelk.cdf.feature.investigador.CuarentenaViewModel;
import dev.vskelk.cdf.feature.investigador.CuarentenaViewModel_HiltModules;
import dev.vskelk.cdf.feature.investigador.InvestigadorViewModel;
import dev.vskelk.cdf.feature.investigador.InvestigadorViewModel_HiltModules;
import dev.vskelk.cdf.feature.main.MainViewModel;
import dev.vskelk.cdf.feature.main.MainViewModel_HiltModules;
import dev.vskelk.cdf.feature.simulador.SimuladorViewModel;
import dev.vskelk.cdf.feature.simulador.SimuladorViewModel_HiltModules;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import kotlinx.serialization.json.Json;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;

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
public final class DaggerCdfApp_HiltComponents_SingletonC {
  private DaggerCdfApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public CdfApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements CdfApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public CdfApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements CdfApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public CdfApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements CdfApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public CdfApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements CdfApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public CdfApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements CdfApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public CdfApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements CdfApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public CdfApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements CdfApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public CdfApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends CdfApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends CdfApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends CdfApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends CdfApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(8).put(LazyClassKeyProvider.dev_vskelk_cdf_feature_chaos_ChaosViewModel, ChaosViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.dev_vskelk_cdf_feature_investigador_CuarentenaViewModel, CuarentenaViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.dev_vskelk_cdf_feature_diagnostico_DiagnosticoViewModel, DiagnosticoViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.dev_vskelk_cdf_feature_entrevista_EntrevistaViewModel, EntrevistaViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.dev_vskelk_cdf_feature_investigador_InvestigadorViewModel, InvestigadorViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.dev_vskelk_cdf_feature_main_MainViewModel, MainViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.dev_vskelk_cdf_feature_simulador_SimuladorViewModel, SimuladorViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.dev_vskelk_cdf_app_ui_SplashViewModel, SplashViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
      injectMainActivity2(arg0);
    }

    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectPendingSyncScheduler(instance, singletonCImpl.pendingSyncSchedulerProvider.get());
      MainActivity_MembersInjector.injectDatabaseSeeder(instance, singletonCImpl.databaseSeederProvider.get());
      return instance;
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String dev_vskelk_cdf_feature_chaos_ChaosViewModel = "dev.vskelk.cdf.feature.chaos.ChaosViewModel";

      static String dev_vskelk_cdf_feature_entrevista_EntrevistaViewModel = "dev.vskelk.cdf.feature.entrevista.EntrevistaViewModel";

      static String dev_vskelk_cdf_feature_investigador_InvestigadorViewModel = "dev.vskelk.cdf.feature.investigador.InvestigadorViewModel";

      static String dev_vskelk_cdf_feature_simulador_SimuladorViewModel = "dev.vskelk.cdf.feature.simulador.SimuladorViewModel";

      static String dev_vskelk_cdf_app_ui_SplashViewModel = "dev.vskelk.cdf.app.ui.SplashViewModel";

      static String dev_vskelk_cdf_feature_investigador_CuarentenaViewModel = "dev.vskelk.cdf.feature.investigador.CuarentenaViewModel";

      static String dev_vskelk_cdf_feature_main_MainViewModel = "dev.vskelk.cdf.feature.main.MainViewModel";

      static String dev_vskelk_cdf_feature_diagnostico_DiagnosticoViewModel = "dev.vskelk.cdf.feature.diagnostico.DiagnosticoViewModel";

      @KeepFieldType
      ChaosViewModel dev_vskelk_cdf_feature_chaos_ChaosViewModel2;

      @KeepFieldType
      EntrevistaViewModel dev_vskelk_cdf_feature_entrevista_EntrevistaViewModel2;

      @KeepFieldType
      InvestigadorViewModel dev_vskelk_cdf_feature_investigador_InvestigadorViewModel2;

      @KeepFieldType
      SimuladorViewModel dev_vskelk_cdf_feature_simulador_SimuladorViewModel2;

      @KeepFieldType
      SplashViewModel dev_vskelk_cdf_app_ui_SplashViewModel2;

      @KeepFieldType
      CuarentenaViewModel dev_vskelk_cdf_feature_investigador_CuarentenaViewModel2;

      @KeepFieldType
      MainViewModel dev_vskelk_cdf_feature_main_MainViewModel2;

      @KeepFieldType
      DiagnosticoViewModel dev_vskelk_cdf_feature_diagnostico_DiagnosticoViewModel2;
    }
  }

  private static final class ViewModelCImpl extends CdfApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<ChaosViewModel> chaosViewModelProvider;

    private Provider<CuarentenaViewModel> cuarentenaViewModelProvider;

    private Provider<DiagnosticoViewModel> diagnosticoViewModelProvider;

    private Provider<EntrevistaViewModel> entrevistaViewModelProvider;

    private Provider<InvestigadorViewModel> investigadorViewModelProvider;

    private Provider<MainViewModel> mainViewModelProvider;

    private Provider<SimuladorViewModel> simuladorViewModelProvider;

    private Provider<SplashViewModel> splashViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private ObserveChaosStatusUseCase observeChaosStatusUseCase() {
      return new ObserveChaosStatusUseCase(singletonCImpl.chaosRepositoryImplProvider.get());
    }

    private RefreshChaosStatusUseCase refreshChaosStatusUseCase() {
      return new RefreshChaosStatusUseCase(singletonCImpl.chaosRepositoryImplProvider.get());
    }

    private ObservarCuarentenaUseCase observarCuarentenaUseCase() {
      return new ObservarCuarentenaUseCase(singletonCImpl.investigadorRepositoryImplProvider.get());
    }

    private AprobarFragmentoUseCase aprobarFragmentoUseCase() {
      return new AprobarFragmentoUseCase(singletonCImpl.investigadorRepositoryImplProvider.get());
    }

    private RechazarFragmentoUseCase rechazarFragmentoUseCase() {
      return new RechazarFragmentoUseCase(singletonCImpl.investigadorRepositoryImplProvider.get());
    }

    private ObserveReactivoDiagnosticsUseCase observeReactivoDiagnosticsUseCase() {
      return new ObserveReactivoDiagnosticsUseCase(singletonCImpl.reactivoRepositoryImplProvider.get());
    }

    private ObserveInterviewNodesUseCase observeInterviewNodesUseCase() {
      return new ObserveInterviewNodesUseCase(singletonCImpl.ontologyRepositoryImplProvider.get());
    }

    private InvestigarTemaUseCase investigarTemaUseCase() {
      return new InvestigarTemaUseCase(singletonCImpl.investigadorRepositoryImplProvider.get());
    }

    private IngerirDocumentoUseCase ingerirDocumentoUseCase() {
      return new IngerirDocumentoUseCase(singletonCImpl.investigadorRepositoryImplProvider.get());
    }

    private ObserveConversationUseCase observeConversationUseCase() {
      return new ObserveConversationUseCase(singletonCImpl.conversationRepositoryImplProvider.get());
    }

    private ObserveSettingsUseCase observeSettingsUseCase() {
      return new ObserveSettingsUseCase(singletonCImpl.settingsRepositoryImplProvider.get());
    }

    private SendMessageUseCase sendMessageUseCase() {
      return new SendMessageUseCase(singletonCImpl.conversationRepositoryImplProvider.get(), singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.chaosRepositoryImplProvider.get(), new DecisionEngine());
    }

    private SaveApiKeyUseCase saveApiKeyUseCase() {
      return new SaveApiKeyUseCase(singletonCImpl.settingsRepositoryImplProvider.get());
    }

    private ToggleOfflineModeUseCase toggleOfflineModeUseCase() {
      return new ToggleOfflineModeUseCase(singletonCImpl.settingsRepositoryImplProvider.get());
    }

    private GetSimulationReactivosUseCase getSimulationReactivosUseCase() {
      return new GetSimulationReactivosUseCase(singletonCImpl.reactivoRepositoryImplProvider.get());
    }

    private InitializeAppUseCase initializeAppUseCase() {
      return new InitializeAppUseCase(singletonCImpl.bootstrapRepositoryImplProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.chaosViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.cuarentenaViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.diagnosticoViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.entrevistaViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.investigadorViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.mainViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.simuladorViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.splashViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(8).put(LazyClassKeyProvider.dev_vskelk_cdf_feature_chaos_ChaosViewModel, ((Provider) chaosViewModelProvider)).put(LazyClassKeyProvider.dev_vskelk_cdf_feature_investigador_CuarentenaViewModel, ((Provider) cuarentenaViewModelProvider)).put(LazyClassKeyProvider.dev_vskelk_cdf_feature_diagnostico_DiagnosticoViewModel, ((Provider) diagnosticoViewModelProvider)).put(LazyClassKeyProvider.dev_vskelk_cdf_feature_entrevista_EntrevistaViewModel, ((Provider) entrevistaViewModelProvider)).put(LazyClassKeyProvider.dev_vskelk_cdf_feature_investigador_InvestigadorViewModel, ((Provider) investigadorViewModelProvider)).put(LazyClassKeyProvider.dev_vskelk_cdf_feature_main_MainViewModel, ((Provider) mainViewModelProvider)).put(LazyClassKeyProvider.dev_vskelk_cdf_feature_simulador_SimuladorViewModel, ((Provider) simuladorViewModelProvider)).put(LazyClassKeyProvider.dev_vskelk_cdf_app_ui_SplashViewModel, ((Provider) splashViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String dev_vskelk_cdf_feature_investigador_CuarentenaViewModel = "dev.vskelk.cdf.feature.investigador.CuarentenaViewModel";

      static String dev_vskelk_cdf_feature_entrevista_EntrevistaViewModel = "dev.vskelk.cdf.feature.entrevista.EntrevistaViewModel";

      static String dev_vskelk_cdf_app_ui_SplashViewModel = "dev.vskelk.cdf.app.ui.SplashViewModel";

      static String dev_vskelk_cdf_feature_investigador_InvestigadorViewModel = "dev.vskelk.cdf.feature.investigador.InvestigadorViewModel";

      static String dev_vskelk_cdf_feature_diagnostico_DiagnosticoViewModel = "dev.vskelk.cdf.feature.diagnostico.DiagnosticoViewModel";

      static String dev_vskelk_cdf_feature_main_MainViewModel = "dev.vskelk.cdf.feature.main.MainViewModel";

      static String dev_vskelk_cdf_feature_chaos_ChaosViewModel = "dev.vskelk.cdf.feature.chaos.ChaosViewModel";

      static String dev_vskelk_cdf_feature_simulador_SimuladorViewModel = "dev.vskelk.cdf.feature.simulador.SimuladorViewModel";

      @KeepFieldType
      CuarentenaViewModel dev_vskelk_cdf_feature_investigador_CuarentenaViewModel2;

      @KeepFieldType
      EntrevistaViewModel dev_vskelk_cdf_feature_entrevista_EntrevistaViewModel2;

      @KeepFieldType
      SplashViewModel dev_vskelk_cdf_app_ui_SplashViewModel2;

      @KeepFieldType
      InvestigadorViewModel dev_vskelk_cdf_feature_investigador_InvestigadorViewModel2;

      @KeepFieldType
      DiagnosticoViewModel dev_vskelk_cdf_feature_diagnostico_DiagnosticoViewModel2;

      @KeepFieldType
      MainViewModel dev_vskelk_cdf_feature_main_MainViewModel2;

      @KeepFieldType
      ChaosViewModel dev_vskelk_cdf_feature_chaos_ChaosViewModel2;

      @KeepFieldType
      SimuladorViewModel dev_vskelk_cdf_feature_simulador_SimuladorViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dev.vskelk.cdf.feature.chaos.ChaosViewModel 
          return (T) new ChaosViewModel(viewModelCImpl.observeChaosStatusUseCase(), viewModelCImpl.refreshChaosStatusUseCase());

          case 1: // dev.vskelk.cdf.feature.investigador.CuarentenaViewModel 
          return (T) new CuarentenaViewModel(viewModelCImpl.observarCuarentenaUseCase(), viewModelCImpl.aprobarFragmentoUseCase(), viewModelCImpl.rechazarFragmentoUseCase());

          case 2: // dev.vskelk.cdf.feature.diagnostico.DiagnosticoViewModel 
          return (T) new DiagnosticoViewModel(viewModelCImpl.observeReactivoDiagnosticsUseCase());

          case 3: // dev.vskelk.cdf.feature.entrevista.EntrevistaViewModel 
          return (T) new EntrevistaViewModel(viewModelCImpl.observeInterviewNodesUseCase());

          case 4: // dev.vskelk.cdf.feature.investigador.InvestigadorViewModel 
          return (T) new InvestigadorViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), viewModelCImpl.investigarTemaUseCase(), viewModelCImpl.ingerirDocumentoUseCase(), viewModelCImpl.observarCuarentenaUseCase());

          case 5: // dev.vskelk.cdf.feature.main.MainViewModel 
          return (T) new MainViewModel(viewModelCImpl.observeConversationUseCase(), viewModelCImpl.observeSettingsUseCase(), viewModelCImpl.sendMessageUseCase(), viewModelCImpl.saveApiKeyUseCase(), viewModelCImpl.toggleOfflineModeUseCase());

          case 6: // dev.vskelk.cdf.feature.simulador.SimuladorViewModel 
          return (T) new SimuladorViewModel(viewModelCImpl.getSimulationReactivosUseCase());

          case 7: // dev.vskelk.cdf.app.ui.SplashViewModel 
          return (T) new SplashViewModel(viewModelCImpl.initializeAppUseCase(), singletonCImpl.bootstrapRepositoryImplProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends CdfApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends CdfApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends CdfApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<AppDatabase> provideDbProvider;

    private Provider<DataStore<UserPreferences>> provideUserPreferencesStoreProvider;

    private Provider<CipherService> cipherServiceProvider;

    private Provider<PreferencesDataSource> preferencesDataSourceProvider;

    private Provider<AuthInterceptor> authInterceptorProvider;

    private Provider<CircuitBreaker> circuitBreakerProvider;

    private Provider<RetryBackoffInterceptor> retryBackoffInterceptorProvider;

    private Provider<DebugLoggingInterceptorFactory> debugLoggingInterceptorFactoryProvider;

    private Provider<CertificatePinsProvider> provideCertificatePinsProvider;

    private Provider<CertificatePinner> provideCertificatePinnerProvider;

    private Provider<OkHttpClient> provideOkHttpProvider;

    private Provider<Json> provideJsonProvider;

    private Provider<AnthropicApi> provideAnthropicApiProvider;

    private Provider<ConversationRepositoryImpl> conversationRepositoryImplProvider;

    private Provider<PendingMessagesSyncWorker_AssistedFactory> pendingMessagesSyncWorker_AssistedFactoryProvider;

    private Provider<WorkManager> provideWorkManagerProvider;

    private Provider<PendingSyncScheduler> pendingSyncSchedulerProvider;

    private Provider<DatabaseSeeder> databaseSeederProvider;

    private Provider<ConnectivityNetworkMonitor> connectivityNetworkMonitorProvider;

    private Provider<ChaosRepositoryImpl> chaosRepositoryImplProvider;

    private Provider<InvestigadorRepositoryImpl> investigadorRepositoryImplProvider;

    private Provider<ReactivoRepositoryImpl> reactivoRepositoryImplProvider;

    private Provider<OntologyRepositoryImpl> ontologyRepositoryImplProvider;

    private Provider<SettingsRepositoryImpl> settingsRepositoryImplProvider;

    private Provider<BootstrapRepositoryImpl> bootstrapRepositoryImplProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private MessageDao messageDao() {
      return DatabaseModule_ProvideMessageDaoFactory.provideMessageDao(provideDbProvider.get());
    }

    private SessionDao sessionDao() {
      return DatabaseModule_ProvideSessionDaoFactory.provideSessionDao(provideDbProvider.get());
    }

    private AnthropicRemoteDataSource anthropicRemoteDataSource() {
      return new AnthropicRemoteDataSource(provideAnthropicApiProvider.get());
    }

    private SyncPendingMessagesUseCase syncPendingMessagesUseCase() {
      return new SyncPendingMessagesUseCase(conversationRepositoryImplProvider.get());
    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return Collections.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>singletonMap("dev.vskelk.cdf.data.repository.worker.PendingMessagesSyncWorker", ((Provider) pendingMessagesSyncWorker_AssistedFactoryProvider));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    private CuarentenaDao cuarentenaDao() {
      return DatabaseModule_ProvideCuarentenaDaoFactory.provideCuarentenaDao(provideDbProvider.get());
    }

    private NormativeDao normativeDao() {
      return DatabaseModule_ProvideNormativeDaoFactory.provideNormativeDao(provideDbProvider.get());
    }

    private ReactivoDao reactivoDao() {
      return DatabaseModule_ProvideReactivoDaoFactory.provideReactivoDao(provideDbProvider.get());
    }

    private OntologyDao ontologyDao() {
      return DatabaseModule_ProvideOntologyDaoFactory.provideOntologyDao(provideDbProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDbProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 2));
      this.provideUserPreferencesStoreProvider = DoubleCheck.provider(new SwitchingProvider<DataStore<UserPreferences>>(singletonCImpl, 4));
      this.cipherServiceProvider = DoubleCheck.provider(new SwitchingProvider<CipherService>(singletonCImpl, 5));
      this.preferencesDataSourceProvider = DoubleCheck.provider(new SwitchingProvider<PreferencesDataSource>(singletonCImpl, 3));
      this.authInterceptorProvider = DoubleCheck.provider(new SwitchingProvider<AuthInterceptor>(singletonCImpl, 8));
      this.circuitBreakerProvider = DoubleCheck.provider(new SwitchingProvider<CircuitBreaker>(singletonCImpl, 10));
      this.retryBackoffInterceptorProvider = DoubleCheck.provider(new SwitchingProvider<RetryBackoffInterceptor>(singletonCImpl, 9));
      this.debugLoggingInterceptorFactoryProvider = DoubleCheck.provider(new SwitchingProvider<DebugLoggingInterceptorFactory>(singletonCImpl, 11));
      this.provideCertificatePinsProvider = DoubleCheck.provider(new SwitchingProvider<CertificatePinsProvider>(singletonCImpl, 13));
      this.provideCertificatePinnerProvider = DoubleCheck.provider(new SwitchingProvider<CertificatePinner>(singletonCImpl, 12));
      this.provideOkHttpProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 7));
      this.provideJsonProvider = DoubleCheck.provider(new SwitchingProvider<Json>(singletonCImpl, 14));
      this.provideAnthropicApiProvider = DoubleCheck.provider(new SwitchingProvider<AnthropicApi>(singletonCImpl, 6));
      this.conversationRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ConversationRepositoryImpl>(singletonCImpl, 1));
      this.pendingMessagesSyncWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<PendingMessagesSyncWorker_AssistedFactory>(singletonCImpl, 0));
      this.provideWorkManagerProvider = DoubleCheck.provider(new SwitchingProvider<WorkManager>(singletonCImpl, 16));
      this.pendingSyncSchedulerProvider = DoubleCheck.provider(new SwitchingProvider<PendingSyncScheduler>(singletonCImpl, 15));
      this.databaseSeederProvider = DoubleCheck.provider(new SwitchingProvider<DatabaseSeeder>(singletonCImpl, 17));
      this.connectivityNetworkMonitorProvider = DoubleCheck.provider(new SwitchingProvider<ConnectivityNetworkMonitor>(singletonCImpl, 19));
      this.chaosRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ChaosRepositoryImpl>(singletonCImpl, 18));
      this.investigadorRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<InvestigadorRepositoryImpl>(singletonCImpl, 20));
      this.reactivoRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ReactivoRepositoryImpl>(singletonCImpl, 21));
      this.ontologyRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<OntologyRepositoryImpl>(singletonCImpl, 22));
      this.settingsRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<SettingsRepositoryImpl>(singletonCImpl, 23));
      this.bootstrapRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<BootstrapRepositoryImpl>(singletonCImpl, 24));
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    @Override
    public void injectCdfApp(CdfApp arg0) {
      injectCdfApp2(arg0);
    }

    private CdfApp injectCdfApp2(CdfApp instance) {
      CdfApp_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dev.vskelk.cdf.data.repository.worker.PendingMessagesSyncWorker_AssistedFactory 
          return (T) new PendingMessagesSyncWorker_AssistedFactory() {
            @Override
            public PendingMessagesSyncWorker create(Context appContext, WorkerParameters params) {
              return new PendingMessagesSyncWorker(appContext, params, singletonCImpl.syncPendingMessagesUseCase());
            }
          };

          case 1: // dev.vskelk.cdf.data.repository.repository.ConversationRepositoryImpl 
          return (T) new ConversationRepositoryImpl(singletonCImpl.messageDao(), singletonCImpl.sessionDao(), singletonCImpl.preferencesDataSourceProvider.get(), singletonCImpl.anthropicRemoteDataSource());

          case 2: // dev.vskelk.cdf.core.database.AppDatabase 
          return (T) DatabaseModule_ProvideDbFactory.provideDb(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // dev.vskelk.cdf.core.datastore.datasource.PreferencesDataSource 
          return (T) new PreferencesDataSource(singletonCImpl.provideUserPreferencesStoreProvider.get(), singletonCImpl.cipherServiceProvider.get());

          case 4: // androidx.datastore.core.DataStore<dev.vskelk.cdf.core.datastore.UserPreferences> 
          return (T) DataStoreModule_ProvideUserPreferencesStoreFactory.provideUserPreferencesStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // dev.vskelk.cdf.core.datastore.crypto.CipherService 
          return (T) new CipherService(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 6: // dev.vskelk.cdf.core.network.api.AnthropicApi 
          return (T) NetworkModule_ProvideAnthropicApiFactory.provideAnthropicApi(singletonCImpl.provideOkHttpProvider.get(), singletonCImpl.provideJsonProvider.get());

          case 7: // okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideOkHttpFactory.provideOkHttp(singletonCImpl.authInterceptorProvider.get(), singletonCImpl.retryBackoffInterceptorProvider.get(), singletonCImpl.debugLoggingInterceptorFactoryProvider.get(), singletonCImpl.provideCertificatePinnerProvider.get());

          case 8: // dev.vskelk.cdf.core.network.interceptor.AuthInterceptor 
          return (T) new AuthInterceptor();

          case 9: // dev.vskelk.cdf.core.network.interceptor.RetryBackoffInterceptor 
          return (T) new RetryBackoffInterceptor(singletonCImpl.circuitBreakerProvider.get());

          case 10: // dev.vskelk.cdf.core.network.resilience.CircuitBreaker 
          return (T) new CircuitBreaker();

          case 11: // dev.vskelk.cdf.core.network.interceptor.DebugLoggingInterceptorFactory 
          return (T) new DebugLoggingInterceptorFactory();

          case 12: // okhttp3.CertificatePinner 
          return (T) NetworkModule_ProvideCertificatePinnerFactory.provideCertificatePinner(singletonCImpl.provideCertificatePinsProvider.get());

          case 13: // dev.vskelk.cdf.core.network.security.CertificatePinsProvider 
          return (T) AppModule_ProvideCertificatePinsProviderFactory.provideCertificatePinsProvider(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 14: // kotlinx.serialization.json.Json 
          return (T) NetworkModule_ProvideJsonFactory.provideJson();

          case 15: // dev.vskelk.cdf.app.work.PendingSyncScheduler 
          return (T) new PendingSyncScheduler(singletonCImpl.provideWorkManagerProvider.get());

          case 16: // androidx.work.WorkManager 
          return (T) AppModule_ProvideWorkManagerFactory.provideWorkManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 17: // dev.vskelk.cdf.core.database.di.DatabaseSeeder 
          return (T) new DatabaseSeeder(singletonCImpl.provideDbProvider.get());

          case 18: // dev.vskelk.cdf.data.repository.repository.ChaosRepositoryImpl 
          return (T) new ChaosRepositoryImpl(singletonCImpl.connectivityNetworkMonitorProvider.get(), singletonCImpl.messageDao(), singletonCImpl.circuitBreakerProvider.get());

          case 19: // dev.vskelk.cdf.core.common.ConnectivityNetworkMonitor 
          return (T) new ConnectivityNetworkMonitor(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 20: // dev.vskelk.cdf.data.repository.repository.InvestigadorRepositoryImpl 
          return (T) new InvestigadorRepositoryImpl(singletonCImpl.anthropicRemoteDataSource(), singletonCImpl.cuarentenaDao(), singletonCImpl.normativeDao(), singletonCImpl.preferencesDataSourceProvider.get());

          case 21: // dev.vskelk.cdf.data.repository.repository.ReactivoRepositoryImpl 
          return (T) new ReactivoRepositoryImpl(singletonCImpl.reactivoDao());

          case 22: // dev.vskelk.cdf.data.repository.repository.OntologyRepositoryImpl 
          return (T) new OntologyRepositoryImpl(singletonCImpl.ontologyDao());

          case 23: // dev.vskelk.cdf.data.repository.repository.SettingsRepositoryImpl 
          return (T) new SettingsRepositoryImpl(singletonCImpl.preferencesDataSourceProvider.get());

          case 24: // dev.vskelk.cdf.data.repository.repository.BootstrapRepositoryImpl 
          return (T) new BootstrapRepositoryImpl(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.preferencesDataSourceProvider.get(), singletonCImpl.normativeDao(), singletonCImpl.ontologyDao(), singletonCImpl.reactivoDao());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
