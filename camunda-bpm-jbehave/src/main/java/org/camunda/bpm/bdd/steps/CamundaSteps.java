package org.camunda.bpm.bdd.steps;

import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.processInstance;

import javax.inject.Inject;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests;
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
    FluentProcessEngineTests.after();
  }

  @When("the process definition $processDefinition")
  @Given("the process definition $processDefinition")
  public void deployProcess(final String processDefinition) {
    FluentProcessEngineTests.deploy(processDefinition);
  }

  @When("the process $processDefinitionKey is started")
  public void startProcess(final String processDefinitionKey) {
    final ProcessInstance processInstance = FluentProcessEngineTests.newProcessInstance(processDefinitionKey).start();
    FluentProcessEngineTests.assertThat(processInstance).isActive();
  }

  /**
   * Process is finished.
   */
  @Then("the process is finished")
  public void processIsFinished() {
    FluentProcessEngineTests.assertThat(processInstance()).isEnded();
    LOG.debug("Process finished.");
  }

  @Then("the process is finished with event $eventName")
  public void processFinishedSucessfully(final String eventName) {
    FluentProcessEngineTests.assertThat(processInstance()).isFinishedAndPassedActivity(eventName);
  }

  /**
   * Process step reached.
   * 
   * @param activityId
   *          name of the step to reach.
   */
  @Then("the step $activityId is reached")
  @When("the step $activityId is reached")
  public void stepIsReached(final String activityId) {
    FluentProcessEngineTests.assertThat(processInstance()).isWaitingAt(activityId);
    LOG.debug("Step {} reached.", activityId);
  }
}
