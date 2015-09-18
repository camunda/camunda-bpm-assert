package org.camunda.bpm.engine.test.assertions.cmmn_new;

import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * Created by Malte on 18.09.2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProcessEngineTestsTest {

  public static final String ACTIVE_TASK = "PI_HT_A";
  public static final String AVAILABLE_TASK = "PI_HT_B";
  public static final String ENABLED_TASK = "PI_HT_C";
  public static final String CASE_KEY = "Case_TaskWithSentryTests";


  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  @Test
  public void complete_should_complete_active_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    // When trying to complete an active task
    complete(humanTask(ACTIVE_TASK, caseInstance));

    // Then that task should be completed afterwards
    HistoricCaseActivityInstance historicCaseActivityInstance = historyService().createHistoricCaseActivityInstanceQuery().caseActivityId(ACTIVE_TASK).completed().singleResult();
    assertThat(historicCaseActivityInstance).isNotNull();
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void complete_should_fail_on_available_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    // When trying to complete an available human task
    complete(humanTask(AVAILABLE_TASK, caseInstance));

    // Then an execption should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void complete_should_fail_on_completed_tasks() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_TASK).singleResult().getId());

    // When trying to complete the completed task
    complete(humanTask(ACTIVE_TASK, caseInstance));

    // Then an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void complete_should_fail_on_enabled_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    // When trying to complete an enabled human task
    complete(humanTask(ENABLED_TASK, caseInstance));

    // Then an exception should be raised
  }

  @Test
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void humanTask_should_find_active_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    // When getting an active human task
    TaskHolder caseTask = humanTask(ACTIVE_TASK, caseInstance);

    // Then the TaskAssert should not be null
    assertThat(caseTask).isNotNull();
  }

  @Test
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void humanTask_should_find_available_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    // When getting an available human task
    TaskHolder caseTask = humanTask(AVAILABLE_TASK, caseInstance);

    // Then the TaskAssert should not be null
    assertThat(caseTask).isNotNull();
  }

  @Test
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void humanTask_should_find_completed_tasks() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_TASK).singleResult().getId());

    // When getting the completed human task
    TaskHolder caseTask = humanTask(ACTIVE_TASK, caseInstance);

    // Then the TaskAssert should not be null
    assertThat(caseTask).isNotNull();
  }

  @Test
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void humanTask_should_find_enabled_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    // When getting an enabled human task
    TaskHolder caseTask = humanTask(ENABLED_TASK, caseInstance);

    // Then the TaskAssert should not be null
    assertThat(caseTask).isNotNull();
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void start_should_fail_on_active_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    // When trying to complete an active human task
    start(humanTask(ACTIVE_TASK, caseInstance));

    // Then an execption should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void start_should_fail_on_available_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    // When trying to complete an available human task
    start(humanTask(AVAILABLE_TASK, caseInstance));

    // Then an execption should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void start_should_fail_on_completed_tasks() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_TASK).singleResult().getId());

    // When trying to start the completed task
    start(humanTask(ACTIVE_TASK, caseInstance));

    // Then an exception should be raised
  }

  @Test
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void start_should_start_enabled_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    // When trying to start an enabled task
    start(humanTask(ENABLED_TASK, caseInstance));

    // Then that task should be active afterwards
    CaseExecution caseExecution = caseService().createCaseExecutionQuery().activityId(ENABLED_TASK).active().singleResult();
    assertThat(caseExecution).isNotNull();
  }

}
