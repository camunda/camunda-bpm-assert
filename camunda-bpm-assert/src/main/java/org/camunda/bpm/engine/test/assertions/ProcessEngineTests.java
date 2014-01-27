package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.runtime.ExecutionQuery;
import org.camunda.bpm.engine.runtime.JobQuery;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * Convenience class to access all assertions camunda BPM 
 * Process Engine Assertions - PLUS a few helper methods.
 * 
 * In your code use import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
 *
 * @see org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineTests extends ProcessEngineAssertions {

  protected ProcessEngineTests() {}

  /**
   * Helper method to easily access RuntimeService
   * @see org.camunda.bpm.engine.RuntimeService
   * @return RuntimeService of process engine bound to this testing thread
   */
  public static RuntimeService runtimeService() {
    return processEngine().getRuntimeService();
  }

  /**
   * Helper method to easily access AuthorizationService
   * @see org.camunda.bpm.engine.AuthorizationService
   * @return AuthorizationService of process engine bound to this testing thread
   */
  public static AuthorizationService authorizationService() {
    return processEngine().getAuthorizationService();
  }

  /**
   * Helper method to easily access FormService
   * @see org.camunda.bpm.engine.FormService
   * @return FormService of process engine bound to this testing thread
   */
  public static FormService formService() {
    return processEngine().getFormService();
  }

  /**
   * Helper method to easily access HistoryService
   * @see org.camunda.bpm.engine.HistoryService
   * @return HistoryService of process engine bound to this testing thread
   */
  public static HistoryService historyService() {
    return processEngine().getHistoryService();
  }

  /**
   * Helper method to easily access IdentityService
   * @see org.camunda.bpm.engine.IdentityService
   * @return IdentityService of process engine bound to this testing thread
   */
  public static IdentityService identityService() {
    return processEngine().getIdentityService();
  }

  /**
   * Helper method to easily access ManagementService
   * @see org.camunda.bpm.engine.ManagementService
   * @return ManagementService of process engine bound to this testing thread
   */
  public static ManagementService managementService() {
    return processEngine().getManagementService();
  }

  /**
   * Helper method to easily access RepositoryService
   * @see org.camunda.bpm.engine.RepositoryService
   * @return RepositoryService of process engine bound to this testing thread
   */
  public static RepositoryService repositoryService() {
    return processEngine().getRepositoryService();
  }

  /**
   * Helper method to easily access TaskService
   * @see org.camunda.bpm.engine.TaskService
   * @return TaskService of process engine bound to this testing thread
   */
  public static TaskService taskService() {
    return processEngine().getTaskService();
  }

  /**
   * Helper method to easily create a new TaskQuery
   * @see org.camunda.bpm.engine.task.TaskQuery
   * @return new TaskQuery for process engine bound to this testing thread
   */
  public static TaskQuery taskQuery() {
    return taskService().createTaskQuery();
  }

  /**
   * Helper method to easily create a new JobQuery
   * @see org.camunda.bpm.engine.runtime.JobQuery
   * @return new JobQuery for process engine bound to this testing thread
   */
  public static JobQuery jobQuery() {
    return managementService().createJobQuery();
  }

  /**
   * Helper method to easily create a new ProcessInstanceQuery
   * @see org.camunda.bpm.engine.runtime.ProcessInstanceQuery
   * @return new ProcessInstanceQuery for process engine bound to this testing thread
   */
  public static ProcessInstanceQuery processInstanceQuery() {
    return runtimeService().createProcessInstanceQuery();
  }

  /**
   * Helper method to easily create a new ExecutionQuery
   * @see org.camunda.bpm.engine.runtime.ExecutionQuery
   * @return new ExecutionQuery for process engine bound to this testing thread
   */
  public static ExecutionQuery executionQuery() {
    return runtimeService().createExecutionQuery();
  }

  /**
   * Helper method to easily construct a map of process variables
   * @param key (obligatory) key of first process variable
   * @param value (obligatory) value of first process variable
   * @param furtherKeyValuePairs (optional) key/value pairs for further process variables
   * @return a map of process variables by passing a list of String -> Object key value pairs.
   */
  public static Map<String, Object> withVariables(final String key, final Object value, final Object... furtherKeyValuePairs) {
    if (key == null)
        throw new IllegalArgumentException(format("Illegal call of withVariables(key = '%s', value = '%s', ...) - key must not be null!", key, value));
    final Map<String, Object> map = new HashMap<String, Object>();
    map.put(key, value);
    if (furtherKeyValuePairs != null) {
      if (furtherKeyValuePairs.length % 2 != 0) {
        throw new IllegalArgumentException(format("Illegal call of withVariables() - must have an even number of arguments, but found length = %s!", furtherKeyValuePairs.length + 2));
      }
      for (int i = 0; i < furtherKeyValuePairs.length; i += 2) {
        if (!(furtherKeyValuePairs[i] instanceof String))
          throw new IllegalArgumentException(format("Illegal call of withVariables() - keys must be strings, found object of type '%s'!", furtherKeyValuePairs[i] != null ? furtherKeyValuePairs[i].getClass().getName() : null));
        map.put((String) furtherKeyValuePairs[i], furtherKeyValuePairs[i + 1]);
      }
    }
    return map;
  }

  /**
   * Helper method to easily access the last asserted task.
   * @return the last task used as an object under test of a TaskAssert. 
   * May return null if no such task exists anymore.
   */
  public static Task task() {
    TaskAssert lastAssert = AbstractProcessAssert.getLastAssert(TaskAssert.class);
    if (lastAssert == null)
        throw new IllegalStateException("Call a task assertion first -  e.g. assertThat(task)... or assertThat(processInstance).task()...!");
    return taskService().createTaskQuery().taskId(lastAssert.getActual().getId()).singleResult();
  }

  /**
   * Helper method to easily claim a task for a specific assignee.
   * @param task Task to be claimed for an assignee
   * @param assigneeUserId userId of assignee for which the task should be claimed
   * @return the assigned task - properly refreshed to its assigned state.
   */
  public static Task claim(Task task, String assigneeUserId) {
    if (task == null || assigneeUserId == null)
      throw new IllegalArgumentException(format("Illegal call of claim(task = '%s', assigneeUserId = '%s') - both must not be null!", task, assigneeUserId));
    taskService().claim(task.getId(), assigneeUserId);
    return taskService().createTaskQuery().taskId(task.getId()).singleResult();
  }

  /**
   * Helper method to easily complete a task and pass some process variables. 
   * @param task Task to be completed 
   * @param variables Process variables to be passed to the process instance when 
   *                  completing the task. For setting those variables, you can use 
   *                  withVariables(String key, Object value, ...)
   */
  public static void complete(Task task, Map<String, Object> variables) {
    if (task == null || variables == null)
      throw new IllegalArgumentException(format("Illegal call of claim(task = '%s', variables = '%s') - both must not be null!", task, variables));
    taskService().complete(task.getId(), variables);
  }

  /**
   * Helper method to easily complete a task.
   * @param task Task to be completed 
   */
  public static void complete(Task task) {
    if (task == null)
      throw new IllegalArgumentException(format("Illegal call of claim(task = '%s') - must not be null!", task));
    taskService().complete(task.getId());
  }

}
