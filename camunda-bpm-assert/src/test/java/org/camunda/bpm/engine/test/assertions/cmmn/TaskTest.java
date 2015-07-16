package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseTask;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenter@holisticon.de>
 */
public class TaskTest {

  public static final String TASK_A = "PI_TaskA";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "cmmn/TaskTest.cmmn"
  })
  public void case_and_task_should_be_active() {
    // Given
    // case model is deployed
    // When
    CaseInstance caseInstance = caseService().createCaseInstanceByKey("Case_TaskTests");
    // Then
    assertThat(caseInstance).isActive().task(TASK_A).isActive();
  }

  @Test
  @Deployment(resources = {
    "cmmn/TaskTest.cmmn"
  })
  public void case_should_complete_when_task_is_completed() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreated();
    // When
    complete(caseTask(TASK_A, caseInstance));
    // Then
    assertThat(caseInstance).isCompleted().task(TASK_A).isCompleted();
  }

  private CaseInstance givenCaseIsCreated() {
    return caseService().createCaseInstanceByKey("Case_TaskTests");
  }
}
