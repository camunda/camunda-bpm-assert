package org.camunda.bpm.bdd.steps;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.test.CamundaSupport;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic Camunda Steps.
 * 
 * @author Simon Zambrovski, Holisticon AG.
 */
public class CamundaSteps {

	private static final Logger LOG = LoggerFactory.getLogger(CamundaSteps.class);

	@Inject
	private CamundaSupport support;

	@BeforeStory
	public void init() {
		LOG.debug("Initializing before a story run.");
		FluentProcessEngineTests.before(support.getProcessEngine());
	}

	/**
	 * Clean up all resources.
	 */
	@AfterStory(uponGivenStory = false)
	public void cleanUp() {
		LOG.debug("Cleaning up after story run.");
		Mocks.reset();
		support.undeploy();
		support.resetClock();
		FluentProcessEngineTests.after();
	}

	@When("the process definition $processDefinition")
	@Given("the process definition $processDefinition")
	public void deployProcess(final String processDefinition) {
		support.deploy(processDefinition);
	}

	@When("the process $processKey is started")
	public void startProcess(final String processKey) {
		final ProcessInstance processInstance = support.startProcessInstanceByKey(processKey);
		assertNotNull(processInstance);
	}

	/**
	 * Process is finished.
	 */
	@Then("the process is finished")
	public void processIsFinished() {
		assertTrue("Process is not ended", FluentProcessEngineTests.processInstance().isEnded());
		LOG.debug("Process finished.");
	}
	
	@Then("the process is finished with event $eventName")
	public void processFinishedSucessfully(final String eventName) {
		assertTrue("Process is not ended", FluentProcessEngineTests.processInstance().isEnded());
		support.assertActivityVisitedOnce(eventName);
	}


	/**
	 * Process step reached.
	 * 
	 * @param activityId
	 *            name of the step to reach.
	 */
	@Then("the step $activityId is reached")
	@When("the step $activityId is reached")
	public void stepIsReached(final String activityId) {
		final Execution execution = support.getProcessEngine().getRuntimeService().createExecutionQuery()
				.processInstanceId(support.getProcessInstance().getId()).activityId(activityId).singleResult();
		assertNotNull(execution);
		LOG.debug("Step {} reached.", activityId);
	}
}
