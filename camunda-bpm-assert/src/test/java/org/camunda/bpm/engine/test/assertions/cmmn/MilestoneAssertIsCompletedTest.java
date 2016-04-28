package org.camunda.bpm.engine.test.assertions.cmmn;

import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.caseExecution;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.caseService;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.complete;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Malte Soerensen <malte.soerensen@holisticon.de>
 */
@Deployment(resources = "cmmn/MilestoneAssertIsCompletedTest.cmmn")
public class MilestoneAssertIsCompletedTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  public void test_IsCompleted_Success() {
    CaseInstance caseInstance = caseService().createCaseInstanceByKey("MilestoneAssertIsCompletedTest");
    MilestoneAssert milestoneAssert = assertThat(caseInstance).milestone("Milestone");

    complete(caseExecution("PI_TaskA", caseInstance));

    milestoneAssert.isCompleted();
  }

  @Test
  public void test_IsCompleted_Fail() {
    final CaseInstance caseInstance = caseService().createCaseInstanceByKey("MilestoneAssertIsCompletedTest");

    expect(new Failure() {
      @Override
      public void when() {
        assertThat(caseInstance).milestone("Milestone").isCompleted();
      }
    });
  }
}
