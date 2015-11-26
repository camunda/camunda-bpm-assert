package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.CaseDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.assertions.cmmn.CaseExecutionAssert;
import org.camunda.bpm.engine.test.assertions.cmmn.CaseInstanceAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import static org.camunda.bpm.engine.test.assertions.cmmn.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineAssertionsTest {

  ProcessEngine processEngine;

  @Before
  public void setUp() {
    processEngine = Mockito.mock(ProcessEngine.class);
    init(processEngine);
  }

  @After
  public void tearDown() {
    reset();
  }

  @Test
  public void testProcessEngine() throws Exception {
    // When
    ProcessEngine returnedEngine = processEngine();
    // Then
    assertThat(returnedEngine).isNotNull().isSameAs(processEngine);
  }

  @Test
  public void testInit() throws Exception {
    // Given
    reset();
    // When
    init(processEngine);
    // Then
    assertThat(ProcessEngineAssertions.processEngine()).isNotNull().isSameAs(processEngine);
  }

  @Test
  public void testReset() throws Exception {
    // When
    reset();
    // Then
    assertThat(ProcessEngineTests.processEngine.get()).isNull();
  }

  @Test
  public void testAssertThat_ProcessDefinition() throws Exception {
    // Given
    ProcessDefinition processDefinition = Mockito.mock(ProcessDefinition.class);
    // When
    ProcessDefinitionAssert returnedAssert = assertThat(processDefinition);
    // Then
    assertThat(returnedAssert).isNotNull().isInstanceOf(ProcessDefinitionAssert.class);
    ProcessDefinitionAssert processDefinitionAssert = assertThat(processDefinition);
    assertThat(processDefinitionAssert.getActual()).isSameAs(processDefinition);
  }

  @Test
  public void testAssertThat_ProcessInstance() throws Exception {
    // Given
    ProcessInstance processInstance = Mockito.mock(ProcessInstance.class);
    // When
    ProcessInstanceAssert returnedAssert = assertThat(processInstance);
    // Then
    assertThat(returnedAssert).isNotNull().isInstanceOf(ProcessInstanceAssert.class);
    ProcessInstanceAssert processInstanceAssert = assertThat(processInstance);
    assertThat(processInstanceAssert.getActual()).isSameAs(processInstance);
  }

  @Test
  public void testAssertThat_Task() throws Exception {
    // Given
    Task task = Mockito.mock(Task.class);
    // When
    TaskAssert returnedAssert = assertThat(task);
    // Then
    assertThat(returnedAssert).isNotNull().isInstanceOf(TaskAssert.class);
    TaskAssert taskAssert = assertThat(task);
    assertThat(taskAssert.getActual()).isSameAs(task);
  }

  @Test
  public void testAssertThat_Job() throws Exception {
    // Given
    Job job = Mockito.mock(Job.class);
    // When
    JobAssert returnedAssert = assertThat(job);
    // Then
    assertThat(returnedAssert).isNotNull().isInstanceOf(JobAssert.class);
    JobAssert jobAssert = assertThat(job);
    assertThat(jobAssert.getActual()).isSameAs(job);
  }

  @Test
  public void testAssertThat_CaseInstance() throws Exception {
    //Given
    CaseInstance caseInstance = Mockito.mock(CaseInstance.class);
    // When
    CaseInstanceAssert returnedAssert = assertThat(caseInstance);
    // Then
    assertThat(returnedAssert.getActual()).isSameAs(caseInstance);
  }

  @Test
  public void testAssertThat_CaseExecution() throws Exception {
    //Given
    CaseExecution caseExecution = Mockito.mock(CaseExecution.class);
    // When
    CaseExecutionAssert returnedAssert = assertThat(caseExecution);
    // Then
    assertThat(returnedAssert.getActual()).isSameAs(caseExecution);
  }

  @Test
  public void testAssertThat_CaseDefinition() throws Exception {
    //Given
    CaseDefinition caseDefinition = Mockito.mock(CaseDefinition.class);
    // When
    CaseDefinitionAssert returnedAssert = assertThat(caseDefinition);
    // Then
    assertThat(returnedAssert.getActual()).isSameAs(caseDefinition);
  }

}
