package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseExecution;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * This test is meant to help building the fluent API by providing simple test
 * cases.
 * 
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class TaskTest {

  public static final String TASK_A = "PI_TaskA";
  public static final String CASE_KEY = "Case_TaskTests";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = { "cmmn/TaskTest.cmmn" })
  /**
   * Introduces:
   * assertThat(CaseInstance)
   * caseInstance.isActive()
   * caseInstance.activity(id)
   * task.isActive()
   */
  public void case_and_task_should_be_active() {
    // Given
    // case model is deployed
    // When
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);
    // Then
    assertThat(caseInstance).isActive().task(TASK_A).isActive();
  }

  @Test
  @Deployment(resources = { "cmmn/TaskTest.cmmn" })
  /**
   * Introduces:
   * caseExecution(id, caseInstance)
   * complete(caseExecution)
   * caseInstance.isCompleted()
   * task.isCompleted()
   */
  public void case_should_complete_when_task_is_completed() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreated();
    // When
    complete(caseExecution(TASK_A, caseInstance));
    // Then
    assertThat(caseInstance).isCompleted().task(TASK_A).isCompleted();
  }

  private CaseInstance givenCaseIsCreated() {
    return caseService().createCaseInstanceByKey(CASE_KEY);
  }
}
