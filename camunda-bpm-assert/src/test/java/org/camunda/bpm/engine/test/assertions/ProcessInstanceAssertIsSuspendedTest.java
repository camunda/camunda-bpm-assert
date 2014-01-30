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
public class ProcessInstanceAssertIsSuspendedTest extends FailingTestCaseHelper {

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
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isSuspended"
    );
    // Then
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).isSuspended();
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isSuspended.bpmn"
  })
  public void testIsSuspended_AfterActivation_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isSuspended"
    );
    // When
    runtimeService().suspendProcessInstanceById(processInstance.getId());
    // And
    runtimeService().activateProcessInstanceById(processInstance.getId());
    // Then
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).isSuspended();
      }
    });
  }

}
