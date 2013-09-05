package org.camunda.bpm.test;

import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.Map;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.fluent.FluentProcessInstance;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.impl.test.TestHelper;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ExecutionQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.test.needle.supplier.CamundaInstancesSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper for camunda access.
 * @author Simon Zambrovski, Holisticon AG
 */
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
     * Cleans up resources.
     */
    public void undeploy() {
        for (final String deploymentId : FluentProcessEngineTests.getDeploymentIds()) {
            processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
        }
        Mocks.reset();
    }

    /**
     * Starts process by process definition key with given payload.
     * @param processDefinitionKey
     *        process definition keys.
     * @param variables
     *        maps of initial payload variables.
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
     * @param processDefinitionKey
     *        process definition keys.
     * @return process instance
     */
    public ProcessInstance startProcessInstanceByKey(final String processDefinitionKey) {
        return startProcessInstanceByKey(processDefinitionKey, null);
    }

    /**
     * Retrieves the process instance.
     * @return running process instance.
     */
    public ProcessInstance getProcessInstance() {
        return FluentProcessEngineTests.processInstance();
    }

    /**
     * Sets time.
     * @param currentTime
     *        sets current time in the engine
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
     * @return singleton instance.
     */
    public static CamundaSupport getInstance() {
        if (instance == null) {
            instance = new CamundaSupport();
            logger.info("Camunda Support created.");
        }
        return instance;
    }

    /**
     * Retrieves activiti process engine.
     * @return process engine.
     */
    public ProcessEngine getProcessEngine() {
        return processEngine;
    }

    /**
     * Retrieves start time.
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
     * @return process variables.
     */
    public Map<String, Object> getProcessVariables() {
        final ExecutionQuery eQuery = getProcessEngine().getRuntimeService().createExecutionQuery().processInstanceId(getProcessInstance().getId());
        final Execution execution;
        if (eQuery.count() == 1) {
            execution = eQuery.singleResult();
        } else {
            // hack
            execution = eQuery.list().get(0);
        }
        assertNotNull(execution);
        return getProcessEngine().getRuntimeService().getVariables(execution.getId());
    }

    /**
     * Checks historic execution.
     * @param id
     *        activity name.
     */
    public void assertActivityVisitedOnce(final String id) {
        final HistoricActivityInstance singleResult = getProcessEngine().getHistoryService().createHistoricActivityInstanceQuery().finished()
                .activityId(id).singleResult();
        assertThat("activity '" + id + "' not found!", singleResult, notNullValue());
    }

    public void completeTask(final Object... values) {
        FluentProcessEngineTests.processInstance().task().complete(values);
    }

}
