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
public class ProcessInstanceAssertTaskTest {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_Single_Success() {
    // When
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-task"
    );
    // Then
    assertThat(processInstance).task().isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_SingleWithQuery_Success() {
    // When
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-task"
    );
    // Then
    assertThat(processInstance).task(taskQuery().taskDefinitionKey("UserTask_1")).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_MultipleWithQuery_Success() {
    // When
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-task"
    );
    // And
    complete(taskQuery().singleResult());
    // Then
    assertThat(processInstance).task(taskQuery().taskDefinitionKey("UserTask_2")).isNotNull();
    // And
    assertThat(processInstance).task(taskQuery().taskDefinitionKey("UserTask_3")).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_NotYet_Failure() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-task"
    );
    // When
    try {
      assertThat(processInstance).task(taskQuery().taskDefinitionKey("UserTask_2")).isNotNull();
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
      return;
    }
    fail("expected an assertion error to be thrown, but did not see any");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_Passed_Failure() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-task"
    );
    // And
    complete(taskQuery().singleResult());
    // When
    try {
      assertThat(processInstance).task(taskQuery().taskDefinitionKey("UserTask_1")).isNotNull();
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
      return;
    }
    fail("expected an assertion error to be thrown, but did not see any");
  }

}
