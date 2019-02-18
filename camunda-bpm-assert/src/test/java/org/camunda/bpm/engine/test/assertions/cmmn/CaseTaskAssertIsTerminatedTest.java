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

public class CaseTaskAssertIsTerminatedTest extends ProcessAssertTestCase {

  public static final String TASK_A = "PI_TaskA";
  public static final String TASK_B = "PI_TaskB";
  public static final String HTASK_B = "PI_TaskB_HT";
  public static final String CASE_KEY = "Case_CaseTaskAssertIsTerminatedTest";
  public static final String CASE_KEY_B = "Case_CaseTaskAssertIsTerminatedTest_CaseB";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = { "cmmn/CaseTaskAssertIsTerminatedTest.cmmn" })
  public void testIsTerminated_Success() {
    // Given
    final CaseInstance caseInstance = givenCaseIsCreated();
    CaseInstance caseInstanceB = caseService().createCaseInstanceQuery().caseDefinitionKey(CASE_KEY_B).singleResult();
    caseExecution(TASK_B, caseInstance);
    // When
    complete(caseExecution(HTASK_B, caseInstanceB));
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(caseInstance).caseTask(TASK_B).isTerminated();
      }
    });
  }

  @Test
  @Deployment(resources = { "cmmn/CaseTaskAssertIsTerminatedTest.cmmn" })
  public void testIsTerminated_Failure() {
    // Given
    final CaseInstance caseInstance = givenCaseIsCreated();
    // When
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(caseInstance).caseTask(TASK_B).isTerminated();
      }
    });
  }

  private CaseInstance givenCaseIsCreated() {
    return caseService().createCaseInstanceByKey(CASE_KEY);
  }
}