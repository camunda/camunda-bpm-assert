package org.camunda.bpm.engine.test.function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.test.cfg.MostUsefulProcessEngineConfiguration;
import org.junit.Before;
import org.junit.Test;

public class GetProcessEngineConfigurationTest {

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_fail_for_null() {
    GetProcessEngineConfiguration.INSTANCE.apply(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_fail_for_not_processEngineImpl() {
    GetProcessEngineConfiguration.INSTANCE.apply(mock(ProcessEngine.class));
  }

  @Test
  public void should_return_configuration() {
    final MostUsefulProcessEngineConfiguration configuration = MostUsefulProcessEngineConfiguration.mostUsefulProcessEngineConfiguration();
    final ProcessEngine processEngine = configuration.buildProcessEngine();

    assertThat(GetProcessEngineConfiguration.INSTANCE.apply(processEngine), is((ProcessEngineConfiguration) configuration));

  }
}
