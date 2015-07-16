package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenter@holisticon.de>
 */
public class CaseInstanceAssertTest {

  public static final String TASK_A = "PI_TaskA";
  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "cmmn/TaskTest.cmmn"
  })
  public void isActive_should_properly_detect_status_of_case_instances() {
    // When
    CaseInstance caseInstance = caseService().createCaseInstanceByKey("Case_TaskTests");
    // Then
    assertThat(caseInstance).isActive();
  }

  @Test
  @Deployment(resources = {
    "cmmn/TaskTest.cmmn"
  })
  public void task_should_return_CaseTaskAssert_for_given_activityId() {
    // Given
    CaseInstance caseInstance = givenStartedCase();
    CaseExecution pi_taskA = caseTask(TASK_A, caseInstance);
    // When
    CaseTaskAssert caseTaskAssert = assertThat(caseInstance).task(TASK_A);
    // Then
    CaseExecution actual = caseTaskAssert.getActual();
    Assertions.assertThat(actual).overridingErrorMessage("Expected case execution " + toString(actual) + " to be equal to " + toString(pi_taskA)).isEqualToComparingOnlyGivenFields(pi_taskA, "id");
  }

  private CaseInstance givenStartedCase() {
    return caseService().createCaseInstanceByKey("Case_TaskTests");
  }

  protected String toString(CaseExecution caseExecution) {
    return caseExecution != null ?
      String.format("%s {" +
          "id='%s', " +
          "caseDefinitionId='%s', " +
          "activityType='%s'" +
          "}",
        caseExecution.getClass().getSimpleName(),
        caseExecution.getId(),
        caseExecution.getCaseDefinitionId(),
        caseExecution.getActivityType())
      : null;
  }

}
