package org.camunda.bpm.test;

import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.impl.test.TestHelper;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ExecutionQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.mock.Mocks;
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
	 * Configuration name.
	 */
	private static final String CONFIG_RESOURCE = "camunda-needle.cfg.xml";

	/**
	 * Singleton instance.
	 */
	private static CamundaSupport instance;

	private static final Logger logger = LoggerFactory.getLogger(CamundaSupport.class);
	private final Set<String> deploymentIds = Sets.newHashSet();
	private ProcessEngine processEngine;
	private ProcessInstance processInstance;
	private Date startTime;

	/**
	 * Private constructor to avoid direct instantiation.
	 */
	private CamundaSupport() {
		this.processEngine = TestHelper.getProcessEngine(CONFIG_RESOURCE);
	}

	/**
	 * Checks deployment of the process definition.
	 * 
	 * @param processModelResources
	 *            process definition file (BPMN)
	 */
	public void deploy(String... processModelResources) {
		final DeploymentBuilder deploymentBuilder = processEngine.getRepositoryService().createDeployment();
		for (String resource : processModelResources) {
			deploymentBuilder.addClasspathResource(resource);
		}
		this.deploymentIds.add(deploymentBuilder.deploy().getId());
		getStartTime();
	}

	/**
	 * Cleans up resources.
	 */
	public void undeploy() {
		for (String deploymentId : deploymentIds) {
			processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
		}
		Mocks.reset();
	}

	/**
	 * Starts process by process definition key with given payload.
	 * 
	 * @param processDefinitionKey
	 *            process definition keys.
	 * @param variables
	 *            maps of initial payload variables.
	 * @return process instance
	 * @see RuntimeService#startProcessInstanceByKey(String, Map)
	 */
	public ProcessInstance startProcessInstanceByKey(final String processDefinitionKey, final Map<String, Object> variables) {
		checkArgument(processDefinitionKey != null, "processDefinitionKey must not be null!");
		final RuntimeService runtimeService = getProcessEngine().getRuntimeService();
		if (variables == null) {
			setProcessInstance(runtimeService.startProcessInstanceByKey(processDefinitionKey));
		} else {
			setProcessInstance(runtimeService.startProcessInstanceByKey(processDefinitionKey, variables));
		}

		return getProcessInstance();
	}

	/**
	 * Starts process by process definition key.
	 * 
	 * @param processDefinitionKey
	 *            process definition keys.
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
		return processInstance;
	}

	/**
	 * Sets running process instance.
	 * 
	 * @param processInstance
	 *            instance to set.
	 */
	public void setProcessInstance(final ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	/**
	 * Sets time.
	 * 
	 * @param currentTime
	 *            sets current time in the engine
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
			logger.info("Camunda Support created.");
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
		final ExecutionQuery eQuery = getProcessEngine().getRuntimeService().createExecutionQuery().processInstanceId(processInstance.getId());
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
	 * 
	 * @param name
	 *            activity name.
	 */
	public void assertActivityVisitedOnce(final String name) {
		final HistoricActivityInstance singleResult = getProcessEngine().getHistoryService().createHistoricActivityInstanceQuery().finished().activityId(name)
				.singleResult();
		assertThat("activity '" + name + "' not found!", singleResult, notNullValue());
	}

}
