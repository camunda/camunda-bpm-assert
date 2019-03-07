package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.caseExecution;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.caseService;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.disable;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTaskAssertIsDisabledTest extends ProcessAssertTestCase {

  public static final String TASK_A = "PI_TaskA";
  public static final String CASE_KEY = "Case_ProcessTaskAssertIsDisabledTest";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = { "cmmn/ProcessTaskAssertIsDisabledTest.cmmn", "cmmn/ProcessTaskAssert-calledProcess.bpmn" })
  public void testIsDisabled_Success() {
    // Given
    final CaseInstance caseInstance = givenCaseIsCreated();
    ProcessTaskAssert processTask = assertThat(caseInstance).processTask(TASK_A);
    // When
    disable(caseExecution(TASK_A, caseInstance));
    // Then
    processTask.isDisabled();
  }

  @Test
  @Deployment(resources = { "cmmn/ProcessTaskAssertIsDisabledTest.cmmn", "cmmn/ProcessTaskAssert-calledProcess.bpmn" })
  public void testIsDisabled_Failure() {
    // Given
    // When
    final CaseInstance caseInstance = givenCaseIsCreated();
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(caseInstance).processTask(TASK_A).isDisabled();
      }
    });
  }

  private CaseInstance givenCaseIsCreated() {
    return caseService().createCaseInstanceByKey(CASE_KEY);
  }
}