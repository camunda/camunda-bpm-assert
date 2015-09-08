package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseExecution;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.manuallyStart;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class StageWithSentryExitCriteriaTest {

  public static final String TASK_A = "PI_HT_A";
  public static final String TASK_B = "PI_HT_B";
  public static final String STAGE_S = "PI_StageS";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = { "cmmn/StageWithSentryTestExitCriteria.cmmn" })
  public void case_is_active_and_task_a_and_task_b_should_be_enabled() {
    // Given
    // case model is deployed
    // When
    CaseInstance caseInstance = givenCaseIsCreated();
    // Then
    assertThat(caseInstance).isActive().stage(STAGE_S).isEnabled();
    // TODO task(TASK_A).isEnabled();
    assertThat(caseInstance).isActive().task(TASK_B).isEnabled();
  }

  @Test
  @Deployment(resources = { "cmmn/StageWithSentryTestExitCriteria.cmmn" })
  public void case_is_active_and_stage_s_should_be_active_and_task_a_and_task_b_enabled() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreated();
    // When
    manuallyStart(caseExecution(STAGE_S, caseInstance));
    // Then
    assertThat(caseInstance).isActive().stage(STAGE_S).isActive().task(TASK_A).isEnabled();
    assertThat(caseInstance).isActive().task(TASK_B).isEnabled();
  }

  @Test
  @Deployment(resources = { "cmmn/StageWithSentryTestExitCriteria.cmmn" })
  public void case_is_active_and_stage_s_and_task_a_should_be_active_and_task_b_enabled() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreatedAndStageSActive();
    // When
    manuallyStart(caseExecution(TASK_A, caseInstance));
    // Then
    assertThat(caseInstance).isActive().stage(STAGE_S).isActive().task(TASK_A).isActive();
    assertThat(caseInstance).isActive().task(TASK_B).isEnabled();
  }

  @Test
  @Deployment(resources = { "cmmn/StageWithSentryTestExitCriteria.cmmn" })
  public void case_is_active_and_stage_s_and_task_a_should_be_terminated_and_task_b_active() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreatedAndStageSActiveAndTaskAActive();
    // When
    manuallyStart(caseExecution(TASK_B, caseInstance));
    // Then
    assertThat(caseInstance).isActive().stage(STAGE_S).isTerminated().task(TASK_A).isTerminated();
    assertThat(caseInstance).isActive().task(TASK_B).isActive();
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
