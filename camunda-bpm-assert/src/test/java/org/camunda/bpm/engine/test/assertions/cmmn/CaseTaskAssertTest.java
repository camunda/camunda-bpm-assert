package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenter@holisticon.de>
 */
public class CaseTaskAssertTest {

  public static final String TASK_A = "PI_TaskA";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "cmmn/TaskTest.cmmn"
  })
  public void isActive_should_not_throw_exception_when_task_is_active() {
    //Given
    CaseInstance caseInstance = aCaseWithAnActiveTask();
    // When
    assertThat(caseInstance).task(TASK_A).isActive();
    // Then
    // nothing should happen
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {
    "cmmn/TaskTest.cmmn"
  })
  public void isActive_should_throw_AssertionError_when_task_is_not_active() {
    //Given
    CaseInstance caseInstance = aCaseWithACompletedTask();
    // When
    assertThat(caseInstance).task(TASK_A).isActive();
    // Then
    // AssertionError should be thrown
  }

  private CaseInstance aCaseWithACompletedTask() {
    CaseInstance caseInstance = aCaseWithAnActiveTask();
    caseService().completeCaseExecution(caseExecutionQuery().activityId(TASK_A).singleResult().getId());
    return caseInstance;
  }

  private CaseInstance aCaseWithAnActiveTask() {
    return caseService().createCaseInstanceByKey("Case_TaskTests");
  }
}
