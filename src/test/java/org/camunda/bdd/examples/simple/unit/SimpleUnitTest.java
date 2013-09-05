package org.camunda.bdd.examples.simple.unit;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import javax.inject.Inject;

import org.camunda.bdd.examples.simple.SimpleProcess;
import org.camunda.bdd.examples.simple.SimpleProcessAdapter;
import org.camunda.bdd.examples.simple.SimpleProcess.Elements;
import org.camunda.bdd.examples.simple.SimpleProcess.Events;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.test.needle.ProcessEngineNeedleRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Deployment unit test of simple process.
 * 
 * @author Simon Zambrovski, Holisticon AG.
 */
public class SimpleUnitTest {

  @Rule
  public ProcessEngineNeedleRule processEngine = ProcessEngineNeedleRule.fluentNeedleRule(this).build();

  @Inject
  private SimpleProcessAdapter simpleProcessAdapter;

  class Glue {

    private String processInstanceId;

    public void loadContractData(final boolean isAutomatically) {
      when(simpleProcessAdapter.loadContractData()).thenReturn(isAutomatically);
    }

    public void startSimpleProcess() {
      final ProcessInstance processInstance = processEngine.startProcessInstanceByKey(SimpleProcess.PROCESS);
      assertNotNull(processInstance);
      processInstanceId = processInstance.getProcessInstanceId();
    }

    public void processAutomatically(final boolean withErrors) {
      if (withErrors) {
        doThrow(new BpmnError(Events.ERROR_PROCESS_AUTOMATICALLY_FAILED)).when(simpleProcessAdapter).processContract();
      }
    }

    /**
     * Assert that process execution has run through the activity with given id.
     * 
     * @param name
     *          name of the activity.
     */
    private void assertActivityVisitedOnce(final String name) {

      final HistoricActivityInstance singleResult = processEngine.getHistoryService().createHistoricActivityInstanceQuery().finished().activityId(name)
          .singleResult();
      assertThat("activity '" + name + "' not found!", singleResult, notNullValue());
    }

    /**
     * Assert process end event.
     * 
     * @param name
     *          name of the end event.
     */
    private void assertEndEvent(final String name) {
      assertActivityVisitedOnce(name);
      processEngine.assertNoMoreRunningInstances();
    }

    public void waitsInManualProcessing() {
      final Execution execution = processEngine.getRuntimeService().createExecutionQuery().processInstanceId(processInstanceId)
          .activityId(Elements.TASK_PROCESS_MANUALLY).singleResult();
      assertNotNull(execution);
    }

  }

  private final Glue glue = new Glue();

  @Before
  public void initMocks() {
    Mocks.register(SimpleProcessAdapter.NAME, simpleProcessAdapter);
  }

  @Test
  @Deployment(resources = SimpleProcess.BPMN)
  public void shouldDeploy() {
    // nothing to do.
  }

  @Test
  @Deployment(resources = SimpleProcess.BPMN)
  public void shouldStartAndRunAutomatically() {

    // given
    glue.loadContractData(true);
    glue.processAutomatically(false);

    // when
    glue.startSimpleProcess();

    // then
    glue.assertEndEvent(Events.EVENT_CONTRACT_PROCESSED);
  }

  @Test
  @Deployment(resources = SimpleProcess.BPMN)
  public void shouldStartAndWaitForManual() {

    // given
    glue.loadContractData(false);

    // when
    glue.startSimpleProcess();

    // then
    glue.waitsInManualProcessing();
  }

  @Test
  @Deployment(resources = SimpleProcess.BPMN)
  public void shouldStartProcessAutomaticallyAndWaitForManual() {

    // given
    glue.loadContractData(true);
    glue.processAutomatically(true);

    // when
    glue.startSimpleProcess();

    // then
    glue.waitsInManualProcessing();
  }

}
