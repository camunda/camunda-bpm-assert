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

public class CaseTaskAssertIsActiveTest extends ProcessAssertTestCase {

  public static final String TASK_A = "PI_TaskA";
  public static final String TASK_B = "PI_TaskB";
  public static final String CASE_KEY = "Case_CaseTaskAssertIsActiveTest";
  public static final String CASE_KEY_B = "Case_CaseTaskAssertIsActiveTest_CaseB";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = { "cmmn/CaseTaskAssertIsActiveTest.cmmn" })
  public void testIsActive_Success() {
    // Given
    // case model is deployed
    // When
    CaseInstance caseInstance = givenCaseIsCreated();
    // Then
    assertThat(caseInstance).caseTask(TASK_A).isActive();
  }

  @Test
  @Deployment(resources = { "cmmn/CaseTaskAssertIsActiveTest.cmmn" })
  public void testIsActive_Failure() {
    // Given
    final CaseInstance caseInstance = givenCaseIsCreated();
    CaseInstance caseInstanceB = caseService().createCaseInstanceQuery().caseDefinitionKey(CASE_KEY_B).singleResult();
    // When
    complete(caseExecution(TASK_B, caseInstanceB));
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(caseInstance).caseTask(TASK_A).isActive();
      }
    });
  }

  private CaseInstance givenCaseIsCreated() {
    return caseService().createCaseInstanceByKey(CASE_KEY);
  }
}