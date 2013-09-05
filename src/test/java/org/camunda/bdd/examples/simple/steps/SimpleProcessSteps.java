package org.camunda.bdd.examples.simple.steps;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import javax.inject.Inject;

import org.camunda.bdd.examples.simple.SimpleProcess.Elements;
import org.camunda.bdd.examples.simple.SimpleProcess.Events;
import org.camunda.bdd.examples.simple.SimpleProcess.Variables;
import org.camunda.bdd.examples.simple.SimpleProcessAdapter;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.test.mock.Mocks;
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
		Mocks.register(SimpleProcessAdapter.NAME, simpleProcessAdapter);
	}

	@AfterScenario
	public void cleanUp() {
		Mockito.reset(simpleProcessAdapter);
	}

	@Given("the contract is automatically processible")
	public void loadContractDataAutomatically() {
		loadContractData(true);
	}

	@Given("the contract is not automatically processible")
	public void loadContractDataNotAutomatically() {
		loadContractData(false);
	}

	@Given("the contract processing succeeds")
	public void processingSucceeds() {
		processAutomatically(false);
	}

	@Given("the contract processing fails")
	public void processingFails() {
		processAutomatically(true);
	}

	@Then("the contract is loaded")
	public void contractIsLoaded() {
		support.assertActivityVisitedOnce(Elements.SERVICE_LOAD_CONTRACT_DATA);
	}

	@Then("the contract is processed automatically")
	public void contractIsProcessed() {
		support.assertActivityVisitedOnce(Elements.SERVICE_PROCESS_CONTRACT_AUTOMATICALLY);
	}

	@Then("the process is finished with event Contract processed")
	public void processFinishedSucessfully() {
		support.assertActivityVisitedOnce(Elements.EVENT_CONTRACT_PROCESSED);
	}

	public void loadContractData(final boolean isAutomatically) {
		when(simpleProcessAdapter.loadContractData()).thenReturn(isAutomatically);
	}

	public void processAutomatically(final boolean withErrors) {
		if (withErrors) {
			doThrow(new BpmnError(Events.ERROR_PROCESS_AUTOMATICALLY_FAILED)).when(simpleProcessAdapter).processContract();
		}
	}

	@When("contract is processed manually")
	public void processManuallyWithSucess() {
		support.completeTask(Variables.ARE_PROCESSING_ERRORS_PRESENT, Boolean.FALSE);
	}

	@When("contract is processed manually with errors")
	public void processManuallyWithErrors() {
		support.completeTask(Variables.ARE_PROCESSING_ERRORS_PRESENT, Boolean.TRUE);
	}
}
