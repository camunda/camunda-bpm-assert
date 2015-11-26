package org.camunda.bpm.engine.test.assertions;


import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.*;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;

import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Martin Günther <martin.guenter@holisticon.de>
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 *   
 * @deprecated Use org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests
 *              or org.camunda.bpm.engine.test.assertions.cmmn.ProcessEngineTests
 */
@Deprecated
public class ProcessEngineTests extends ProcessEngineAssertions {
  
  protected ProcessEngineTests() {}

  /**
   * Helper method to easily execute a job.
   *
   * @param   job Job to be executed.
   */
  public static void execute(Job job) {
    org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.execute(job);
  }

  /**
   * Helper method to easily access RuntimeService
   *
   * @return RuntimeService of process engine bound to this testing thread
   * @see     RuntimeService
   */
  public static RuntimeService runtimeService() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.runtimeService();
  }

  /**
   * Helper method to easily access AuthorizationService
   *
   * @return AuthorizationService of process engine bound to this 
   *          testing thread
   * @see     AuthorizationService
   */
  public static AuthorizationService authorizationService() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.authorizationService();
  }

  /**
   * Helper method to easily access FormService
   * 
   * @return  FormService of process engine bound to this testing thread
   * @see     org.camunda.bpm.engine.FormService
   */
  public static FormService formService() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.formService();
  }

  /**
   * Helper method to easily access HistoryService
   *
   * @return HistoryService of process engine bound to this testing thread
   * @see     HistoryService
   */
  public static HistoryService historyService() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.historyService();
  }

  /**
   * Helper method to easily access IdentityService
   *
   * @return IdentityService of process engine bound to this testing thread
   * @see     IdentityService
   */
  public static IdentityService identityService() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.identityService();
  }

  /**
   * Helper method to easily access ManagementService
   *
   * @return ManagementService of process engine bound to this testing thread
   * @see     ManagementService
   */
  public static ManagementService managementService() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.managementService();
  }

  /**
   * Helper method to easily access RepositoryService
   *
   * @return RepositoryService of process engine bound to this testing thread
   * @see     RepositoryService
   */
  public static RepositoryService repositoryService() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.repositoryService();
  }

  /**
   * Helper method to easily access TaskService
   *
   * @return TaskService of process engine bound to this testing thread
   * @see     TaskService
   */
  public static TaskService taskService() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.taskService();
  }

  /**
   * Helper method to easily create a new TaskQuery
   *
   * @return new TaskQuery for process engine bound to this testing thread
   * @see     TaskQuery
   */
  public static TaskQuery taskQuery() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.taskQuery();
  }

  /**
   * Helper method to easily create a new JobQuery
   *
   * @return new JobQuery for process engine bound to this testing thread
   * @see     JobQuery
   */
  public static JobQuery jobQuery() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.jobQuery();
  }

  /**
   * Helper method to easily create a new ProcessInstanceQuery
   *
   * @return new ProcessInstanceQuery for process engine bound to this 
   *          testing thread
   * @see     ProcessInstanceQuery
   */
  public static ProcessInstanceQuery processInstanceQuery() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.processInstanceQuery();
  }

  /**
   * Helper method to easily create a new ProcessDefinitionQuery
   *
   * @return new ProcessDefinitionQuery for process engine bound to this 
   *          testing thread
   * @see     ProcessDefinitionQuery
   */
  public static ProcessDefinitionQuery processDefinitionQuery() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.processDefinitionQuery();
  }

  /**
   * Helper method to easily create a new ExecutionQuery
   *
   * @return new ExecutionQuery for process engine bound to this testing thread
   * @see     ExecutionQuery
   */
  public static ExecutionQuery executionQuery() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.executionQuery();
  }

  /**
   * Helper method to easily construct a map of process variables
   *
   * @param   key (obligatory) key of first process variable
   * @param   value (obligatory) value of first process variable
   * @param   furtherKeyValuePairs (optional) key/value pairs for further 
   *          process variables
   * @return a map of process variables by passing a list of String 
   *          -> Object key value pairs.
   */
  public static Map<String, Object> withVariables(String key, Object value, Object... furtherKeyValuePairs) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.withVariables(key, value, furtherKeyValuePairs);
  }

  /**
   * Helper method to easily access the only task currently
   * available in the context of the last asserted process
   * instance.
   *
   * @return the only task of the last asserted process
   *          instance. May return null if no such task exists.
   * @throws IllegalStateException in case more
   *          than one task is delivered by the underlying 
   *          query or in case no process instance was asserted 
   *          yet.
   */
  public static Task task() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.task();
  }

  /**
   * Helper method to easily access the only task currently 
   * available in the context of the given process instance.
   *
   * @param   processInstance the process instance for which
   *          a task should be retrieved.
   * @return the only task of the process instance. May 
   *          return null if no such task exists.
   * @throws IllegalStateException in case more 
   *          than one task is delivered by the underlying 
   *          query.
   */
  public static Task task(ProcessInstance processInstance) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.task(processInstance);
  }

  /**
   * Helper method to easily access the only task with the 
   * given taskDefinitionKey currently available in the context 
   * of the last asserted process instance.
   *
   * @param   taskDefinitionKey the key of the task that should
   *          be retrieved.                             
   * @return the only task of the last asserted process
   *          instance. May return null if no such task exists.
   * @throws IllegalStateException in case more
   *          than one task is delivered by the underlying 
   *          query or in case no process instance was asserted 
   *          yet.
   */
  public static Task task(String taskDefinitionKey) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.task(taskDefinitionKey);
  }

  /**
   * Helper method to easily access the only task with the 
   * given taskDefinitionKey currently available in the context 
   * of the given process instance.
   *
   * @param   taskDefinitionKey the key of the task that should
   *          be retrieved.                             
   * @param   processInstance the process instance for which
   *          a task should be retrieved.
   * @return the only task of the given process instance. May
   *          return null if no such task exists.
   * @throws IllegalStateException in case more
   *          than one task is delivered by the underlying 
   *          query.
   */
  public static Task task(String taskDefinitionKey, ProcessInstance processInstance) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.task(taskDefinitionKey, processInstance);
  }

  /**
   * Helper method to easily access the only task compliant to 
   * a given taskQuery and currently available in the context 
   * of the last asserted process instance.
   *
   * @param   taskQuery the query with which the task should
   *          be retrieved. This query will be further narrowed
   *          to the last asserted process instance.
   * @return the only task of the last asserted process instance 
   *          and compliant to the given query. May return null 
   *          in case no such task exists.
   * @throws IllegalStateException in case more
   *          than one task is delivered by the underlying 
   *          query or in case no process instance was asserted 
   *          yet.
   */
  public static Task task(TaskQuery taskQuery) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.task(taskQuery);
  }

  /**
   * Helper method to easily access the only task compliant to 
   * a given taskQuery and currently available in the context 
   * of the given process instance.
   *
   * @param   taskQuery the query with which the task should
   *          be retrieved. This query will be further narrowed
   *          to the given process instance.
   * @param   processInstance the process instance for which
   *          a task should be retrieved.
   * @return the only task of the given process instance and 
   *          compliant to the given query. May return null in 
   *          case no such task exists.
   * @throws IllegalStateException in case more
   *          than one task is delivered by the underlying 
   *          query.
   */
  public static Task task(TaskQuery taskQuery, ProcessInstance processInstance) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.task(taskQuery, processInstance);
  }

  /**
   * Helper method to easily access the process definition 
   * on which the last asserted process instance is based.
   *
   * @return the process definition on which the last 
   *          asserted process instance is based.
   * @throws IllegalStateException in case no 
   *          process instance was asserted yet.
   */
  public static ProcessDefinition processDefinition() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.processDefinition();
  }

  /**
   * Helper method to easily access the process definition 
   * on which the given process instance is based.
   *
   * @param   processInstance the process instance for which
   *          the definition should be retrieved.
   * @return the process definition on which the given 
   *          process instance is based.
   */
  public static ProcessDefinition processDefinition(ProcessInstance processInstance) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.processDefinition(processInstance);
  }

  /**
   * Helper method to easily access the process definition with the 
   * given processDefinitionKey.
   *
   * @param   processDefinitionKey the key of the process definition 
   *          that should be retrieved.                             
   * @return the process definition with the given key. 
   *          May return null if no such process definition exists.
   */
  public static ProcessDefinition processDefinition(String processDefinitionKey) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.processDefinition(processDefinitionKey);
  }

  /**
   * Helper method to easily access the process definition compliant 
   * to a given process definition query.
   *
   * @param   processDefinitionQuery the query with which the process 
   *          definition should be retrieved.
   * @return the process definition compliant to the given query. May 
   *          return null in case no such process definition exists.
   * @throws ProcessEngineException in case more 
   *          than one process definition is delivered by the underlying 
   *          query.
   */
  public static ProcessDefinition processDefinition(ProcessDefinitionQuery processDefinitionQuery) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.processDefinition(processDefinitionQuery);
  }

  /**
   * Helper method to easily access the only called process instance 
   * currently available in the context of the last asserted process
   * instance.
   *
   * @return the only called process instance called by the last asserted process
   *          instance. May return null if no such process instance exists.
   * @throws IllegalStateException in case more
   *          than one process instance is delivered by the underlying 
   *          query or in case no process instance was asserted 
   *          yet.
   */
  public static ProcessInstance calledProcessInstance() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.calledProcessInstance();
  }

  /**
   * Helper method to easily access the only called process instance 
   * currently available in the context of the given process instance.
   *
   * @param   processInstance the process instance for which
   *          a called process instance should be retrieved.
   * @return the only called process instance called by the given process 
   *          instance. May return null if no such process instance exists.
   * @throws IllegalStateException in case more 
   *          than one process instance is delivered by the underlying 
   *          query.
   */
  public static ProcessInstance calledProcessInstance(ProcessInstance processInstance) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.calledProcessInstance(processInstance);
  }

  /**
   * Helper method to easily access the only called process instance with 
   * the given processDefinitionKey currently available in the context 
   * of the last asserted process instance.
   *
   * @param   processDefinitionKey the key of the process instance that should
   *          be retrieved.                             
   * @return the only such process instance called by the last asserted process
   *          instance. May return null if no such process instance exists.
   * @throws IllegalStateException in case more
   *          than one process instance is delivered by the underlying 
   *          query or in case no process instance was asserted 
   *          yet.
   */
  public static ProcessInstance calledProcessInstance(String processDefinitionKey) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.calledProcessInstance(processDefinitionKey);
  }

  /**
   * Helper method to easily access the only called process instance with the 
   * given processDefinitionKey currently available in the context 
   * of the given process instance.
   *
   * @param   processDefinitionKey the key of the process instance that should
   *          be retrieved.                             
   * @param   processInstance the process instance for which
   *          a called process instance should be retrieved.
   * @return the only such process instance called by the given process instance. 
   *          May return null if no such process instance exists.
   * @throws IllegalStateException in case more
   *          than one process instance is delivered by the underlying 
   *          query.
   */
  public static ProcessInstance calledProcessInstance(String processDefinitionKey, ProcessInstance processInstance) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.calledProcessInstance(processDefinitionKey, processInstance);
  }

  /**
   * Helper method to easily access the only called process instance compliant to 
   * a given processInstanceQuery and currently available in the context 
   * of the last asserted process instance.
   *
   * @param   processInstanceQuery the query with which the called process instance should
   *          be retrieved. This query will be further narrowed to the last asserted 
   *          process instance.
   * @return the only such process instance called by the last asserted process instance and 
   *          compliant to the given query. May return null in case no such task exists.
   * @throws IllegalStateException in case more
   *          than one process instance is delivered by the underlying query or in case no 
   *          process instance was asserted yet.
   */
  public static ProcessInstance calledProcessInstance(ProcessInstanceQuery processInstanceQuery) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.calledProcessInstance(processInstanceQuery);
  }

  /**
   * Helper method to easily access the only called process instance compliant to 
   * a given processInstanceQuery and currently available in the context of the given 
   * process instance.
   *
   * @param   processInstanceQuery the query with which the process instance should
   *          be retrieved. This query will be further narrowed to the given process 
   *          instance.
   * @param   processInstance the process instance for which
   *          a called process instance should be retrieved.
   * @return the only such process instance called by the given process instance and 
   *          compliant to the given query. May return null in 
   *          case no such process instance exists.
   * @throws IllegalStateException in case more
   *          than one instance is delivered by the underlying 
   *          query.
   */
  public static ProcessInstance calledProcessInstance(ProcessInstanceQuery processInstanceQuery, ProcessInstance processInstance) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.calledProcessInstance(processInstanceQuery, processInstance);
  }

  /**
   * Helper method to easily access the only job currently
   * available in the context of the last asserted process
   * instance.
   *
   * @return the only job of the last asserted process
   *          instance. May return null if no such job exists.
   * @throws IllegalStateException in case more
   *          than one job is delivered by the underlying 
   *          query or in case no process instance was asserted 
   *          yet.
   */
  public static Job job() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.job();
  }

  /**
   * Helper method to easily access the only job currently 
   * available in the context of the given process instance.
   *
   * @param   processInstance the process instance for which
   *          a job should be retrieved.
   * @return the only job of the process instance. May 
   *          return null if no such task exists.
   * @throws IllegalStateException in case more 
   *          than one job is delivered by the underlying 
   *          query.
   */
  public static Job job(ProcessInstance processInstance) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.job(processInstance);
  }

  /**
   * Helper method to easily access the only job with the 
   * given activityId currently available in the context 
   * of the last asserted process instance.
   *
   * @param   activityId the id of the job that should
   *          be retrieved.                             
   * @return the only job of the last asserted process
   *          instance. May return null if no such job exists.
   * @throws IllegalStateException in case more
   *          than one job is delivered by the underlying 
   *          query or in case no process instance was asserted 
   *          yet.
   */
  public static Job job(String activityId) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.job(activityId);
  }

  /**
   * Helper method to easily access the only job with the 
   * given activityId currently available in the context 
   * of the given process instance.
   *
   * @param   activityId the activityId of the job that should
   *          be retrieved.                             
   * @param   processInstance the process instance for which
   *          a job should be retrieved.
   * @return the only job of the given process instance. May
   *          return null if no such job exists.
   * @throws IllegalStateException in case more
   *          than one job is delivered by the underlying 
   *          query.
   */
  public static Job job(String activityId, ProcessInstance processInstance) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.job(activityId, processInstance);
  }

  /**
   * Helper method to easily access the only job compliant to 
   * a given jobQuery and currently available in the context 
   * of the last asserted process instance.
   *
   * @param   jobQuery the query with which the job should
   *          be retrieved. This query will be further narrowed
   *          to the last asserted process instance.
   * @return the only job of the last asserted process instance 
   *          and compliant to the given query. May return null 
   *          in case no such task exists.
   * @throws IllegalStateException in case more
   *          than one job is delivered by the underlying 
   *          query or in case no process instance was asserted 
   *          yet.
   */
  public static Job job(JobQuery jobQuery) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.job(jobQuery);
  }

  /**
   * Helper method to easily access the only job compliant to 
   * a given jobQuery and currently available in the context 
   * of the given process instance.
   *
   * @param   jobQuery the query with which the job should
   *          be retrieved. This query will be further narrowed
   *          to the given process instance.
   * @param   processInstance the process instance for which
   *          a job should be retrieved.
   * @return the only job of the given process instance and 
   *          compliant to the given query. May return null in 
   *          case no such job exists.
   * @throws IllegalStateException in case more
   *          than one job is delivered by the underlying 
   *          query.
   */
  public static Job job(JobQuery jobQuery, ProcessInstance processInstance) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.job(jobQuery, processInstance);
  }

  /**
   * Helper method to easily claim a task for a specific 
   * assignee.
   *
   * @param   task Task to be claimed for an assignee
   * @param   assigneeUserId userId of assignee for which 
   *          the task should be claimed
   * @return the assigned task - properly refreshed to its 
   *          assigned state.
   */
  public static Task claim(Task task, String assigneeUserId) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.claim(task, assigneeUserId);
  }

  /**
   * Helper method to easily unclaim a task.
   *
   * @param   task Task to be claimed for an assignee
   * @return the assigned task - properly refreshed to its 
   *          unassigned state.
   */
  public static Task unclaim(Task task) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.unclaim(task);
  }

  /**
   * Helper method to easily complete a task and pass some 
   * process variables. 
   *  @param   task Task to be completed 
   * @param   variables Process variables to be passed to the 
   *          process instance when completing the task. For 
   *          setting those variables, you can use 
   */
  public static void complete(Task task, Map<String, Object> variables) {
    org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.complete(task, variables);
  }

  /**
   * Helper method to easily complete a task.
   *
   * @param   task Task to be completed 
   */
  public static void complete(Task task) {
    org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.complete(task);
  }
  
}
