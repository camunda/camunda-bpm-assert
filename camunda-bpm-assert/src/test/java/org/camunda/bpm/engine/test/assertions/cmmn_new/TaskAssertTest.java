package org.camunda.bpm.engine.test.assertions.cmmn_new;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskAssertTest {

  public static final String ACTIVE_TASK = "PI_HT_A";
  public static final String AVAILABLE_TASK = "PI_HT_B";
  public static final String ENABLED_TASK = "PI_HT_C";
  public static final String CASE_KEY = "Case_TaskWithSentryTests";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isActive_should_fail_for_available_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    //when testing an available task's state to be active
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(AVAILABLE_TASK, caseInstance));
    taskAssert.isActive();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isActive_should_fail_for_completed_tasks() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_TASK).singleResult().getId());

    //when testing a completed task's state to be active
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(ACTIVE_TASK, caseInstance));
    taskAssert.isActive();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isActive_should_fail_for_enabled_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    //when testing an enabled task's state to be active
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(ENABLED_TASK, caseInstance));
    taskAssert.isActive();

    //an exception should be raised
  }

  @Test
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isActive_should_not_fail_for_active_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    //when testing an active task's state to be active
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(ACTIVE_TASK, caseInstance));
    taskAssert.isActive();

    //no exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isAvailable_should_fail_for_active_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    //when testing an active task's state to be available
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(ACTIVE_TASK, caseInstance));
    taskAssert.isAvailable();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isAvailable_should_fail_for_completed_tasks() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_TASK).singleResult().getId());

    //when testing a completed task's state to be available
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(ACTIVE_TASK, caseInstance));
    taskAssert.isAvailable();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isAvailable_should_fail_for_enabled_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    //when testing an enabled task's state to be available
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(ENABLED_TASK, caseInstance));
    taskAssert.isAvailable();

    //an exception should be raised
  }

  @Test
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isAvailable_should_not_fail_for_available_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    //when testing an available task's state to be available
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(AVAILABLE_TASK, caseInstance));
    taskAssert.isAvailable();

    //no exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isCompleted_should_fail_for_active_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    //when testing an active task's state to be completed
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(ACTIVE_TASK, caseInstance));
    taskAssert.isCompleted();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isCompleted_should_fail_for_available_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    //when testing an available task's state to be completed
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(AVAILABLE_TASK, caseInstance));
    taskAssert.isCompleted();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isCompleted_should_fail_for_enabled_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    //when testing an enabled task's state to be completed
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(ENABLED_TASK, caseInstance));
    taskAssert.isCompleted();

    //an exception should be raised
  }

  @Test
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isCompleted_should_not_fail_for_completed_tasks() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_TASK).singleResult().getId());

    //when testing a completed task's state to be completed
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(ACTIVE_TASK, caseInstance));
    taskAssert.isCompleted();

    //no exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isEnabled_should_fail_for_active_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    //when testing an active task's state to be enabled
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(ACTIVE_TASK, caseInstance));
    taskAssert.isEnabled();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isEnabled_should_fail_for_available_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    //when testing an available task's state to be enabled
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(AVAILABLE_TASK, caseInstance));
    taskAssert.isEnabled();

    //an exception should be raised
  }

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isEnabled_should_fail_for_completed_tasks() {
    // Given case model is deployed, case is started and a task has been completed
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);
    caseService().completeCaseExecution(caseExecutionQuery().activityId(ACTIVE_TASK).singleResult().getId());

    //when testing a completed task's state to be enabled
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(ACTIVE_TASK, caseInstance));
    taskAssert.isEnabled();

    //an exception should be raised
  }

  @Test
  @Deployment(resources = {"cmmn/TaskWithSentryTest.cmmn"})
  public void isEnabled_should_not_fail_for_enabled_tasks() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_KEY);

    //when testing an enabled task's state to be enabled
    TaskAssert taskAssert = new TaskAssert(processEngine(), humanTask(ENABLED_TASK, caseInstance));
    taskAssert.isEnabled();

    //no exception should be raised
  }
}
