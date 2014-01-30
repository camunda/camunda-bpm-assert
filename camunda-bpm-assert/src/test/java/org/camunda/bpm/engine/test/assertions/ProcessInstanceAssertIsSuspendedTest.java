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
public class ProcessInstanceAssertIsSuspendedTest {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isSuspended.bpmn"
  })
  public void testIsSuspended_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isSuspended"
    );
    // When
    runtimeService().suspendProcessInstanceById(processInstance.getId());
    // Then
    assertThat(processInstance).isSuspended();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isSuspended.bpmn"
  })
  public void testIsSuspended_AfterStart_Failure() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isSuspended"
    );
    // When
    try {
      assertThat(processInstance).isSuspended();
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isSuspended.bpmn"
  })
  public void testIsSuspended_AfterActivation_Failure() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isSuspended"
    );
    // And
    runtimeService().suspendProcessInstanceById(processInstance.getId());
    // When
    runtimeService().activateProcessInstanceById(processInstance.getId());
    try {
      assertThat(processInstance).isSuspended();
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

}
