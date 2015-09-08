package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseExecution;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.manuallyStart;

import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class StageTest {

	public static final String TASK_A = "PI_TaskA";
	public static final String STAGE_S = "PI_StageS";

	@Rule
	public ProcessEngineRule processEngineRule = new ProcessEngineRule();

	@Test
	@Deployment(resources = { "cmmn/StageTest.cmmn" })
	public void case_and_stage_and_task_should_be_active() {
		// Given
		// case model is deployed
		// When
		CaseInstance caseInstance = givenCaseIsCreated();
		// Then

		CaseExecution caseExecution = caseService().createCaseExecutionQuery().activityId(TASK_A).singleResult();
		assertThat(caseExecution).isNotNull();

		assertThat(caseInstance).isActive().stage(STAGE_S).isActive().task(TASK_A).isActive();
	}

	@Test
	@Deployment(resources = { "cmmn/StageTest.cmmn" })
	public void case_should_complete_when_task_is_completed() {
		// Given
		CaseInstance caseInstance = givenCaseIsCreated();
		// When
		complete(caseExecution(TASK_A, caseInstance));
		// Then
		assertThat(caseInstance).isCompleted().stage(STAGE_S).isCompleted().task(TASK_A).isCompleted();
	}

	@Test
	@Ignore
	@Deployment(resources = { "cmmn/StageTest.cmmn" })
	public void case_should_complete_when_task_is_terminated() {
		// Given
		CaseInstance caseInstance = givenCaseIsCreated();
		// When
		// terminate(caseExecution(TASK_A, caseInstance));
		// Then
		assertThat(caseInstance).isCompleted().stage(STAGE_S).isCompleted();
	}

	private CaseInstance givenCaseIsCreated() {
		CaseInstance caseInstance = caseService().createCaseInstanceByKey("Case_StageTests");
		manuallyStart(caseExecution(STAGE_S, caseInstance));
		manuallyStart(caseExecution(TASK_A, caseInstance));
		return caseInstance;
	}

	private CaseInstance givenCaseIsCreatedAndTaskACompleted() {
		CaseInstance caseInstance = givenCaseIsCreated();
		complete(caseExecution(TASK_A, caseInstance));
		return caseInstance;
	}
}
