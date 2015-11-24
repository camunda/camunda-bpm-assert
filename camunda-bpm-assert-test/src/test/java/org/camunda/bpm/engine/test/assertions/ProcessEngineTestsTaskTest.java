package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
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
public class ProcessEngineTestsTaskTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @After
  public void tearDown() {
    reset();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    assertThat(processInstance).isNotNull();
    // Then
    assertThat(task()).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_OnlyActivity_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        task();
      }
    }, IllegalStateException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    assertThat(processInstance).isNotNull();
    // And
    complete(task());
    // When
    expect(new Failure() {
      @Override
      public void when() {
        task();
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    assertThat(processInstance).isNotNull();
    // Then
    assertThat(task("UserTask_1")).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    assertThat(processInstance).isNotNull();
    complete(task());
    // Then
    assertThat(task("UserTask_2")).isNotNull().hasDefinitionKey("UserTask_2");
    // And
    assertThat(task("UserTask_3")).isNotNull().hasDefinitionKey("UserTask_3");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_OnlyActivity_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        task("UserTask_1");
      }
    }, IllegalStateException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    assertThat(processInstance).isNotNull();
    // Then
    assertThat(task(taskQuery().taskDefinitionKey("UserTask_1"))).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_OnlyActivity_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        task(taskQuery().taskDefinitionKey("UserTask_1"));
      }
    }, IllegalStateException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    assertThat(processInstance).isNotNull();
    complete(task());
    // Then
    assertThat(task(taskQuery().taskDefinitionKey("UserTask_2"))).isNotNull().hasDefinitionKey("UserTask_2");
    // And
    assertThat(task(taskQuery().taskDefinitionKey("UserTask_3"))).isNotNull().hasDefinitionKey("UserTask_3");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    assertThat(processInstance).isNotNull();
    // And
    complete(task());
    // When
    expect(new Failure() {
      @Override
      public void when() {
        task(taskQuery());
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    assertThat(task(processInstance)).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_TwoActivities_processInstance_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    complete(task(processInstance));
    // When
    expect(new Failure() {
      @Override
      public void when() {
        task(processInstance);
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    assertThat(task("UserTask_1", processInstance)).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_processInstance_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    complete(task(processInstance));
    // Then
    assertThat(task("UserTask_2", processInstance)).isNotNull().hasDefinitionKey("UserTask_2");
    // And
    assertThat(task("UserTask_3", processInstance)).isNotNull().hasDefinitionKey("UserTask_3");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    assertThat(task(taskQuery().taskDefinitionKey("UserTask_1"), processInstance)).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_processInstance_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    complete(task(processInstance));
    // Then
    assertThat(task(taskQuery().taskDefinitionKey("UserTask_2"), processInstance)).isNotNull().hasDefinitionKey("UserTask_2");
    // And
    assertThat(task(taskQuery().taskDefinitionKey("UserTask_3"), processInstance)).isNotNull().hasDefinitionKey("UserTask_3");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_processInstance_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    complete(task(processInstance));
    // When
    expect(new Failure() {
      @Override
      public void when() {
        task(taskQuery(), processInstance);
      }
    }, ProcessEngineException.class);
  }

}
