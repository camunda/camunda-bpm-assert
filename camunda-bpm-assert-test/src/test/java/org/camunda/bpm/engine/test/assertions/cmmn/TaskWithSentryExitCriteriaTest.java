package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseExecution;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.manuallyStart;

import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class TaskWithSentryExitCriteriaTest extends ProcessAssertTestCase {

  public static final String TASK_A = "PI_HT_A";
  public static final String TASK_B = "PI_HT_B";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Before
  public void assumeApi() {
    assumeApi("7.3");
  }

  /**
   * Introduces: 
   */
  @Test
  @Deployment(resources = { "cmmn/TaskWithSentryTestExitCriteria.cmmn" })
  public void case_is_active_and_task_a_and_task_b_should_be_enabled() {
    // Given
    // case model is deployed
    // When
    CaseInstance caseInstance = givenCaseIsCreated();
    // Then
    assertThat(caseInstance).isActive().humanTask(TASK_A).isEnabled();
    assertThat(caseInstance).isActive().humanTask(TASK_B).isEnabled();
  }

  /**
   * Introduces: 
   * task.isTerminated()
   */
  @Test
  @Deployment(resources = { "cmmn/TaskWithSentryTestExitCriteria.cmmn" })
  public void case_is_active_and_task_a_should_be_terminated_and_task_b_active() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreatedAndTaskAActive();
    // When
    CaseExecution taskA = caseExecution(TASK_A, caseInstance);
    manuallyStart(caseExecution(TASK_B, caseInstance));
    // Then
    assertThat(taskA).isTerminated();
    assertThat(caseInstance).isActive().humanTask(TASK_B).isActive();
  }

  private CaseInstance givenCaseIsCreated() {
    CaseInstance caseInstance = caseService().createCaseInstanceByKey("Case_TaskWithSentryExitCriteriaTest");
    return caseInstance;
  }

  private CaseInstance givenCaseIsCreatedAndTaskAActive() {
    CaseInstance caseInstance = givenCaseIsCreated();
    manuallyStart(caseExecution(TASK_A, caseInstance));
    return caseInstance;
  }

}
