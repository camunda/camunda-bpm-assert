package org.camunda.bdd.examples.simple.steps;

import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.processInstance;
import static org.camunda.bpm.test.CamundaSupport.parseStatement;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import javax.inject.Inject;

import org.camunda.bdd.examples.simple.SimpleProcessAdapter;
import org.camunda.bdd.examples.simple.SimpleProcessConstants.Elements;
import org.camunda.bdd.examples.simple.SimpleProcessConstants.Events;
import org.camunda.bdd.examples.simple.SimpleProcessConstants.Variables;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.test.Expressions;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests;
import org.camunda.bpm.test.CamundaSupport;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.mockito.Mockito;

/**
 * Specific process steps.
 * 
 * @author Simon Zambrovski, Holisticon AG.
 */
public class SimpleProcessSteps {

  @Inject
  private SimpleProcessAdapter simpleProcessAdapter;

  @Inject
  private CamundaSupport support;

  @BeforeScenario
  public void initMocks() {
    Expressions.registerInstance(SimpleProcessAdapter.NAME, simpleProcessAdapter);
  }

  @AfterScenario
  public void resetMocks() {
    Mockito.reset(simpleProcessAdapter);
  }

  @Given("the contract $verb automatically processible")
  public void loadContractDataAutomatically(final String verb) {
    final boolean processingPossible = parseStatement("not", verb, false);
    when(simpleProcessAdapter.loadContractData()).thenReturn(processingPossible);
  }

  @Given("the contract processing $verb")
  public void processingAutomatically(final String verb) {
    final boolean withErrors = parseStatement("succeeds", verb, false);
    if (withErrors) {
      doThrow(new BpmnError(Events.ERROR_PROCESS_AUTOMATICALLY_FAILED)).when(simpleProcessAdapter).processContract();
    }
  }

  @Then("the contract is loaded")
  public void contractIsLoaded() {
    FluentProcessEngineTests.assertThat(processInstance()).isFinishedAndPassedActivity(Elements.SERVICE_LOAD_CONTRACT_DATA);
  }

  @Then("the contract is processed automatically")
  public void contractIsProcessed() {
    FluentProcessEngineTests.assertThat(processInstance()).isFinishedAndPassedActivity(Elements.SERVICE_PROCESS_CONTRACT_AUTOMATICALLY);
  }

  @Then("the contract processing is cancelled")
  public void cancelledProcessing() {
    FluentProcessEngineTests.assertThat(processInstance()).isFinishedAndPassedActivity(Elements.SERVICE_CANCEL_PROCESSING);
  }

  @When("the contract is processed $withoutErrors")
  public void processManuallys(final String withoutErrors) {
    final boolean hasErrors = !parseStatement("with errors", withoutErrors, false);
    FluentProcessEngineTests.task().complete(Variables.ARE_PROCESSING_ERRORS_PRESENT, Boolean.valueOf(hasErrors));
  }
}
