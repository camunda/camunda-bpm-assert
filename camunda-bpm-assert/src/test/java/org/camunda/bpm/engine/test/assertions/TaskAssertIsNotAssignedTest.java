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
public class TaskAssertIsNotAssignedTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "TaskAssert-isNotAssigned.bpmn"
  })
  public void testIsNotAssigned_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-isNotAssigned"
    );
    // Then
    assertThat(processInstance).task().isNotAssigned();
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-isNotAssigned.bpmn"
  })
  public void testIsNotAssigned_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-isNotAssigned"
    );
    // When
    claim(taskQuery().singleResult(), "fozzie");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().isNotAssigned();
      }
    });
  }
  
}
