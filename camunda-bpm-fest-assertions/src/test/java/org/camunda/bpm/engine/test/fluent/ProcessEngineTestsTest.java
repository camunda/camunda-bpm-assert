package org.camunda.bpm.engine.test.fluent;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.runtime.ExecutionQuery;
import org.camunda.bpm.engine.runtime.JobQuery;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.task.TaskQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import static org.camunda.bpm.engine.test.fluent.ProcessEngineTests.*;

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
  public void testRuntimeService() {
    RuntimeService runtimeService = mock(RuntimeService.class);
    when(processEngine.getRuntimeService()).thenReturn(runtimeService);
    assertThat(runtimeService()).isNotNull().isSameAs(runtimeService);
    verify(processEngine, times(1)).getRuntimeService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testAuthorizationService() {
    AuthorizationService authorizationService = mock(AuthorizationService.class);
    when(processEngine.getAuthorizationService()).thenReturn(authorizationService);
    assertThat(authorizationService()).isNotNull().isSameAs(authorizationService);
    verify(processEngine, times(1)).getAuthorizationService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testFormService() {
    FormService formService = mock(FormService.class);
    when(processEngine.getFormService()).thenReturn(formService);
    assertThat(formService()).isNotNull().isSameAs(formService);
    verify(processEngine, times(1)).getFormService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testHistoryService() {
    HistoryService historyService = mock(HistoryService.class);
    when(processEngine.getHistoryService()).thenReturn(historyService);
    assertThat(historyService()).isNotNull().isSameAs(historyService);
    verify(processEngine, times(1)).getHistoryService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testIdentityService() {
    IdentityService identityService = mock(IdentityService.class);
    when(processEngine.getIdentityService()).thenReturn(identityService);
    assertThat(identityService()).isNotNull().isSameAs(identityService);
    verify(processEngine, times(1)).getIdentityService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testManagementService() {
    ManagementService managementService = mock(ManagementService.class);
    when(processEngine.getManagementService()).thenReturn(managementService);
    assertThat(managementService()).isNotNull().isSameAs(managementService);
    verify(processEngine, times(1)).getManagementService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testRepositoryService() {
    RepositoryService repositoryService = mock(RepositoryService.class);
    when(processEngine.getRepositoryService()).thenReturn(repositoryService);
    assertThat(repositoryService()).isNotNull().isSameAs(repositoryService);
    verify(processEngine, times(1)).getRepositoryService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testTaskService() {
    TaskService taskService = mock(TaskService.class);
    when(processEngine.getTaskService()).thenReturn(taskService);
    assertThat(taskService()).isNotNull().isSameAs(taskService);
    verify(processEngine, times(1)).getTaskService();
    verifyNoMoreInteractions(processEngine);
  }

  @Test
  public void testTaskQuery() {
    TaskService taskService = mock(TaskService.class);
    TaskQuery taskQuery = mock(TaskQuery.class);
    when(processEngine.getTaskService()).thenReturn(taskService);
    when(taskService.createTaskQuery()).thenReturn(taskQuery);
    assertThat(taskQuery()).isNotNull().isSameAs(taskQuery);
    verify(taskService, times(1)).createTaskQuery();
    verifyNoMoreInteractions(taskService);
  }

  @Test
  public void testJobQuery() {
    ManagementService managementService = mock(ManagementService.class);
    JobQuery jobQuery = mock(JobQuery.class);
    when(processEngine.getManagementService()).thenReturn(managementService);
    when(managementService.createJobQuery()).thenReturn(jobQuery);
    assertThat(jobQuery()).isNotNull().isSameAs(jobQuery);
    verify(managementService, times(1)).createJobQuery();
    verifyNoMoreInteractions(managementService);
  }

  @Test
  public void testProcessInstanceQuery() {
    RuntimeService runtimeService = mock(RuntimeService.class);
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processEngine.getRuntimeService()).thenReturn(runtimeService);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    assertThat(processInstanceQuery()).isNotNull().isSameAs(processInstanceQuery);
    verify(runtimeService, times(1)).createProcessInstanceQuery();
    verifyNoMoreInteractions(runtimeService);
  }

  @Test
  public void testExecutionQuery() {
    RuntimeService runtimeService = mock(RuntimeService.class);
    ExecutionQuery executionQuery = mock(ExecutionQuery.class);
    when(processEngine.getRuntimeService()).thenReturn(runtimeService);
    when(runtimeService.createExecutionQuery()).thenReturn(executionQuery);
    assertThat(executionQuery()).isNotNull().isSameAs(executionQuery);
    verify(runtimeService, times(1)).createExecutionQuery();
    verifyNoMoreInteractions(runtimeService);
  }

}
