package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseExecutionQuery;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.historyService;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class CaseInstanceAssertTest {

	public static final String TASK_A = "PI_TaskA";

	@Rule
	public ProcessEngineRule processEngineRule = new ProcessEngineRule();

	@Test
	@Deployment(resources = { "cmmn/TaskTest.cmmn" })
	public void isActive_should_not_throw_exception_when_case_is_active() {
		// Given
		CaseInstance caseInstance = aStartedCase();
		// When
		assertThat(caseInstance).isActive();
		// Then
		// nothing should happen
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = { "cmmn/TaskTest.cmmn" })
	public void isActive_should_throw_AssertionError_when_case_is_not_active() {
		// Given
		CaseInstance caseInstance = aCompletedCase();
		// When
		assertThat(caseInstance).isActive();
		// Then
		// AssertionError should be thrown
	}

	@Test
	@Deployment(resources = { "cmmn/TaskTest.cmmn" })
	public void isCompleted_should_not_throw_exception_when_case_is_active() {
		// Given
		CaseInstance caseInstance = aCompletedCase();
		// When
		assertThat(caseInstance).isCompleted();
		// Then
		// nothing should happen
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = { "cmmn/TaskTest.cmmn" })
	public void isCompleted_should_throw_AssertionError_when_case_is_not_completed() {
		// Given
		CaseInstance caseInstance = aStartedCase();
		// When
		assertThat(caseInstance).isCompleted();
		// Then
		// AssertionError should be thrown
	}

	@Test
	@Deployment(resources = { "cmmn/TaskTest.cmmn" })
	public void task_should_return_CaseTaskAssert_for_completed_tasks() {
		// Given
		CaseInstance caseInstance = aCompletedCase();
		HistoricCaseActivityInstance pi_taskA = historyService()
				.createHistoricCaseActivityInstanceQuery()
				.caseActivityId(TASK_A).singleResult();
		// When
		CaseActivityAssert caseTaskAssert = assertThat(caseInstance).task(
				TASK_A);
		// Then
		HistoricCaseActivityInstance actual = caseTaskAssert.getActual();
		Assertions
				.assertThat(actual)
				.overridingErrorMessage(
						"Expected case execution " + toString(actual)
								+ " to be equal to " + toString(pi_taskA))
				.isEqualToComparingOnlyGivenFields(pi_taskA, "id");
	}

	@Test
	@Deployment(resources = { "cmmn/TaskTest.cmmn" })
	public void task_should_return_CaseTaskAssert_for_given_activityId() {
		// Given
		CaseInstance caseInstance = aStartedCase();
		HistoricCaseActivityInstance pi_taskA = historyService()
				.createHistoricCaseActivityInstanceQuery()
				.caseActivityId(TASK_A).singleResult();
		// When
		CaseActivityAssert caseTaskAssert = assertThat(caseInstance).task(
				TASK_A);
		// Then
		HistoricCaseActivityInstance actual = caseTaskAssert.getActual();
		Assertions
				.assertThat(actual)
				.overridingErrorMessage(
						"Expected case execution " + toString(actual)
								+ " to be equal to " + toString(pi_taskA))
				.isEqualToComparingOnlyGivenFields(pi_taskA, "id");
	}

	private CaseInstance aCompletedCase() {
		CaseInstance caseInstance = aStartedCase();

		caseService().completeCaseExecution(
				caseExecutionQuery().activityId(TASK_A).singleResult().getId());

		return caseInstance;
	}

	private CaseInstance aStartedCase() {
		return caseService().createCaseInstanceByKey("Case_TaskTests");
	}

	protected String toString(
			HistoricCaseActivityInstance historicCaseActivityInstance) {
		return historicCaseActivityInstance != null ? String.format("%s {"
				+ "id='%s', " + "caseDefinitionId='%s', " + "activityType='%s'"
				+ "}", historicCaseActivityInstance.getClass().getSimpleName(),
				historicCaseActivityInstance.getId(),
				historicCaseActivityInstance.getCaseDefinitionId(),
				historicCaseActivityInstance.getCaseActivityType()) : null;
	}

}
