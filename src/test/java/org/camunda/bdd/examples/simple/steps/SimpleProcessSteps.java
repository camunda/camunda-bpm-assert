package org.camunda.bdd.examples.simple.steps;

import static org.mockito.Mockito.when;

import javax.inject.Inject;

import org.camunda.bdd.examples.simple.SimpleProcess.Elements;
import org.camunda.bdd.examples.simple.SimpleProcessAdapter;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.test.CamundaSupport;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * Specific process steps.
 * 
 * @author Simon Zambrovski, Holisticon AG.
 * 
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

	@When("the contract is automatically processible")
	public void loadContractDataAutomatically() {
		loadContractData(true);
	}

	@When("the contract processing succeeds")
	public void processingSucceeds() {
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
			// simulate error event.
		}
	}

}
