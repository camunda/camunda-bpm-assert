package org.camunda.bpm.engine.test.fluent;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.runtime.ExecutionQuery;
import org.camunda.bpm.engine.runtime.JobQuery;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.task.TaskQuery;

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

}
