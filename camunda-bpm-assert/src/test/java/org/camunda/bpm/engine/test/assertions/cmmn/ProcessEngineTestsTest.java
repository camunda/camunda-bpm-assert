package org.camunda.bpm.engine.test.assertions.cmmn;

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
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnModelConstants.*;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProcessEngineTestsTest {



  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  @Test
  public void complete_should_complete_active_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    // When trying to complete an active task
    complete(stage(ACTIVE_STAGE, caseInstance));

    // Then that task should be completed afterwards
    HistoricCaseActivityInstance historicCaseActivityInstance = historyService().createHistoricCaseActivityInstanceQuery().caseActivityId(ACTIVE_STAGE).completed().singleResult();
    assertThat(historicCaseActivityInstance).isNotNull();
  }

  @Deployment(resources = {"cmmn/HumanTaskTests.cmmn"})
  @Test
  public void complete_should_complete_active_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_HUMAN_TASK_TESTS);

    // When trying to complete an active task
    complete(humanTask(ACTIVE_TASK, caseInstance));

    // Then that task should be completed afterwards
    HistoricCaseActivityInstance historicCaseActivityInstance = historyService().createHistoricCaseActivityInstanceQuery().caseActivityId(ACTIVE_TASK).completed().singleResult();
    assertThat(historicCaseActivityInstance).isNotNull();
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void complete_should_fail_on_available_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    // When trying to complete an available human task
    complete(stage(AVAILABLE_STAGE, caseInstance));

    // Then an execption should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/HumanTaskTests.cmmn"})
  public void complete_should_fail_on_available_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_HUMAN_TASK_TESTS);

    // When trying to complete an available human task
    complete(humanTask(AVAILABLE_TASK, caseInstance));

    // Then an execption should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void complete_should_fail_on_completed_stages() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_STAGE).singleResult().getId());

    // When trying to complete the completed task
    complete(stage(ACTIVE_STAGE, caseInstance));

    // Then an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/HumanTaskTests.cmmn"})
  public void complete_should_fail_on_completed_tasks() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_HUMAN_TASK_TESTS);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_TASK).singleResult().getId());

    // When trying to complete the completed task
    complete(humanTask(ACTIVE_TASK, caseInstance));

    // Then an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void complete_should_fail_on_enabled_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    // When trying to complete an enabled human task
    complete(stage(ENABLED_STAGE, caseInstance));

    // Then an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/HumanTaskTests.cmmn"})
  public void complete_should_fail_on_enabled_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_HUMAN_TASK_TESTS);

    // When trying to complete an enabled human task
    complete(humanTask(ENABLED_TASK, caseInstance));

    // Then an exception should be raised
  }

  @Test
  @Deployment(resources = {"cmmn/HumanTaskTests.cmmn"})
  public void humanTask_should_find_active_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_HUMAN_TASK_TESTS);

    // When getting an active human task
    TaskHolder task = humanTask(ACTIVE_TASK, caseInstance);

    // Then the TaskAssert should not be null
    assertThat(task).isNotNull();
  }

  @Test
  @Deployment(resources = {"cmmn/HumanTaskTests.cmmn"})
  public void humanTask_should_find_available_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_HUMAN_TASK_TESTS);

    // When getting an available human task
    TaskHolder task = humanTask(AVAILABLE_TASK, caseInstance);

    // Then the TaskAssert should not be null
    assertThat(task).isNotNull();
  }

  @Test
  @Deployment(resources = {"cmmn/HumanTaskTests.cmmn"})
  public void humanTask_should_find_completed_tasks() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_HUMAN_TASK_TESTS);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_TASK).singleResult().getId());

    // When getting the completed human task
    TaskHolder task = humanTask(ACTIVE_TASK, caseInstance);

    // Then the TaskAssert should not be null
    assertThat(task).isNotNull();
  }

  @Test
  @Deployment(resources = {"cmmn/HumanTaskTests.cmmn"})
  public void humanTask_should_find_enabled_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_HUMAN_TASK_TESTS);

    // When getting an enabled human task
    TaskHolder task = humanTask(ENABLED_TASK, caseInstance);

    // Then the TaskAssert should not be null
    assertThat(task).isNotNull();
  }

  @Test
  @Deployment(resources = {"cmmn/MilestoneTests.cmmn"})
  public void milestone_should_find_created_milestones() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_MILESTONE_TESTS);

    // When getting an occurred milestone
    MilestoneHolder milestone = milestone(CREATED_MILESTONE, caseInstance);

    // Then the MilestoneAssert should not be null
    assertThat(milestone).isNotNull();
  }

  @Test
  @Deployment(resources = {"cmmn/MilestoneTests.cmmn"})
  public void milestone_should_find_occurred_milestones() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_MILESTONE_TESTS);

    // When getting an occurred milestone
    MilestoneHolder milestone = milestone(OCCURRED_MILESTONE, caseInstance);

    // Then the MilestoneAssert should not be null
    assertThat(milestone).isNotNull();
  }

  @Test
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void stage_should_find_active_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    // When getting an active human stage
    StageHolder stage = stage(ACTIVE_STAGE, caseInstance);

    // Then the StageAssert should not be null
    assertThat(stage).isNotNull();
  }

  @Test
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void stage_should_find_available_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    // When getting an available human stage
    StageHolder stage = stage(AVAILABLE_STAGE, caseInstance);

    // Then the StageAssert should not be null
    assertThat(stage).isNotNull();
  }

  @Test
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void stage_should_find_completed_stages() {
    // Given case model is deployed, case is started and a stage has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_STAGE).singleResult().getId());

    // When getting the completed human stage
    StageHolder stage = stage(ACTIVE_STAGE, caseInstance);

    // Then the StageAssert should not be null
    assertThat(stage).isNotNull();
  }

  @Test
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void stage_should_find_enabled_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    // When getting an enabled human stage
    StageHolder stage = stage(ENABLED_STAGE, caseInstance);

    // Then the StageAssert should not be null
    assertThat(stage).isNotNull();
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void start_should_fail_on_active_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    // When trying to complete an active human stage
    start(stage(ACTIVE_STAGE, caseInstance));

    // Then an execption should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/HumanTaskTests.cmmn"})
  public void start_should_fail_on_active_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_HUMAN_TASK_TESTS);

    // When trying to complete an active human task
    start(humanTask(ACTIVE_TASK, caseInstance));

    // Then an execption should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void start_should_fail_on_available_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    // When trying to complete an available human stage
    start(stage(AVAILABLE_STAGE, caseInstance));

    // Then an execption should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/HumanTaskTests.cmmn"})
  public void start_should_fail_on_available_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_HUMAN_TASK_TESTS);

    // When trying to complete an available human task
    start(humanTask(AVAILABLE_TASK, caseInstance));

    // Then an execption should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void start_should_fail_on_completed_stages() {
    // Given case model is deployed, case is started and a stage has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_STAGE).singleResult().getId());

    // When trying to start the completed stage
    start(stage(ACTIVE_STAGE, caseInstance));

    // Then an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/HumanTaskTests.cmmn"})
  public void start_should_fail_on_completed_tasks() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_HUMAN_TASK_TESTS);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_TASK).singleResult().getId());

    // When trying to start the completed task
    start(humanTask(ACTIVE_TASK, caseInstance));

    // Then an exception should be raised
  }

  @Test
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void start_should_start_enabled_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    // When trying to start an enabled stage
    start(stage(ENABLED_STAGE, caseInstance));

    // Then that stage should be active afterwards
    CaseExecution caseExecution = caseService().createCaseExecutionQuery().activityId(ENABLED_STAGE).active().singleResult();
    assertThat(caseExecution).isNotNull();
  }

  @Test
  @Deployment(resources = {"cmmn/HumanTaskTests.cmmn"})
  public void start_should_start_enabled_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_HUMAN_TASK_TESTS);

    // When trying to start an enabled task
    start(humanTask(ENABLED_TASK, caseInstance));

    // Then that task should be active afterwards
    CaseExecution caseExecution = caseService().createCaseExecutionQuery().activityId(ENABLED_TASK).active().singleResult();
    assertThat(caseExecution).isNotNull();
  }

}
