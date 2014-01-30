package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertIsNotEndedTest {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule(); 

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isEnded.bpmn"
  })
  public void testIsNotEnded_Success() {
    // When
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isEnded"
    );
    // Then
    assertThat(processInstance).isNotEnded();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isEnded.bpmn"
  })
  public void testIsNotEnded_Failure() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isEnded"
    );
    // When
    complete(taskQuery().singleResult());
    try {
      assertThat(processInstance).isNotEnded();
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

}
