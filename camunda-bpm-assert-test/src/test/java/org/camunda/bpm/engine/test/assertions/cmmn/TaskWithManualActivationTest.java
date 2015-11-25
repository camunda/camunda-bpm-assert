package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class TaskWithManualActivationTest extends ProcessAssertTestCase {

  public static final String TASK_A = "PI_TaskA";
  public static final String CASE_KEY = "Case_TaskTests";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Before
  public void assumeApi() {
    assumeApi("7.3");
  }

  @Test
  @Deployment(resources = { "cmmn/TaskWithManualActivationTest.cmmn" })
  public void task_should_be_enabled() {
    // Given
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);
    // Then
    assertThat(caseExecution(TASK_A, caseInstance)).isEnabled();
  }

  @Test
  @Deployment(resources = { "cmmn/TaskWithManualActivationTest.cmmn" })
  public void task_should_be_active_when_manually_started() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreated();
    // When
    manuallyStart(caseExecution(TASK_A, caseInstance));
    // Then
    assertThat(caseExecution(TASK_A, caseInstance)).isActive();
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
    CaseExecution taskA;
    disable(taskA = caseExecution(TASK_A, caseInstance));
    // Then
    assertThat(caseInstance).isCompleted();
    assertThat(taskA).isDisabled();
  }

  private CaseInstance givenCaseIsCreated() {
    return caseService().createCaseInstanceByKey(CASE_KEY);
  }
}
