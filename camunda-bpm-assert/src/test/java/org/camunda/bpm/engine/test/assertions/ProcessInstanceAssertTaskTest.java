package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngineException;
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
public class ProcessInstanceAssertTaskTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_Single_Success() {
    // When
    final ProcessInstance processInstance = startProcess();
    // Then
    assertThat(processInstance).task().isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_SingleWithQuery_Success() {
    // When
    final ProcessInstance processInstance = startProcess();
    // Then
    assertThat(processInstance).task(taskQuery().taskDefinitionKey("UserTask_1")).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_MultipleWithQuery_Success() {
    // When
    final ProcessInstance processInstance = startProcess();
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
    final ProcessInstance processInstance = startProcess();
    // Then
    expect(new Failure() {
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
    final ProcessInstance processInstance = startProcess();
    // When
    complete(taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task(taskQuery().taskDefinitionKey("UserTask_1")).isNotNull();
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_MultipleWithQuery_Failure() {
    // When
    final ProcessInstance processInstance = startProcess();
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_1").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_3").singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task(taskQuery().taskDefinitionKey("UserTask_4")).isNotNull();
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_MultipleWithTaskDefinitionKey_Success() {
    // When
    final ProcessInstance processInstance = startProcess();
    // And
    complete(taskQuery().singleResult());
    // Then
    assertThat(processInstance).task("UserTask_2").isNotNull();
    // And
    assertThat(processInstance).task("UserTask_3").isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_MultipleWithTaskDefinitionKey_Failure() {
    // When
    final ProcessInstance processInstance = startProcess();
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_1").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_3").singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task("UserTask_4").isNotNull();
      }
    }, ProcessEngineException.class);
  }


  @Test
  @Deployment(resources = {
      "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_notWaitingAtTaskDefinitionKey() {
    final ProcessInstance processInstance = startProcess();
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task("UserTask_2").isNotNull();
      }
    });
  }

  private ProcessInstance startProcess() {
    return runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-task"
    );
  }
  
}
