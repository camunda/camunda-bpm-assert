package org.camunda.bpm.engine.impl.bpmn.parser;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.test.cfg.MostUsefulProcessEngineConfiguration;
import org.camunda.bpm.engine.test.predicate.TaskDefinitionPredicate;
import org.junit.Before;
import org.junit.Test;

public class TaskDefinitionBpmnParseListenerTest {

  private final MostUsefulProcessEngineConfiguration configuration = new MostUsefulProcessEngineConfiguration();
  private final TaskDefinitionBpmnParseListener formKeyConstraintBpmnParseListener = TaskDefinitionBpmnParseListener.assertTaskDefinitionConstraints(
      TaskDefinitionPredicate.candidateGroupsIsSet(), TaskDefinitionPredicate.formkeyIsSet());
  private ProcessEngine processEngine;

  @Before
  public void setUp() throws Exception {
    configuration.addCustomPostBpmnParseListener(formKeyConstraintBpmnParseListener);
    processEngine = configuration.buildProcessEngine();
  }

  @Test(expected = AssertionError.class)
  public void shouldFailDeploymentWithMissingFormKey() throws Exception {

    buildEngineAndDeploy("no_form_key.bpmn");

  }

  private Deployment buildEngineAndDeploy(final String resource) {
    return processEngine.getRepositoryService().createDeployment().addClasspathResource(resource).deploy();
  }
}
