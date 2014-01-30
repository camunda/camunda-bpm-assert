package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Check;
import org.camunda.bpm.engine.test.assertions.helpers.FailingTestCaseHelper;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertIsEndedTest extends FailingTestCaseHelper {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isEnded.bpmn"
  })
  public void testIsEnded_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isEnded"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    assertThat(processInstance).isEnded();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isEnded.bpmn"
  })
  public void testIsEnded_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isEnded"
    );
    // Then
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).isEnded();
      }
    });
  }

}
