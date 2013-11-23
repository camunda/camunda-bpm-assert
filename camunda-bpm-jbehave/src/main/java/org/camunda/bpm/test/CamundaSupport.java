package org.camunda.bpm.test;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Date;
import java.util.Map;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.fluent.FluentProcessInstance;
import org.camunda.bpm.engine.impl.test.TestHelper;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests;
import org.camunda.bpm.engine.test.needle.supplier.CamundaInstancesSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper for camunda access.
 * 
 * @deprecated should/could be removed since all functionality moved to
 *             {@link FluentProcessEngineTests}
 * @author Simon Zambrovski, Holisticon AG
 * 
 */
@Deprecated
public class CamundaSupport {

  /**
   * Singleton instance.
   */
  private static CamundaSupport instance;

  private static final Logger logger = LoggerFactory.getLogger(CamundaSupport.class);
  private ProcessEngine processEngine;
  private Date startTime;

  /**
   * Private constructor to avoid direct instantiation.
   */
  private CamundaSupport() {
    this.processEngine = TestHelper.getProcessEngine(CamundaInstancesSupplier.CAMUNDA_CONFIG_RESOURCE);
  }

  /**
   * Starts process by process definition key with given payload.
   * 
   * @param processDefinitionKey
   *          process definition keys.
   * @param variables
   *          maps of initial payload variables.
   * @return process instance
   * @deprecated use {@link FluentProcessEngineTests#newProcessInstance(String)}
   *             directly
   * @see RuntimeService#startProcessInstanceByKey(String, Map)
   */
  @Deprecated
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
   * @deprecated use {@link FluentProcessEngineTests#newProcessInstance(String)}
   *             directly
   * @param processDefinitionKey
   *          process definition keys.
   * @return process instance
   */
  @Deprecated
  public ProcessInstance startProcessInstanceByKey(final String processDefinitionKey) {
    return startProcessInstanceByKey(processDefinitionKey, null);
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
