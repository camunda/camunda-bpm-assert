package org.camunda.bpm.engine.test.assertions.cmmn;


import org.camunda.bpm.engine.runtime.CaseExecution;
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
public class StageWithSentryExitCriteriaTest extends ProcessAssertTestCase {

  public static final String TASK_A = "PI_HT_A";
  public static final String TASK_B = "PI_HT_B";
  public static final String STAGE_S = "PI_StageS";

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
  @Deployment(resources = { "cmmn/StageWithSentryTestExitCriteria.cmmn" })
  public void case_is_active_and_task_a_and_task_b_should_be_enabled() {
    // Given
    // case model is deployed
    // When
    CaseInstance caseInstance = givenCaseIsCreated();
    // Then
    assertThat(caseInstance).isActive().stage(STAGE_S).isEnabled();
    assertThat(caseInstance).isActive().humanTask(TASK_B).isEnabled();
  }

  /**
   * Introduces:
   */
  @Test
  @Deployment(resources = { "cmmn/StageWithSentryTestExitCriteria.cmmn" })
  public void case_is_active_and_stage_s_should_be_active_and_task_a_and_task_b_enabled() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreated();
    // When
    manuallyStart(caseExecution(STAGE_S, caseInstance));
    // Then
    assertThat(caseInstance).isActive().stage(STAGE_S).isActive().humanTask(TASK_A).isEnabled();
    assertThat(caseInstance).isActive().humanTask(TASK_B).isEnabled();
  }

  /**
   * Introduces:
   */
  @Test
  @Deployment(resources = { "cmmn/StageWithSentryTestExitCriteria.cmmn" })
  public void case_is_active_and_stage_s_and_task_a_should_be_active_and_task_b_enabled() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreatedAndStageSActive();
    // When
    manuallyStart(caseExecution(TASK_A, caseInstance));
    // Then
    assertThat(caseInstance).isActive().stage(STAGE_S).isActive().humanTask(TASK_A).isActive();
    assertThat(caseInstance).isActive().humanTask(TASK_B).isEnabled();
  }

  /**
   * Introduces: stage.isTerminated()
   */
  @Test
  @Deployment(resources = { "cmmn/StageWithSentryTestExitCriteria.cmmn" })
  public void case_is_active_and_stage_s_and_task_a_should_be_terminated_and_task_b_active() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreatedAndStageSActiveAndTaskAActive();
    CaseExecution taskA = caseExecution(TASK_A, caseInstance);
    CaseExecution stageS = caseExecution(STAGE_S, caseInstance);
    // When
    manuallyStart(caseExecution(TASK_B, caseInstance));
    // Then
    assertThat(caseInstance).isActive();
    assertThat(taskA).isTerminated();
    assertThat(stageS).isTerminated();
    assertThat(caseInstance).isActive()
      .humanTask(TASK_B).isActive();
  }

  private CaseInstance givenCaseIsCreated() {
    CaseInstance caseInstance = caseService().createCaseInstanceByKey("Case_StageWithSentryExitCriteriaTest");
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
