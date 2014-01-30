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
public class ProcessInstanceAssertTaskTest extends FailingTestCaseHelper {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_Single_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
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
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
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
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
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
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-task"
    );
    // Then
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).task(taskQuery().taskDefinitionKey("UserTask_2")).isNotNull();
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_Passed_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-task"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).task(taskQuery().taskDefinitionKey("UserTask_1")).isNotNull();
      }
    });
  }

}
