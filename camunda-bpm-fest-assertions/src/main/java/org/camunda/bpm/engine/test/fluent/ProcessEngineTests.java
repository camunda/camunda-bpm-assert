package org.camunda.bpm.engine.test.fluent;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.runtime.ExecutionQuery;
import org.camunda.bpm.engine.runtime.JobQuery;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.bpm.engine.test.fluent.assertions.TaskAssert;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * Convenience class to access all fluent camunda BPM 
 * Process Engine Assertions - PLUS a few helper methods.
 * 
 * In your code use import static org.camunda.bpm.engine.test.fluent.ProcessEngineTests.*;
 *
 * @see org.camunda.bpm.engine.test.fluent.ProcessEngineAssertions
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineTests extends ProcessEngineAssertions {

  protected ProcessEngineTests() {}

  public static RuntimeService runtimeService() {
    return processEngine().getRuntimeService();
  }
  
  public static AuthorizationService authorizationService() {
    return processEngine().getAuthorizationService();
  }
  
  public static FormService formService() {
    return processEngine().getFormService();
  }
  
  public static HistoryService historyService() {
    return processEngine().getHistoryService();
  }
  
  public static IdentityService identityService() {
    return processEngine().getIdentityService();
  }
  
  public static ManagementService managementService() {
    return processEngine().getManagementService();
  }
  
  public static RepositoryService repositoryService() {
    return processEngine().getRepositoryService();
  }
  
  public static TaskService taskService() {
    return processEngine().getTaskService();
  }

  public static TaskQuery taskQuery() {
    return taskService().createTaskQuery();
  }

  public static JobQuery jobQuery() {
    return managementService().createJobQuery();
  }

  public static ProcessInstanceQuery processInstanceQuery() {
    return runtimeService().createProcessInstanceQuery();
  }

  public static ExecutionQuery executionQuery() {
    return runtimeService().createExecutionQuery();
  }

  /**
   * Helper method to easily construct a map of process variables
   * @return a map of process variables by passing a list of String -> Object key value pairs.
   */
  public static Map<String, Object> withVariables(final String key, final Object value, final Object... keyValuePairs) {
    if (key == null)
        throw new IllegalArgumentException(format("Illegal call of withVariables(key = '%s', value = '%s', ...) - key must not be null!", key, value));
    final Map<String, Object> map = new HashMap<String, Object>();
    map.put(key, value);
    if (keyValuePairs != null) {
      if (keyValuePairs.length % 2 != 0) {
        throw new IllegalArgumentException(format("Illegal call of withVariables() - must have an even number of arguments, but found length = %s!", keyValuePairs.length + 2));
      }
      for (int i = 0; i < keyValuePairs.length; i += 2) {
        if (!(keyValuePairs[i] instanceof String))
          throw new IllegalArgumentException(format("Illegal call of withVariables() - keys must be strings, found object of type '%s'!", keyValuePairs[i] != null ? keyValuePairs[i].getClass().getName() : null));
        map.put((String) keyValuePairs[i], keyValuePairs[i + 1]);
      }
    }
    return map;
  }

  /**
   * Helper method to easily access the last asserted task.
   * @return the last task used as an object under test of a TaskAssert. May return null if no such task exists anymore.
   */
  public static Task task() {
    TaskAssert lastAssert = TaskAssert.lastAssert();
    if (lastAssert == null)
        throw new IllegalStateException("Call a task assertion first -  e.g. assertThat(task)... or assertThat(processInstance).task()...!");
    return taskService().createTaskQuery().taskId(TaskAssert.lastAssert().getActual().getId()).singleResult();
  }

  /**
   * Helper method to easily claim a task for a specific assignee.
   * @return the assigned task - properly refreshed to its assigned state.
   */
  public static Task claim(Task task, String assigneeUserId) {
    if (task == null || assigneeUserId == null)
      throw new IllegalArgumentException(format("Illegal call of claim(task = '%s', assigneeUserId = '%s') - both must not be null!", task, assigneeUserId));
    taskService().claim(task.getId(), assigneeUserId);
    return task();
  }

  /**
   * Helper method to easily complete a task and pass some process variables. For 
   * setting those variables, you can use withVariables(String key, Object value, ...)
   */
  public static void complete(Task task, Map<String, Object> variables) {
    if (task == null || variables == null)
      throw new IllegalArgumentException(format("Illegal call of claim(task = '%s', variables = '%s') - both must not be null!", task, variables));
    taskService().complete(task.getId(), variables);
  }

  /**
   * Helper method to easily complete a task.
   */
  public static void complete(Task task) {
    if (task == null)
      throw new IllegalArgumentException(format("Illegal call of claim(task = '%s') - must not be null!", task));
    taskService().complete(task.getId());
  }

}
