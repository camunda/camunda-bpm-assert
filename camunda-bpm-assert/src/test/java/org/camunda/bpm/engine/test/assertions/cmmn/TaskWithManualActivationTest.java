package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseActivity;

/**
 * This test is meant to help building the fluent API by providing simple test
 * cases.
 *
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class TaskWithManualActivationTest {

	public static final String TASK_A = "PI_TaskA";
	public static final String CASE_KEY = "Case_TaskTests";

	@Rule
	public ProcessEngineRule processEngineRule = new ProcessEngineRule();

	@Test
	@Deployment(resources = { "cmmn/TaskWithManualActivationTest.cmmn" })
	/**
	 * Introduces:
   * activity.isEnabled()
	 */
	public void task_should_be_enabled() {
		// Given
		// case model is deployed
		// When creating a new case instance
		CaseInstance caseInstance = caseService().createCaseInstanceByKey(
				CASE_KEY);
		// Then task A should be enabled
		assertThat(caseActivity(TASK_A, caseInstance)).isEnabled();
	}

	@Test
	@Deployment(resources = { "cmmn/TaskWithManualActivationTest.cmmn" })
	/**
	 * Introduces:
	 * manuallyStart(caseExecution)
	 */
	public void task_should_be_active_when_manually_started() {
		// Given
		CaseInstance caseInstance = givenCaseIsCreated();
		// When
		manuallyStart(caseExecution(TASK_A, caseInstance));
		// Then
		assertThat(caseActivity(TASK_A, caseInstance)).isActive();
	}

	private CaseInstance givenCaseIsCreated() {
		return caseService().createCaseInstanceByKey(CASE_KEY);
	}
}
