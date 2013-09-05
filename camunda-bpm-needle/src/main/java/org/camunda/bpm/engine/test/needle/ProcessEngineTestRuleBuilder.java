package org.camunda.bpm.engine.test.needle;

import static org.camunda.bpm.engine.test.needle.supplier.CamundaInstancesSupplier.CAMUNDA_CONFIG_RESOURCE;

import java.util.Arrays;
import java.util.Set;

import org.apache.commons.lang3.builder.Builder;

import com.google.common.collect.Sets;

import de.akquinet.jbosscc.needle.injection.InjectionProvider;

public class ProcessEngineTestRuleBuilder implements Builder<ProcessEngineNeedleRule> {

  private final Object testInstance;
  private String configFile = CAMUNDA_CONFIG_RESOURCE;
  private final Set<InjectionProvider<?>> injectionProviders = Sets.newHashSet();

  public ProcessEngineTestRuleBuilder(final Object testInstance) {
    this.testInstance = testInstance;
  }

  public ProcessEngineTestRuleBuilder withConfig(final String configFile) {
    this.configFile = configFile;
    return this;
  }

  public ProcessEngineTestRuleBuilder addInjectionProvider(final InjectionProvider<?>... injectionProviders) {
    this.injectionProviders.addAll(Arrays.asList(injectionProviders));
    return this;
  }

  @Override
  public ProcessEngineNeedleRule build() {
    return new ProcessEngineNeedleRule(testInstance, configFile, injectionProviders);
  }

}
