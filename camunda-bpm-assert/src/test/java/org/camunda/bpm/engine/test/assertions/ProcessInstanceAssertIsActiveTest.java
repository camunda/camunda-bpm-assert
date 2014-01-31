package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertIsActiveTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isActive.bpmn"
  })
  public void testIsActive_Success() {
    // When
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isActive"
    );
    // Then
    assertThat(processInstance).isActive();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isActive.bpmn"
  })
  public void testIsActive_AfterActivation_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isActive"
    );
    // And
    runtimeService().suspendProcessInstanceById(processInstance.getId());
    // When
    runtimeService().activateProcessInstanceById(processInstance.getId());
    // Then
    assertThat(processInstance).isActive();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isActive.bpmn"
  })
  public void testIsActive_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isActive"
    );
    // When
    runtimeService().suspendProcessInstanceById(processInstance.getId());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isActive();
      }
    });
  }

}
