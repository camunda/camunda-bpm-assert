package org.camunda.bpm.bdd.steps;

import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.test.CamundaSupport;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic Camunda Steps.
 * @author Simon Zambrovski, Holisticon AG.
 */
public class CamundaSteps {

    private static final Logger LOG = LoggerFactory.getLogger(CamundaSteps.class);

    @Inject
    private CamundaSupport support;

    @BeforeScenario
    public void init() {
        FluentProcessEngineTests.before(support.getProcessEngine());
    }

    /**
     * Clean up all resources.
     */
    @AfterScenario
    public void cleanUp() {
        LOG.info("Cleaning up.");
        Mocks.reset();
        support.undeploy();
        support.resetClock();
        FluentProcessEngineTests.after();
    }

    @When("the process definition \"$processDefinition\"")
    @Given("the process definition \"$processDefinition\"")
    public void deployProcess(final String processDefinition) {
        deploy(processDefinition);
    }

    @When("the process \"$processKey\" is started")
    public void startSimpleProcess(final String processKey) {
        final ProcessInstance processInstance = support.startProcessInstanceByKey(processKey);
        assertNotNull(processInstance);
    }

    /**
     * Process is finished.
     */
    @Then("the process is finished")
    public void processIsFinished() {
        assertThat(support.getProcessEngine().getRuntimeService().createProcessInstanceQuery().active().count(), is(0L));
        LOG.info("Process finished.");
    }

    /**
     * Process step reached.
     * @param activityId
     *        name of the step to reach.
     */
    @Then("the step \"$activityId\" is reached")
    @When("the step \"$activityId\" is reached")
    public void stepIsReached(final String activityId) {
        final Execution execution = support.getProcessEngine().getRuntimeService().createExecutionQuery()
                .processInstanceId(support.getProcessInstance().getId()).activityId(activityId).singleResult();
        assertNotNull(execution);
        LOG.info("Step reached {}", activityId);
    }
}
