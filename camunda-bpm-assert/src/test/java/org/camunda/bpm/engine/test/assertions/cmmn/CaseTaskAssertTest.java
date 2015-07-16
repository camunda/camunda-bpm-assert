package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenter@holisticon.de>
 */
public class CaseTaskAssertTest {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Ignore
  @Test
  @Deployment(resources = {
    "cmmn/TaskTest.cmmn"
  })
  public void test() {
    //Given
    CaseInstance caseInstance = givenStartedCase();
    // When
    complete(caseTask("PI_TaskA", caseInstance));
    // Then
    assertThat(caseInstance).isActive();
    assertThat(caseInstance).task("PI_TaskA").isCompleted();
  }

  private CaseInstance givenStartedCase() {
    return caseService().createCaseInstanceByKey("Case_TaskTests");
  }
}
