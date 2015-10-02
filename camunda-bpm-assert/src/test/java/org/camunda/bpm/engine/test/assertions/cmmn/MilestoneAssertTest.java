package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.caseService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.milestone;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnModelConstants.*;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MilestoneAssertTest {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test(expected = AssertionError.class)
  @Deployment(resources = {"cmmn/MilestoneTests.cmmn"})
  public void hasOccurred_should_fail_for_created_milestones() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_MILESTONE_TESTS);

    //when testing an available task's state to be active
    MilestoneAssert milestoneAssert = new MilestoneAssert(processEngine(), milestone(CREATED_MILESTONE, caseInstance));
    milestoneAssert.hasOccurred();

    //an exception should be raised
  }

  @Test
  @Deployment(resources = {"cmmn/MilestoneTests.cmmn"})
  public void hasOccurred_should_not_fail_for_occured_milestones() {
    // Given case model is deployed and case is started
    CaseInstance caseInstance = caseService().createCaseInstanceByKey(CASE_MILESTONE_TESTS);

    //when testing an available task's state to be active
    MilestoneAssert milestoneAssert = new MilestoneAssert(processEngine(), milestone(OCCURRED_MILESTONE, caseInstance));
    milestoneAssert.hasOccurred();

    //no exception should be raised
  }
}
