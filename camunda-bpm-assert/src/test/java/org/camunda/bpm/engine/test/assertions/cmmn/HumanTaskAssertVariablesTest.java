package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.caseService;

import org.camunda.bpm.engine.runtime.CaseExecutionCommandBuilder;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

public class HumanTaskAssertVariablesTest extends ProcessAssertTestCase {

  public static final String TASK_A = "PI_TaskA";
  public static final String CASE_KEY = "Case_HumanTaskAssert-variables";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = { "cmmn/HumanTaskAssert-variables.cmmn" })
  public void testVariables_Success() {
    // Given
    final CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);
    setAVariableOnHumanTask();

    // When
    // Then
    assertThat(caseInstance).humanTask(TASK_A).variables().containsEntry("aVariable", "aValue");
  }

  @Test
  @Deployment(resources = { "cmmn/HumanTaskAssert-variables.cmmn" })
  public void testVariables_Success_No_Variables() {
    // Given
    final CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    // When
    // Then
    assertThat(caseInstance).humanTask(TASK_A).variables().isEmpty();
  }

  @Test
  @Deployment(resources = { "cmmn/HumanTaskAssert-variables.cmmn" })
  public void testVariables_Failure_HumanTask_Completed() {
    // Given
    final CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);
    setAVariableOnHumanTaskAndComplete();

    // When
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(caseInstance).humanTask(TASK_A).variables().containsEntry("aVariable", "aValue");
      }
    });
  }

  private void setAVariableOnHumanTaskAndComplete() {
    getCommandExectuionToSetAVariableOnAHumanTask().complete();
  }

  private void setAVariableOnHumanTask() {
    getCommandExectuionToSetAVariableOnAHumanTask().execute();
  }

  private CaseExecutionCommandBuilder getCommandExectuionToSetAVariableOnAHumanTask() {
    return caseService().withCaseExecution(caseService().createCaseExecutionQuery().activityId(TASK_A).singleResult().getId()).setVariable(
        "aVariable",
        "aValue");
  }
}