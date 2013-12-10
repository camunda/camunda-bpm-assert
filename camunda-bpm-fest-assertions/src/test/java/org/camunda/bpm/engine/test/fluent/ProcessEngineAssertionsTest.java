package org.camunda.bpm.engine.test.fluent;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.fluent.assertions.JobAssert;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessDefinitionAssert;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessInstanceAssert;
import org.camunda.bpm.engine.test.fluent.assertions.TaskAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import static org.camunda.bpm.engine.test.fluent.ProcessEngineAssertions.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineAssertionsTest {

  ProcessEngine processEngine;

  @Before
  public void setUp() {
    processEngine = Mockito.mock(ProcessEngine.class);
    ProcessEngineAssertions.init(processEngine);
  }

  @After
  public void tearDown() {
    ProcessEngineAssertions.reset();
  }

  @Test
  public void testProcessEngine() throws Exception {
    assertThat(ProcessEngineAssertions.processEngine()).isNotNull().isSameAs(processEngine);
  }

  @Test
  public void testInit() throws Exception {
    reset();
    init(processEngine);
    assertThat(ProcessEngineAssertions.processEngine()).isNotNull().isSameAs(processEngine);
  }

  @Test
  public void testReset() throws Exception {
    reset();
    try {
      processEngine();
      fail("IllegalStateException expected, because processEngine() called immediately after reset()");
    } catch (Throwable e) {
      assertThat(e).isInstanceOf(IllegalStateException.class);
    }
  }

  @Test
  public void testAssertThat_ProcessDefinition() throws Exception {
    ProcessDefinition processDefinition = Mockito.mock(ProcessDefinition.class);
    assertThat(assertThat(processDefinition)).isNotNull().isInstanceOf(ProcessDefinitionAssert.class);
    ProcessDefinitionAssert processDefinitionAssert = assertThat(processDefinition);
    assertThat(processDefinitionAssert.getActual()).isSameAs(processDefinition);
  }

  @Test
  public void testAssertThat_ProcessInstance() throws Exception {
    ProcessInstance processInstance = Mockito.mock(ProcessInstance.class);
    assertThat(assertThat(processInstance)).isNotNull().isInstanceOf(ProcessInstanceAssert.class);
    ProcessInstanceAssert processInstanceAssert = assertThat(processInstance);
    assertThat(processInstanceAssert.getActual()).isSameAs(processInstance);
  }

  @Test
  public void testAssertThat_Task() throws Exception {
    Task task = Mockito.mock(Task.class);
    assertThat(assertThat(task)).isNotNull().isInstanceOf(TaskAssert.class);
    TaskAssert taskAssert = assertThat(task);
    assertThat(taskAssert.getActual()).isSameAs(task);
  }

  @Test
  public void testAssertThat_Job() throws Exception {
    Job job = Mockito.mock(Job.class);
    assertThat(assertThat(job)).isNotNull().isInstanceOf(JobAssert.class);
    JobAssert jobAssert = assertThat(job);
    assertThat(jobAssert.getActual()).isSameAs(job);
  }

}
