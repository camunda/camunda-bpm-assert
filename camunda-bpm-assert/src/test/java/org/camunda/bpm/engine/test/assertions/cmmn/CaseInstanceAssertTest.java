package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.assertThat;

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
	public void testReturnsCaseTaskAssertForCompletedTasks() {
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
	public void testReturnsHumanTaskAssertForGivenActivityId() {
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
	
	@Test
  @Deployment(resources = { "cmmn/TaskTest.cmmn" })
  public void testIsCaseInstance() {
    // Given
    CaseInstance caseInstance = aStartedCase();
    // Then
    assertThat((CaseExecution) caseInstance).isCaseInstance();
  }
	
	private CaseInstance aStartedCase() {
		return caseService().createCaseInstanceByKey("Case_TaskTests");
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
