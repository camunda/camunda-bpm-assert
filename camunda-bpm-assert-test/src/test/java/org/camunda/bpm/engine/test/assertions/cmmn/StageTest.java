package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseExecution;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.disable;
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
public class StageTest extends ProcessAssertTestCase {

  public static final String TASK_A = "PI_TaskA";
  public static final String STAGE_S = "PI_StageS";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Before
  public void assumeApi() {
    assumeApi("7.3");
  }

  /**
   * Introduces: assertThat(CaseInstance) caseInstance.isActive() caseInstance.stage(id) stage.isEnabled()
   */
  @Test
  @Deployment(resources = { "cmmn/StageTest.cmmn" })
  public void case_is_active_and_stage_and_task_should_be_enabled() {
    // Given
    // case model is deployed
    // When
    CaseInstance caseInstance = givenCaseIsCreated();
    // Then
    assertThat(caseInstance).isActive().stage(STAGE_S).isEnabled();
  }

  /**
   * Introduces: manuallyStart(CaseExecution) stage.isActive()
   */
  @Test
  @Deployment(resources = { "cmmn/StageTest.cmmn" })
  public void stage_should_be_active_and_taske_enabled() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreated();
    // When
    manuallyStart(caseExecution(STAGE_S, caseInstance));
    // Then
    assertThat(caseInstance).isActive().stage(STAGE_S).isActive().humanTask(TASK_A).isEnabled();
  }

  /**
   * Introduces:
   */
  @Test
  @Deployment(resources = { "cmmn/StageTest.cmmn" })
  public void stage_and_task_should_be_active() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreatedAndStageSActive();
    // When
    manuallyStart(caseExecution(TASK_A, caseInstance));
    // Then
    assertThat(caseInstance).isActive().stage(STAGE_S).isActive().humanTask(TASK_A).isActive();
  }

  /**
   * Introduces: complete(CaseExecution) stage.isCompleted()
   */
  @Test
  @Deployment(resources = { "cmmn/StageTest.cmmn" })
  public void case_should_complete_when_task_is_completed() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreatedAndStageSActiveAndTaskAActive();
    // When
    complete(caseExecution(TASK_A, caseInstance));
    // Then
    assertThat(caseInstance).isCompleted();
  }

  /**
   * Introduces: disable(CaseExecution) stage.isDisabled()
   */
  @Test
  @Deployment(resources = { "cmmn/StageTest.cmmn" })
  public void stage_and_task_should_be_disabled() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreated();
    // When
    CaseExecution stage = caseExecution(STAGE_S, caseInstance);
    disable(stage);
    // Then
    assertThat(caseInstance).isCompleted();
    assertThat(stage).isDisabled();
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
