package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * This test is meant to help building the fluent API by providing simple test cases.

 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenter@holisticon.de>
 */
public class TaskWithSentryTest {

  public static final String TASK_A = "PI_HT_A";
  public static final String TASK_B = "PI_HT_B";
  public static final String CASE_KEY = "Case_TaskWithSentryTests";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "cmmn/TaskWithSentryTest.cmmn"
  })
  /**
   * Introduces:
   * caseActivity(id, caseInstance)
   * assertThat(caseActivity)
   * activity.isAvailable()
   */
  public void task_b_should_be_available() {
    // Given
    // case model is deployed
    // When
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);
    // Then
    assertThat(caseActivity(TASK_A, caseInstance)).isActive();
    assertThat(caseActivity(TASK_B, caseInstance)).isAvailable();
  }
}
