package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseExecution;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;
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
public class StageWithSentryTest {

  public static final String TASK_A = "PI_HT_A";
  public static final String STAGE_S = "PI_StageS";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  /**
   * Introduces: stage.isAvailable()
   */
  @Test
  @Deployment(resources = { "cmmn/StageWithSentryTest.cmmn" })
  public void stage_t_should_be_available() {
    // Given
    // When
    CaseInstance caseInstance = givenCaseIsCreated();
    // Then
    assertThat(caseInstance).isActive().task(TASK_A).isEnabled();
    assertThat(caseInstance).isActive().stage(STAGE_S).isAvailable();
  }

  /**
   * Introduces:
   */
  @Test
  @Deployment(resources = { "cmmn/StageWithSentryTest.cmmn" })
  public void stage_t_should_be_enabled() {
    // Given
    CaseInstance caseInstance = givenCaseIsCreated();
    // When
    manuallyStart(caseExecution(TASK_A, caseInstance));
    complete(caseExecution(TASK_A, caseInstance));
    // Then
    assertThat(caseInstance).isActive().task(TASK_A).isCompleted();
    assertThat(caseInstance).isActive().stage(STAGE_S).isEnabled();
  }

  private CaseInstance givenCaseIsCreated() {
    CaseInstance caseInstance = caseService().createCaseInstanceByKey("Case_StageWithSentryTests");
    return caseInstance;
  }

}
