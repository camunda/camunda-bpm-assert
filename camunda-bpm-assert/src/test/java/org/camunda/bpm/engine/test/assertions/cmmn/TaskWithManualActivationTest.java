package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseActivity;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseExecution;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.disable;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.manuallyStart;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * This test is meant to help building the fluent API by providing simple test cases.
 * 
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class TaskWithManualActivationTest {

  public static final String TASK_A = "PI_TaskA";
  public static final String CASE_KEY = "Case_TaskTests";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = { "cmmn/TaskWithManualActivationTest.cmmn" })
  /**
   * Introduces:
   * activity.isEnabled()
   */
  public void task_should_be_enabled() {
    // Given
    // case model is deployed
    // When creating a new case instance
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);
    // Then task A should be enabled
    assertThat(caseActivity(TASK_A, caseInstance)).isEnabled();
  }

  @Test
  @Deployment(resources = { "cmmn/TaskWithManualActivationTest.cmmn" })
  /**
   * Introduces:
   * manuallyStart(caseExecution)
   */
  public void task_should_be_active_when_manually_started() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreated();
    // When
    manuallyStart(caseExecution(TASK_A, caseInstance));
    // Then
    assertThat(caseActivity(TASK_A, caseInstance)).isActive();
  }

  /**
   * Introduces:
   * disable(caseExecution)
   * task.isDisabled()
   */
  @Test
  @Deployment(resources = { "cmmn/TaskWithManualActivationTest.cmmn" })
  public void task_should_be_disabed() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreated();
    // When
    disable(caseExecution(TASK_A, caseInstance));
    // Then
    assertThat(caseInstance).isCompleted().task(TASK_A).isDisabled();
  }

  private CaseInstance givenCaseIsCreated() {
    return caseService().createCaseInstanceByKey(CASE_KEY);
  }
}
