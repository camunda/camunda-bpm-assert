package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.*;
import org.assertj.core.api.AbstractAssert;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.runtime.ExecutionQuery;
import org.camunda.bpm.engine.runtime.JobQuery;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.runtime.VariableInstanceQuery;
import org.camunda.bpm.engine.task.TaskQuery;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class AbstractProcessAssert<S extends AbstractProcessAssert<S, A>, A> extends AbstractAssert<S, A> {

  protected ProcessEngine engine;

  private static ThreadLocal<Map<Class<?>, AbstractProcessAssert>>
    lastAsserts = new ThreadLocal<Map<Class<?>, AbstractProcessAssert>>();

  protected AbstractProcessAssert(ProcessEngine engine, A actual, Class<?> selfType) {
    super(actual, selfType);
    this.engine = engine;
    setLastAssert(selfType, this);
  }
  
  public A getActual() {
    return actual;
  }
  
  public static void resetLastAsserts() {
    getLastAsserts().clear();
  }

  @SuppressWarnings("unchecked")
  public static <S extends AbstractProcessAssert> S getLastAssert(Class<S> assertClass) {
    return (S) getLastAsserts().get(assertClass);
  }

  private static void setLastAssert(Class<?> assertClass, AbstractProcessAssert assertInstance) {
    getLastAsserts().put(assertClass, assertInstance);
  }

  private static Map<Class<?>, AbstractProcessAssert> getLastAsserts() {
    Map<Class<?>, AbstractProcessAssert> asserts = lastAsserts.get();
    if (asserts == null)
      lastAsserts.set(asserts = new HashMap<Class<?>, AbstractProcessAssert>());
    return asserts;
  }

  protected RepositoryService repositoryService() {
    return engine.getRepositoryService();
  }

  protected RuntimeService runtimeService() {
    return engine.getRuntimeService();
  }

  protected FormService formService() {
    return engine.getFormService();
  }

  protected TaskService taskService() {
    return engine.getTaskService();
  }

  protected HistoryService historyService() {
    return engine.getHistoryService();
  }

  protected IdentityService identityService() {
    return engine.getIdentityService();
  }

  protected ManagementService managementService() {
    return engine.getManagementService();
  }

  protected AuthorizationService authorizationService() {
    return engine.getAuthorizationService();
  }

  protected TaskQuery taskQuery() {
    return taskService().createTaskQuery();
  }

  protected JobQuery jobQuery() {
    return managementService().createJobQuery();
  }

  protected ProcessInstanceQuery processInstanceQuery() {
    return runtimeService().createProcessInstanceQuery();
  }

  protected ExecutionQuery executionQuery() {
    return runtimeService().createExecutionQuery();
  }

  protected VariableInstanceQuery variableInstanceQuery() {
    return runtimeService().createVariableInstanceQuery();
  }

  protected HistoricActivityInstanceQuery historicActivityInstanceQuery() {
    return historyService().createHistoricActivityInstanceQuery();
  }

  protected HistoricDetailQuery historicDetailQuery() {
    return historyService().createHistoricDetailQuery();
  }

  protected HistoricProcessInstanceQuery historicProcessInstanceQuery() {
    return historyService().createHistoricProcessInstanceQuery();
  }

  protected HistoricTaskInstanceQuery historicTaskInstanceQuery() {
    return historyService().createHistoricTaskInstanceQuery();
  }

  protected HistoricVariableInstanceQuery historicVariableInstanceQuery() {
    return historyService().createHistoricVariableInstanceQuery();
  }

}
