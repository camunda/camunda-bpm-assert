package org.camunda.bpm.engine.test.assertions.cmmn_new;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.ProcessEngineTests;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.humanTask;

/**
 * Created by Malte on 18.09.2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProcessEngineAssertionsTest {

  public static final String ACTIVE_TASK = "PI_HT_A";
  public static final String AVAILABLE_TASK = "PI_HT_B";
  public static final String ENABLED_TASK = "PI_HT_C";
  public static final String CASE_KEY = "Case_TaskWithSentryTests";


  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  @Test
  public void assertThat_should_return_TaskAssert_for_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    // When trying to verify assertion on a task
    TaskHolder caseTask = humanTask(ACTIVE_TASK, caseInstance);
    TaskAssert taskAssert = ProcessEngineTests.assertThat(caseTask);

    // Then a TaskAssert object for the given task is returned
    assertThat(taskAssert).isNotNull();
    assertThat(taskAssert.getActual()).isNotNull().isSameAs(caseTask);
  }
}
