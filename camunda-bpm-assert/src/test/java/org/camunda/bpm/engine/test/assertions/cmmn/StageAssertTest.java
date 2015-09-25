package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnModelConstants.*;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StageAssertTest {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isActive_should_fail_for_available_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    //when testing an available task's state to be active
    StageAssert stageAssert = new StageAssert(processEngine(), stage(AVAILABLE_STAGE, caseInstance));
    stageAssert.isActive();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isActive_should_fail_for_completed_stages() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_STAGE).singleResult().getId());

    //when testing a completed task's state to be active
    StageAssert stageAssert = new StageAssert(processEngine(), stage(ACTIVE_STAGE, caseInstance));
    stageAssert.isActive();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isActive_should_fail_for_enabled_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    //when testing an enabled task's state to be active
    StageAssert stageAssert = new StageAssert(processEngine(), stage(ENABLED_STAGE, caseInstance));
    stageAssert.isActive();

    //an exception should be raised
  }

  @Test
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isActive_should_not_fail_for_active_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    //when testing an active task's state to be active
    StageAssert stageAssert = new StageAssert(processEngine(), stage(ACTIVE_STAGE, caseInstance));
    stageAssert.isActive();

    //no exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isAvailable_should_fail_for_active_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    //when testing an active task's state to be available
    StageAssert stageAssert = new StageAssert(processEngine(), stage(ACTIVE_STAGE, caseInstance));
    stageAssert.isAvailable();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isAvailable_should_fail_for_completed_stages() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_STAGE).singleResult().getId());

    //when testing a completed task's state to be available
    StageAssert stageAssert = new StageAssert(processEngine(), stage(ACTIVE_STAGE, caseInstance));
    stageAssert.isAvailable();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isAvailable_should_fail_for_enabled_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    //when testing an enabled task's state to be available
    StageAssert stageAssert = new StageAssert(processEngine(), stage(ENABLED_STAGE, caseInstance));
    stageAssert.isAvailable();

    //an exception should be raised
  }

  @Test
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isAvailable_should_not_fail_for_available_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    //when testing an available task's state to be available
    StageAssert stageAssert = new StageAssert(processEngine(), stage(AVAILABLE_STAGE, caseInstance));
    stageAssert.isAvailable();

    //no exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isCompleted_should_fail_for_active_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    //when testing an active task's state to be completed
    StageAssert stageAssert = new StageAssert(processEngine(), stage(ACTIVE_STAGE, caseInstance));
    stageAssert.isCompleted();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isCompleted_should_fail_for_available_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    //when testing an available task's state to be completed
    StageAssert stageAssert = new StageAssert(processEngine(), stage(AVAILABLE_STAGE, caseInstance));
    stageAssert.isCompleted();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isCompleted_should_fail_for_enabled_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    //when testing an enabled task's state to be completed
    StageAssert stageAssert = new StageAssert(processEngine(), stage(ENABLED_STAGE, caseInstance));
    stageAssert.isCompleted();

    //an exception should be raised
  }

  @Test
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isCompleted_should_not_fail_for_completed_stages() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_STAGE).singleResult().getId());

    //when testing a completed task's state to be completed
    StageAssert stageAssert = new StageAssert(processEngine(), stage(ACTIVE_STAGE, caseInstance));
    stageAssert.isCompleted();

    //no exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isEnabled_should_fail_for_active_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    //when testing an active task's state to be enabled
    StageAssert stageAssert = new StageAssert(processEngine(), stage(ACTIVE_STAGE, caseInstance));
    stageAssert.isEnabled();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isEnabled_should_fail_for_available_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    //when testing an available task's state to be enabled
    StageAssert stageAssert = new StageAssert(processEngine(), stage(AVAILABLE_STAGE, caseInstance));
    stageAssert.isEnabled();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isEnabled_should_fail_for_completed_stages() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_STAGE).singleResult().getId());

    //when testing a completed task's state to be enabled
    StageAssert stageAssert = new StageAssert(processEngine(), stage(ACTIVE_STAGE, caseInstance));
    stageAssert.isEnabled();

    //an exception should be raised
  }

  @Test
  @Deployment(resources = {"cmmn/StageTests.cmmn"})
  public void isEnabled_should_not_fail_for_enabled_stages() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_STAGE_TESTS);

    //when testing an enabled task's state to be enabled
    StageAssert stageAssert = new StageAssert(processEngine(), stage(ENABLED_STAGE, caseInstance));
    stageAssert.isEnabled();

    //no exception should be raised
  }
}
