package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
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
public class TaskAssertIsAssignedToTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "TaskAssert-isAssignedTo.bpmn"
  })
  public void testIsAssignedTo_Success() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-isAssignedTo"
    );
    // When
    claim(taskQuery().singleResult(), "fozzie");
    // Then
    assertThat(processInstance).task().isAssignedTo("fozzie");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-isAssignedTo.bpmn"
  })
  public void testIsAssignedTo_NotAssigned_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-isAssignedTo"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().isAssignedTo("fozzie");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-isAssignedTo.bpmn"
  })
  public void testIsAssignedTo_OtherAssignee_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-isAssignedTo"
    );
    // When
    claim(taskQuery().singleResult(), "fozzie");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().isAssignedTo("gonzo");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-isAssignedTo.bpmn"
  })
  public void testIsAssignedTo_Null_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-isAssignedTo"
    );
    // When
    claim(taskQuery().singleResult(), "fozzie");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().isAssignedTo(null);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-isAssignedTo.bpmn"
  })
  public void testIsAssignedTo_NonExistingTask_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "TaskAssert-isAssignedTo"
    );
    // When
    final Task task = taskQuery().singleResult();
    complete(task);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(task).isAssignedTo("fozzie");
      }
    });
  }

}
