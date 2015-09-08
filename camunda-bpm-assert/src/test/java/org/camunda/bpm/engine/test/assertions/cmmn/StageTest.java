package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseExecution;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.manuallyStart;

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
	public void case_is_active_and_stage_and_task_should_be_enabled() {
		// Given
		// case model is deployed
		// When
		CaseInstance caseInstance = givenCaseIsCreated();
		// Then
		assertThat(caseInstance).isActive().stage(STAGE_S).isEnabled();
		// check task not yet created
		// .task(TASK_A).isEnabled();
	}

	@Test
	@Deployment(resources = { "cmmn/StageTest.cmmn" })
	public void stage_should_be_active_and_taske_enabled() {
		// Given
		CaseInstance caseInstance = givenCaseIsCreated();
		// When
		manuallyStart(caseExecution(STAGE_S, caseInstance));
		// Then
		assertThat(caseInstance).isActive().stage(STAGE_S).isActive().task(TASK_A).isEnabled();
	}

	@Test
	@Deployment(resources = { "cmmn/StageTest.cmmn" })
	public void stage_and_task_should_be_active() {
		// Given
		CaseInstance caseInstance = givenCaseIsCreatedAndStageSActive();
		// When
		manuallyStart(caseExecution(TASK_A, caseInstance));
		// Then
		assertThat(caseInstance).isActive().stage(STAGE_S).isActive().task(TASK_A).isActive();
	}

	@Test
	@Deployment(resources = { "cmmn/StageTest.cmmn" })
	public void case_should_complete_when_task_is_completed() {
		// Given
		CaseInstance caseInstance = givenCaseIsCreatedAndStageSActiveAndTaskAActive();
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
		CaseInstance caseInstance = givenCaseIsCreatedAndStageSActiveAndTaskAActive();
		// When
		// terminate();
		// Then
		assertThat(caseInstance).isCompleted().stage(STAGE_S).isCompleted();
	}

	private CaseInstance givenCaseIsCreated() {
		CaseInstance caseInstance = caseService().createCaseInstanceByKey("Case_StageTests");
		return caseInstance;
	}

	private CaseInstance givenCaseIsCreatedAndStageSActive() {
		CaseInstance caseInstance = givenCaseIsCreated();
		manuallyStart(caseExecution(STAGE_S, caseInstance));
		return caseInstance;
	}

	private CaseInstance givenCaseIsCreatedAndStageSActiveAndTaskAActive() {
		CaseInstance caseInstance = givenCaseIsCreatedAndStageSActive();
		manuallyStart(caseExecution(TASK_A, caseInstance));
		return caseInstance;
	}

}
