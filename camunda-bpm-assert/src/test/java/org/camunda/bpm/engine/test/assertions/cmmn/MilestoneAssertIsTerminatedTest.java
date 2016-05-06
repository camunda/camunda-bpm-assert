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
@Deployment(resources = "cmmn/MilestoneAssertIsTerminatedTest.cmmn")
public class MilestoneAssertIsTerminatedTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  public void test_IsTerminated_Success() {
    CaseInstance caseInstance = caseService().createCaseInstanceByKey("MilestoneAssertIsTerminatedTest");
    MilestoneAssert milestoneAssert = assertThat(caseInstance).stage("Stage").milestone("Milestone");

    complete(caseExecution("PI_TaskA", caseInstance));

    milestoneAssert.isTerminated();
  }

  @Test
  public void test_IsTerminated_Fail() {
    final CaseInstance caseInstance = caseService().createCaseInstanceByKey("MilestoneAssertIsTerminatedTest");

    expect(new Failure() {
      @Override
      public void when() {
        assertThat(caseInstance).stage("Stage").milestone("Milestone").isTerminated();
      }
    });
  }
}
