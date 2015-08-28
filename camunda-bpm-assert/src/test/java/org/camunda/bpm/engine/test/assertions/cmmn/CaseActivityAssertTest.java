package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseExecutionQuery;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseService;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class CaseActivityAssertTest {

	public static final String TASK_A = "PI_TaskA";
	public static final String TASK_B = "PI_HT_B";

	@Rule
	public ProcessEngineRule processEngineRule = new ProcessEngineRule();

	@Test
	@Deployment(resources = { "cmmn/TaskTest.cmmn" })
	public void isActive_should_not_throw_exception_when_task_is_active() {
		// Given
		CaseInstance caseInstance = aCaseWithAnActiveTask();
		// When
		assertThat(caseInstance).task(TASK_A).isActive();
		// Then
		// nothing should happen
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = { "cmmn/TaskTest.cmmn" })
	public void isActive_should_throw_AssertionError_when_task_is_not_active() {
		// Given
		CaseInstance caseInstance = aCaseWithACompletedTask();
		// When
		assertThat(caseInstance).task(TASK_A).isActive();
		// Then
		// AssertionError should be thrown
	}

	@Test
	@Deployment(resources = { "cmmn/TaskWithSentryTest.cmmn" })
	public void isAvailable_should_not_throw_exception_when_task_is_available() {
		// Given
		CaseInstance caseInstance = aCaseWithAnAvailbaleTask();
		// When
		assertThat(caseInstance).task(TASK_B).isAvailable();
		// Then
		// nothing should happen
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = { "cmmn/TaskTest.cmmn" })
	public void isAvailable_should_throw_AssertionError_when_task_is_not_active() {
		// Given
		CaseInstance caseInstance = aCaseWithACompletedTask();
		// When
		assertThat(caseInstance).task(TASK_A).isActive();
		// Then
		// AssertionError should be thrown
	}

	private CaseInstance aCaseWithACompletedTask() {
		CaseInstance caseInstance = aCaseWithAnActiveTask();
		caseService().completeCaseExecution(
				caseExecutionQuery().activityId(TASK_A).singleResult().getId());
		return caseInstance;
	}

	private CaseInstance aCaseWithAnAvailbaleTask() {
		return caseService()
				.createCaseInstanceByKey("Case_TaskWithSentryTests");
	}

	private CaseInstance aCaseWithAnActiveTask() {
		return caseService().createCaseInstanceByKey("Case_TaskTests");
	}
}
