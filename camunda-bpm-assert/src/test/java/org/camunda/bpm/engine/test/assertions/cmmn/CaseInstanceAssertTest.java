package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class CaseInstanceAssertTest extends ProcessAssertTestCase {

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

	@Test
	@Deployment(resources = { "cmmn/TaskTest.cmmn" })
	public void isCompleted_should_throw_AssertionError_when_case_is_not_completed() {
		// Given
		final CaseInstance caseInstance = aStartedCase();
		// Then
		expect(new Failure() {
			@Override
			public void when() {
				assertThat(caseInstance).isCompleted();
			}
		});
	}

	@Test
	@Deployment(resources = { "cmmn/TaskTest.cmmn" })
	public void task_should_return_CaseTaskAssert_for_completed_tasks() {
		// Given
		CaseInstance caseInstance = aStartedCase();
		CaseExecution taskA = caseExecution(TASK_A, caseInstance);
		// When
		caseService().completeCaseExecution(caseExecutionQuery().activityId(TASK_A).singleResult().getId());
    // Then
		assertThat(taskA).isCompleted();
		// And
		assertThat(caseInstance).isCompleted();
	}

	@Test
	@Deployment(resources = { "cmmn/TaskTest.cmmn" })
	public void task_should_return_HumanTaskAssert_for_given_activityId() {
		// Given
		CaseInstance caseInstance = aStartedCase();
		CaseExecution pi_taskA = caseService()
				.createCaseExecutionQuery()
				.activityId(TASK_A).singleResult();
		// Then
		assertThat(caseInstance).isActive();
		// When
		HumanTaskAssert caseTaskAssert = assertThat(caseInstance).humanTask(TASK_A);
		// Then
		CaseExecution actual = caseTaskAssert.getActual();
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

  protected String toString(
    CaseExecution caseExecution) {
    return caseExecution != null ? String.format("%s {"
        + "id='%s', " + "caseDefinitionId='%s', " + "activityType='%s'"
        + "}", caseExecution.getClass().getSimpleName(),
      caseExecution.getId(),
      caseExecution.getCaseDefinitionId(),
      caseExecution.getActivityType()) : null;
  }
}
