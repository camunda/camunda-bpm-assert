package org.camunda.bpm.engine.test.needle;

import static org.camunda.bpm.engine.test.cfg.MostUsefulProcessEngineConfiguration.mostUsefulProcessEngineConfiguration;
import static com.google.common.base.Preconditions.checkArgument;

import java.util.Arrays;
import java.util.Set;

import org.apache.commons.lang3.builder.Builder;
import org.camunda.bpm.engine.ProcessEngineConfiguration;

import com.google.common.collect.Sets;

import de.akquinet.jbosscc.needle.injection.InjectionProvider;

/**
 * Builder to create and configure instances of {@link ProcessEngineNeedleRule}.
 */
public class ProcessEngineNeedleRuleBuilder implements Builder<ProcessEngineNeedleRule> {

  private final Object testInstance;
  private ProcessEngineConfiguration configuration = mostUsefulProcessEngineConfiguration();
  private final Set<InjectionProvider<?>> injectionProviders = Sets.newHashSet();

  public ProcessEngineNeedleRuleBuilder(final Object testInstance) {
    this.testInstance = testInstance;
  }

  public ProcessEngineNeedleRuleBuilder withConfiguration(final ProcessEngineConfiguration configuration) {
    checkArgument(configuration != null, "configuration must not be null!");
    this.configuration = configuration;
    return this;
  }

  public ProcessEngineNeedleRuleBuilder withConfiguration(final String configurationFile) {
    return withConfiguration(ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(configurationFile));
  }

  public ProcessEngineNeedleRuleBuilder addInjectionProvider(final InjectionProvider<?>... injectionProviders) {
    this.injectionProviders.addAll(Arrays.asList(injectionProviders));
    return this;
  }

  @Override
  public ProcessEngineNeedleRule build() {
    return new ProcessEngineNeedleRule(testInstance, configuration, injectionProviders);
  }

}
