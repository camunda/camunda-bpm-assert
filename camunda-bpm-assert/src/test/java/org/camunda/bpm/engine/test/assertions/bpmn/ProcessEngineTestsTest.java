package org.camunda.bpm.engine.test.assertions.bpmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.*;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineTestsTest {

  ProcessEngine processEngine;

  @Before
  public void setUp() {
    processEngine = mock(ProcessEngine.class);
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
    Assertions.assertThat(BpmnAwareTests.processEngine()).isNotNull().isSameAs(processEngine);
  }

  @Test
  public void testReset() throws Exception {
    // When
    reset();
    // Then
    assertThat(BpmnAwareTests.processEngine.get()).isNull();
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
  public void testRuntimeService() {
    // Given
    RuntimeService runtimeService = mock(RuntimeService.class);
    when(processEngine.getRuntimeService()).thenReturn(runtimeService);
    // When
    RuntimeService returnedService = runtimeService();
    // Then
    assertThat(returnedService).isNotNull().isSameAs(runtimeService);
    verify(processEngine, times(1)).getRuntimeService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testAuthorizationService() {
    // Given
    AuthorizationService authorizationService = mock(AuthorizationService.class);
    when(processEngine.getAuthorizationService()).thenReturn(authorizationService);
    // When
    AuthorizationService returnedService = authorizationService();
    // Then
    assertThat(returnedService).isNotNull().isSameAs(authorizationService);
    verify(processEngine, times(1)).getAuthorizationService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testFormService() {
    // Given
    FormService formService = mock(FormService.class);
    when(processEngine.getFormService()).thenReturn(formService);
    // When
    FormService returnedService = formService();
    // Then
    assertThat(returnedService).isNotNull().isSameAs(formService);
    verify(processEngine, times(1)).getFormService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testHistoryService() {
    // Given
    HistoryService historyService = mock(HistoryService.class);
    when(processEngine.getHistoryService()).thenReturn(historyService);
    // When
    HistoryService returnedService = historyService();
    // Then
    assertThat(returnedService).isNotNull().isSameAs(historyService);
    verify(processEngine, times(1)).getHistoryService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testIdentityService() {
    // Given
    IdentityService identityService = mock(IdentityService.class);
    when(processEngine.getIdentityService()).thenReturn(identityService);
    // When
    IdentityService returnedService = identityService();
    // Then
    assertThat(returnedService).isNotNull().isSameAs(identityService);
    verify(processEngine, times(1)).getIdentityService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testManagementService() {
    // Given
    ManagementService managementService = mock(ManagementService.class);
    when(processEngine.getManagementService()).thenReturn(managementService);
    // When
    ManagementService returnedService = managementService();
    // Then
    assertThat(returnedService).isNotNull().isSameAs(managementService);
    verify(processEngine, times(1)).getManagementService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testRepositoryService() {
    // Given
    RepositoryService repositoryService = mock(RepositoryService.class);
    when(processEngine.getRepositoryService()).thenReturn(repositoryService);
    // When
    RepositoryService returnedService = repositoryService();
    // Then
    assertThat(returnedService).isNotNull().isSameAs(repositoryService);
    verify(processEngine, times(1)).getRepositoryService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testTaskService() {
    // Given
    TaskService taskService = mock(TaskService.class);
    when(processEngine.getTaskService()).thenReturn(taskService);
    // When
    TaskService returnedService = taskService();
    // Then
    assertThat(returnedService).isNotNull().isSameAs(taskService);
    verify(processEngine, times(1)).getTaskService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testTaskQuery() {
    // Given
    TaskService taskService = mock(TaskService.class);
    TaskQuery taskQuery = mock(TaskQuery.class);
    when(processEngine.getTaskService()).thenReturn(taskService);
    when(taskService.createTaskQuery()).thenReturn(taskQuery);
    // When
    TaskQuery createdQuery = taskQuery();
    // Then
    assertThat(createdQuery).isNotNull().isSameAs(taskQuery);
    verify(taskService, times(1)).createTaskQuery();
    verifyNoMoreInteractions(taskService);
  }

  @Test
  public void testJobQuery() {
    // Given
    ManagementService managementService = mock(ManagementService.class);
    JobQuery jobQuery = mock(JobQuery.class);
    when(processEngine.getManagementService()).thenReturn(managementService);
    when(managementService.createJobQuery()).thenReturn(jobQuery);
    // When
    JobQuery createdQuery = jobQuery();
    // Then
    assertThat(createdQuery).isNotNull().isSameAs(jobQuery);
    verify(managementService, times(1)).createJobQuery();
    verifyNoMoreInteractions(managementService);
  }

  @Test
  public void testProcessInstanceQuery() {
    // Given
    RuntimeService runtimeService = mock(RuntimeService.class);
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processEngine.getRuntimeService()).thenReturn(runtimeService);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    // When
    ProcessInstanceQuery createdQuery = processInstanceQuery();
    // Then
    assertThat(createdQuery).isNotNull().isSameAs(processInstanceQuery);
    verify(runtimeService, times(1)).createProcessInstanceQuery();
    verifyNoMoreInteractions(runtimeService);
  }

  @Test
  public void testProcessDefinitionQuery() {
    // Given
    RepositoryService repositoryService = mock(RepositoryService.class);
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(processEngine.getRepositoryService()).thenReturn(repositoryService);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    // When
    ProcessDefinitionQuery createdQuery = processDefinitionQuery();
    // Then
    assertThat(createdQuery).isNotNull().isSameAs(processDefinitionQuery);
    verify(repositoryService, times(1)).createProcessDefinitionQuery();
    verifyNoMoreInteractions(repositoryService);
  }
  
  @Test
  public void testExecutionQuery() {
    // Given
    RuntimeService runtimeService = mock(RuntimeService.class);
    ExecutionQuery executionQuery = mock(ExecutionQuery.class);
    when(processEngine.getRuntimeService()).thenReturn(runtimeService);
    when(runtimeService.createExecutionQuery()).thenReturn(executionQuery);
    // When
    ExecutionQuery createdQuery = executionQuery();
    // Then
    assertThat(createdQuery).isNotNull().isSameAs(executionQuery);
    verify(runtimeService, times(1)).createExecutionQuery();
    verifyNoMoreInteractions(runtimeService);
  }

}
