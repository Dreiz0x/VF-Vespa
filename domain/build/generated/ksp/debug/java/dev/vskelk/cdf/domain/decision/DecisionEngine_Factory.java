package dev.vskelk.cdf.domain.decision;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
    "cast"
})
public final class DecisionEngine_Factory implements Factory<DecisionEngine> {
  @Override
  public DecisionEngine get() {
    return newInstance();
  }

  public static DecisionEngine_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DecisionEngine newInstance() {
    return new DecisionEngine();
  }

  private static final class InstanceHolder {
    private static final DecisionEngine_Factory INSTANCE = new DecisionEngine_Factory();
  }
}
