package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineTestsCompleteTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @After
  public void tearDown() {
    reset();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-complete.bpmn"
  })
  public void testComplete_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-complete"
    );
    // When
    complete(task(processInstance));
    // Then
    assertThat(processInstance).isEnded();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-complete.bpmn"
  })
  public void testComplete_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-complete"
    );
    // And
    final Task task = task(processInstance);
    // When
    complete(task);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        complete(task);
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-complete.bpmn"
  })
  public void testComplete_WithVariables_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-complete"
    );
    // When
    complete(task(processInstance), withVariables("a", "b"));
    // Then
    assertThat(processInstance).isEnded();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-complete.bpmn"
  })
  public void testComplete_WithVariables_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-complete"
    );
    // And
    final Task task = task(processInstance);
    // When
    complete(task);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        complete(task, withVariables("a", "b"));
      }
    }, ProcessEngineException.class);
  }

}
