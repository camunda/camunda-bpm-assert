package org.camunda.bpm.test;

import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.fluent.FluentProcessInstance;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.impl.test.TestHelper;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.test.needle.supplier.CamundaInstancesSupplier;
import org.hamcrest.collection.IsEmptyCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

/**
 * Helper for camunda access.
 * 
 * @author Simon Zambrovski, Holisticon AG
 * 
 */
public class CamundaSupport {

  /**
   * Singleton instance.
   */
  private static CamundaSupport instance;

  private static final Logger logger = LoggerFactory.getLogger(CamundaSupport.class);
  private final Set<String> deploymentIds = Sets.newHashSet();
  private ProcessEngine processEngine;
  private Date startTime;

  /**
   * Private constructor to avoid direct instantiation.
   */
  private CamundaSupport() {
    this.processEngine = TestHelper.getProcessEngine(CamundaInstancesSupplier.CAMUNDA_CONFIG_RESOURCE);
  }

  /**
   * Checks deployment of the process definition.
   * 
   * @param processModelResources
   *          process definition file (BPMN)
   */
  public void deploy(final String... processModelResources) {
    final DeploymentBuilder deploymentBuilder = processEngine.getRepositoryService().createDeployment();
    for (final String resource : processModelResources) {
      deploymentBuilder.addClasspathResource(resource);
    }
    this.deploymentIds.add(deploymentBuilder.deploy().getId());
    getStartTime();
  }

  /**
   * Cleans up resources.
   */
  public void undeploy() {
    for (final String deploymentId : deploymentIds) {
      processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
    }
    Mocks.reset();
  }

  /**
   * Starts process by process definition key with given payload.
   * 
   * @param processDefinitionKey
   *          process definition keys.
   * @param variables
   *          maps of initial payload variables.
   * @return process instance
   * @see RuntimeService#startProcessInstanceByKey(String, Map)
   */
  public ProcessInstance startProcessInstanceByKey(final String processDefinitionKey, final Map<String, Object> variables) {
    checkArgument(processDefinitionKey != null, "processDefinitionKey must not be null!");

    final FluentProcessInstance instance = FluentProcessEngineTests.newProcessInstance(processDefinitionKey);
    if (variables != null) {
      instance.setVariables(variables);
    }
    return instance.start().getDelegate();
  }

  /**
   * Starts process by process definition key.
   * 
   * @param processDefinitionKey
   *          process definition keys.
   * @return process instance
   */
  public ProcessInstance startProcessInstanceByKey(final String processDefinitionKey) {
    return startProcessInstanceByKey(processDefinitionKey, null);
  }

  /**
   * Retrieves the process instance.
   * 
   * @return running process instance.
   */
  public ProcessInstance getProcessInstance() {
    return FluentProcessEngineTests.processInstance();
  }

  /**
   * Sets time.
   * 
   * @param currentTime
   *          sets current time in the engine
   */
  public void setCurrentTime(final Date currentTime) {
    ClockUtil.setCurrentTime(currentTime);
  }

  /**
   * Resets process engine clock.
   */
  public void resetClock() {
    ClockUtil.reset();
  }

  /**
   * Retrieves camunda support instance.
   * 
   * @return singleton instance.
   */
  public static CamundaSupport getInstance() {
    if (instance == null) {
      instance = new CamundaSupport();
      logger.debug("Camunda Support created.");
    }
    return instance;
  }

  /**
   * Retrieves activiti process engine.
   * 
   * @return process engine.
   */
  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  /**
   * Retrieves start time.
   * 
   * @return time of deployment.
   */
  public Date getStartTime() {
    if (this.startTime == null) {
      this.startTime = new Date();
    }
    return this.startTime;
  }

  /**
   * Retrieves process variables of running instance.
   * 
   * @return process variables.
   */
  public Map<String, Object> getProcessVariables() {
    return getProcessVariables();
  }

  /**
   * Checks historic execution.
   * 
   * @param id
   *          activity name.
   */
  public void assertActivityVisitedOnce(final String id) {
    final List<HistoricActivityInstance> visits = getProcessEngine().getHistoryService().createHistoricActivityInstanceQuery().finished().activityId(id).list();
    assertThat("activity '" + id + "' not found!", visits, notNullValue());
    assertThat("activity '" + id + "' not found!", visits, not(IsEmptyCollection.empty()));
  }

  /**
   * finishes a task.
   * 
   * @param values
   */
  public void completeTask(final Object... values) {
    FluentProcessEngineTests.processInstance().task().complete(values);
  }

  /**
   * Parses the verb and maps it to a boolean decision.
   * 
   * @param negation
   *          a way how the verb is negated. (e.G. not)
   * @param value
   *          part of text containing the verb in regular or negated form.
   * @param defaultValue
   *          default value, if parsing fails.
   * @return true, if negation not found.
   */
  public static boolean parseStatement(final String negation, final String value, final boolean defaultValue) {
    return (value != null) ? !value.contains(negation) : defaultValue;
  }

}
