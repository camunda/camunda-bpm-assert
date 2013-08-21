package org.camunda.bdd.examples.simple;

import static org.mockito.Mockito.mock;

import javax.inject.Inject;

import org.camunda.bdd.examples.simple.SimpleProcess.Elements;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.needle.ProcessEngineNeedleRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Deployment unit test of simple process.
 * 
 * @author Simon Zambrovski, Holisticon AG.
 * 
 */
public class SimpleDeploymentTest {

	@Rule
	public ProcessEngineNeedleRule processEngine = new ProcessEngineNeedleRule(this);

	@Inject
	private SimpleProcessAdapter simpleProcessAdapter;

	class Glue {
		public void loadContractData(final boolean isAutomatically) {
			when(simpleProcessAdapter.loadContractData()).thenReturn(isAutomatically);
		}

		public void startSimpleProcess() {
			ProcessInstance startProcessInstance = processEngine.startProcessInstanceByKey(SimpleProcess.PROCESS);
			assertNotNull(startProcessInstance);
		}

		public void processAutomatically(final boolean withErrors) {
			if (withErrors) {
				// simulate error event.
			}
		}

		/**
		 * Assert that process execution has run through the activity with given id.
		 * 
		 * @param name
		 *            name of the activity.
		 */
		private void assertActivityVisitedOnce(final String name) {
			final HistoricActivityInstance singleResult = processEngine.getHistoryService().createHistoricActivityInstanceQuery().finished().activityId(name)
					.singleResult();
			assertNotNull(singleResult);
		}

		/**
		 * Assert process end event.
		 * 
		 * @param name
		 *            name of the end event.
		 */
		private void assertEndEvent(final String name) {
			assertActivityVisitedOnce(name);
			processEngine.assertNoMoreRunningInstances();

		}

	}

	private final Glue glue = new Glue();

	@Before
	public void initMocks() {
		Mocks.register(SimpleProcessAdapter.NAME, mock(SimpleProcessAdapter.class));
	}

	@Test
	@Deployment(resources = SimpleProcess.BPMN)
	public void shouldDeploy() {
		// nothing to do.
	}

	@Test
	@Deployment(resources = SimpleProcess.BPMN)
	public void shouldStartAndRunAutomatically() {

		// given
		glue.startSimpleProcess();

		// when
		glue.loadContractData(true);
		glue.processAutomatically(false);

		// then
		glue.assertEndEvent(Elements.EVENT_CONTRACT_PROCESSED);
	}
}
