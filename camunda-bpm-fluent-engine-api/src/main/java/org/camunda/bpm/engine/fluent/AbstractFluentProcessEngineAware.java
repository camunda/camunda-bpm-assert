package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class AbstractFluentProcessEngineAware {

  protected final FluentProcessEngine engine;

  protected AbstractFluentProcessEngineAware(final FluentProcessEngine engine) {
    this.engine = engine;
  }

  protected FluentProcessDefinitionRepository getProcessDefinitionRepository() {
    return engine.getProcessDefinitionRepository();
  }

  protected FluentProcessInstanceRepository getProcessInstanceRepository() {
    return engine.getProcessInstanceRepository();
  }

  protected RepositoryService getRepositoryService() {
    return engine.getRepositoryService();
  }

  protected RuntimeService getRuntimeService() {
    return engine.getRuntimeService();
  }

  protected FormService getFormService() {
    return engine.getFormService();
  }

  protected TaskService getTaskService() {
    return engine.getTaskService();
  }

  protected HistoryService getHistoryService() {
    return engine.getHistoryService();
  }

  protected IdentityService getIdentityService() {
    return engine.getIdentityService();
  }

  protected ManagementService getManagementService() {
    return engine.getManagementService();
  }

  protected AuthorizationService getAuthorizationService() {
    return engine.getAuthorizationService();
  }

}
