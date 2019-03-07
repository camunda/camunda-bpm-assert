package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.caseService;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

public class CaseInstanceAssertIsClosedTest extends ProcessAssertTestCase {

  public static final String TASK_B = "PI_TaskB";
  public static final String CASE_KEY = "Case_CaseTaskAssertIsTerminatedTest";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();
  
  @Test
  @Deployment(resources = { "cmmn/CaseTaskAssertIsTerminatedTest.cmmn" })
  public void testIsTerminated_Success() {
    // Given
    final CaseInstance caseInstance = givenCaseIsCreated();
    // When
    caseService().terminateCaseExecution(caseInstance.getId());
    assertThat(caseInstance).isTerminated();
    caseService().closeCaseInstance(caseInstance.getId());
    // Then
    assertThat(caseInstance).isClosed();
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
        assertThat(caseInstance).isClosed();
      }
    });
  }

  private CaseInstance givenCaseIsCreated() {
    return caseService().createCaseInstanceByKey(CASE_KEY);
  }
}