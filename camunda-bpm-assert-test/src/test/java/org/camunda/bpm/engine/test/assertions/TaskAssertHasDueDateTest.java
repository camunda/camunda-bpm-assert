package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class TaskAssertHasDueDateTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "TaskAssert-hasDueDate.bpmn"
  })
  public void testHasDueDate_Success() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasDueDate"
    );
    final Date dueDate = new Date();
    Task task = taskQuery().singleResult();
    task.setDueDate(dueDate);
    taskService().saveTask(task);
    // Then
    assertThat(processInstance).task().hasDueDate(dueDate);
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasDueDate.bpmn"
  })
  public void testHasDueDate_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasDueDate"
    );
    // When
    final Date dueDate = new Date();
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasDueDate(dueDate);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasDueDate.bpmn"
  })
  public void testHasDueDate_Null_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasDueDate"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasDueDate(null);
      }
    });
  }

}
