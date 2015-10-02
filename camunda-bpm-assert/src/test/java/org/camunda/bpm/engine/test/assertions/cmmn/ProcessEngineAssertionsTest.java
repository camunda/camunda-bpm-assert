package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.ProcessEngineTests;
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
public class ProcessEngineAssertionsTest {



  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Deployment(resources = {"cmmn/MilestoneTests.cmmn"})
  @Test
  public void assertThat_should_return_MilestoneAssert_for_milestones() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_MILESTONE_TESTS);

    // When trying to verify assertion on a task
    MilestoneHolder milestone = milestone(OCCURRED_MILESTONE, caseInstance);
    MilestoneAssert milestoneAssert = ProcessEngineTests.assertThat(milestone);

    // Then a TaskAssert object for the given task is returned
    assertThat(milestoneAssert).isNotNull();
    assertThat(milestoneAssert.getActual()).isNotNull().isSameAs(milestone);
  }

  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  @Test
  public void assertThat_should_return_StageAssert_for_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    // When trying to verify assertion on a stage
    StageHolder stage = stage(ENABLED_STAGE, caseInstance);
    StageAssert taskAssert = ProcessEngineTests.assertThat(stage);

    // Then a TaskAssert object for the given task is returned
    assertThat(taskAssert).isNotNull();
    assertThat(taskAssert.getActual()).isNotNull().isSameAs(stage);
  }

  @Deployment(resources = {"cmmn/HumanTaskTests.cmmn"})
  @Test
  public void assertThat_should_return_TaskAssert_for_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_HUMAN_TASK_TESTS);

    // When trying to verify assertion on a task
    TaskHolder caseTask = humanTask(ACTIVE_HUMAN_TASK, caseInstance);
    TaskAssert taskAssert = ProcessEngineTests.assertThat(caseTask);

    // Then a TaskAssert object for the given task is returned
    assertThat(taskAssert).isNotNull();
    assertThat(taskAssert.getActual()).isNotNull().isSameAs(caseTask);
  }
}
