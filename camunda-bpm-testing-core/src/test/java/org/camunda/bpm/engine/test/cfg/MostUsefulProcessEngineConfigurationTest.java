package org.camunda.bpm.engine.test.cfg;

import static org.camunda.bpm.engine.test.cfg.MostUsefulProcessEngineConfiguration.mostUsefulProcessEngineConfiguration;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.repository.Deployment;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;

public class MostUsefulProcessEngineConfigurationTest {

  private final MostUsefulProcessEngineConfiguration configuration = mostUsefulProcessEngineConfiguration();

  private ProcessEngine processEngine;

  @Test
  public void shouldInitWithEmptyBpmnParserListenerList() {
    final List<BpmnParseListener> customPostBPMNParseListeners = configuration.getCustomPostBPMNParseListeners();
    assertThat(customPostBPMNParseListeners, IsEmptyCollection.empty());
  }

  /**
   * without an explicit parse listener, user tasks with no form key are
   * deployed.
   */
  @Test
  public void shouldBuildProcessEngineAndDeployProcess() throws Exception {
    processEngine = configuration.buildProcessEngine();
    buildEngineAndDeploy("no_form_key.bpmn");
  }

  private Deployment buildEngineAndDeploy(final String resource) {
    return processEngine.getRepositoryService().createDeployment().addClasspathResource(resource).deploy();
  }

}
