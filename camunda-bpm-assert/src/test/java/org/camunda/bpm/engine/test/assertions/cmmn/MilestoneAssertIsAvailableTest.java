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

@Deployment(resources = "cmmn/MilestoneAssertIsAvailableTest.cmmn")
public class MilestoneAssertIsAvailableTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  public void test_isAvailable_Success() {
    CaseInstance caseInstance = caseService().createCaseInstanceByKey("MilestoneAssertIsAvailableTest");

    assertThat(caseInstance).milestone("Milestone").isAvailable();
  }

  @Test
  public void test_isAvailable_Fail() {
    final CaseInstance caseInstance = caseService().createCaseInstanceByKey("MilestoneAssertIsAvailableTest");

    complete(caseExecution("PI_TaskA", caseInstance));

    expect(new Failure() {
      @Override
      public void when() {
        assertThat(caseInstance).milestone("Milestone").isAvailable();
      }
    });
  }
}
