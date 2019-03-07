package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.caseExecution;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.caseService;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.complete;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

public class HumanTaskAssertIsCompletedTest extends ProcessAssertTestCase {

  public static final String TASK_A = "PI_TaskA";
  public static final String CASE_KEY = "Case_HumanTaskAssertIsCompletedTest";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = { "cmmn/HumanTaskAssertIsCompletedTest.cmmn" })
  public void testIsCompleted_Success() {
    // Given
    final CaseInstance caseInstance = givenCaseIsCreated();
    HumanTaskAssert humanTask = assertThat(caseInstance).humanTask(TASK_A);
    // When
    complete(caseExecution(TASK_A, caseInstance));
    // Then
    humanTask.isCompleted();
  }

  @Test
  @Deployment(resources = { "cmmn/HumanTaskAssertIsCompletedTest.cmmn" })
  public void testIsCompleted_Failure() {
    // Given
    // case model is deployed
    // When
    final CaseInstance caseInstance = givenCaseIsCreated();
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(caseInstance).humanTask(TASK_A).isCompleted();
      }
    });
  }

  private CaseInstance givenCaseIsCreated() {
    return caseService().createCaseInstanceByKey(CASE_KEY);
  }
}