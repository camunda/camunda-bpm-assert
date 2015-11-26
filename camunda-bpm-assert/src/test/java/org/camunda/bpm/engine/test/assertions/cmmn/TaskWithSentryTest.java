package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.cmmn.ProcessEngineTests.*;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class TaskWithSentryTest extends ProcessAssertTestCase {

	public static final String TASK_A = "PI_HT_A";
	public static final String TASK_B = "PI_HT_B";
	public static final String CASE_KEY = "Case_TaskWithSentryTests";

	@Rule
	public ProcessEngineRule processEngineRule = new ProcessEngineRule();

	@Test
	@Deployment(resources = { "cmmn/TaskWithSentryTest.cmmn" })
	public void task_b_should_be_available() {
		// Given
		CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);
		// Then
		assertThat(caseExecution(TASK_A, caseInstance)).isActive();
		// And
		assertThat(caseExecution(TASK_B, caseInstance)).isAvailable();
	}
	
}
