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
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProcessEngineAssertionsTest {

  public static final String TASK_ID = "PI_HT_A";
  public static final String CASE_DEF_WITH_TASKS = "Case_TaskWithSentryTests";
  public static final String STAGE_ID = "PI_Stage_A";
  public static final String CASE_DEF_WITH_STAGES = "Case_StageWithSentryTests";
  public static final String MILESTONE_ID = "PI_MS_Occurred";
  public static final String CASE_DEF_MILESTONE = "Case_MilestoneTests";


  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Deployment(resources = {"cmmn/MilestoneTests.cmmn"})
  @Test
  public void assertThat_should_return_MilestoneAssert_for_milestones() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_DEF_MILESTONE);

    // When trying to verify assertion on a task
    MilestoneHolder milestone = milestone(MILESTONE_ID, caseInstance);
    MilestoneAssert milestoneAssert = ProcessEngineTests.assertThat(milestone);

    // Then a TaskAssert object for the given task is returned
    assertThat(milestoneAssert).isNotNull();
    assertThat(milestoneAssert.getActual()).isNotNull().isSameAs(milestone);
  }

  @Deployment(resources = {"cmmn/StageWithSentryTest.cmmn"})
  @Test
  public void assertThat_should_return_StageAssert_for_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_DEF_WITH_STAGES);

    // When trying to verify assertion on a stage
    StageHolder stage = stage(STAGE_ID, caseInstance);
    StageAssert taskAssert = ProcessEngineTests.assertThat(stage);

    // Then a TaskAssert object for the given task is returned
    assertThat(taskAssert).isNotNull();
    assertThat(taskAssert.getActual()).isNotNull().isSameAs(stage);
  }

  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  @Test
  public void assertThat_should_return_TaskAssert_for_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_DEF_WITH_TASKS);

    // When trying to verify assertion on a task
    TaskHolder caseTask = humanTask(TASK_ID, caseInstance);
    TaskAssert taskAssert = ProcessEngineTests.assertThat(caseTask);

    // Then a TaskAssert object for the given task is returned
    assertThat(taskAssert).isNotNull();
    assertThat(taskAssert.getActual()).isNotNull().isSameAs(caseTask);
  }
}
