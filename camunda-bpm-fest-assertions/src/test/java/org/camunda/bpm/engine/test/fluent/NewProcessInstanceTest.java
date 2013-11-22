package org.camunda.bpm.engine.test.fluent;

import static org.camunda.bpm.engine.test.cfg.MostUsefulProcessEngineConfiguration.mostUsefulProcessEngineConfiguration;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.after;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.assertThat;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.before;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.deploy;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.newProcessInstance;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.processInstance;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.task;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the correct behavior of {@link FluentProcessEngineTests}s
 * "newProcessInstance" method.
 * 
 * @author Jan Galinski, Holisticon AG
 */
public class NewProcessInstanceTest {

  private static final String TASK_DUMMY = "task_dummy";
  private static final String PROCESS_KEY = "process_dummy";
  private static final String DUMMY_BPMN = "dummy.bpmn";
  private final ProcessEngine processEngine = mostUsefulProcessEngineConfiguration().buildProcessEngine();

  @Before
  public void setUp() throws Exception {
    before(processEngine);
    deploy(DUMMY_BPMN);
  }

  @After
  public void tearDown() {
    after();
  }

  @Test
  public void start_and_complete_process_via_fluent_api() throws Exception {
    newProcessInstance(PROCESS_KEY).start();
    assertThat(processInstance()).isActive();
    task(TASK_DUMMY).complete();
    assertThat(processInstance()).isEnded();
  }

  @Test(expected = IllegalStateException.class)
  public void should_fail_to_start_processinstance_when_instance_was_started_externally() throws Exception {
    final ProcessInstance instance = startProcessExternally();
    newProcessInstance(instance).start();
  }

  @Test(expected = IllegalStateException.class)
  public void should_fail_to_startandmove_processinstance_when_instance_was_started_externally() throws Exception {
    final ProcessInstance instance = startProcessExternally();
    newProcessInstance(instance).startAndMove();
  }

  /**
   * should behave exactly like
   * {@link #start_and_complete_process_via_fluent_api()} but starts the process
   * outside the control of the fluent api.
   * 
   * @throws Exception
   */
  @Test
  public void start_process_externally_and_complete_via_fluent_api() throws Exception {
    final ProcessInstance processInstance = startProcessExternally();

    newProcessInstance(processInstance);
    assertThat(processInstance()).isActive();
    task(TASK_DUMMY).complete();
    assertThat(processInstance()).isEnded();
  }

  private ProcessInstance startProcessExternally() {
    final ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(PROCESS_KEY);
    return processInstance;
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_fail_when_process_instance_is_null() {
    newProcessInstance((ProcessInstance) null);
  }

  @Test(expected = IllegalStateException.class)
  public void should_fail_when_process_instance_is_registered_twice_for_process_definition() {

    // create two mock instances
    final ProcessInstance p1 = mock(ProcessInstance.class);
    final ProcessInstance p2 = mock(ProcessInstance.class);

    // both have the same definition id
    when(p1.getProcessDefinitionId()).thenReturn("foo");
    when(p2.getProcessDefinitionId()).thenReturn("foo");

    // should path
    newProcessInstance(p1);

    // fails because foo is already registered
    newProcessInstance(p2);
  }

}
