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
public class ProcessInstanceAssertHasPassedTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassed_OnlyActivity_RunningInstance_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    assertThat(processInstance).hasPassed("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassed_OnlyActivity_RunningInstance_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassed("UserTask_1");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassed_ParallelActivities_RunningInstance_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    // Then
    assertThat(processInstance).hasPassed("UserTask_1");
    // And
    assertThat(processInstance).hasPassed("UserTask_2");
    // And
    assertThat(processInstance).hasPassed("UserTask_1", "UserTask_2");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassed_ParallelActivities_RunningInstance_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassed("UserTask_3");
      }
    });
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassed("UserTask_4");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassed_SeveralActivities_RunningInstance_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_3").singleResult());
    // Then
    assertThat(processInstance).hasPassed("UserTask_1");
    // And
    assertThat(processInstance).hasPassed("UserTask_2");
    // And
    assertThat(processInstance).hasPassed("UserTask_3");
    // And
    assertThat(processInstance).hasPassed("UserTask_1", "UserTask_2", "UserTask_3");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassed_SeveralActivities_RunningInstance_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_3").singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassed("UserTask_4");
      }
    });
  }
  
  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassed_SeveralActivities_HistoricInstance_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_3").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_4").singleResult());
    // Then
    assertThat(processInstance).hasPassed("UserTask_1");
    // And
    assertThat(processInstance).hasPassed("UserTask_2");
    // And
    assertThat(processInstance).hasPassed("UserTask_3");
    // And
    assertThat(processInstance).hasPassed("UserTask_4");
    // And
    assertThat(processInstance).hasPassed("UserTask_1", "UserTask_2", "UserTask_3", "UserTask_4");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassed_SeveralActivities_HistoricInstance_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_3").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_4").singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassed("UserTask_5");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testHasPassed_Null_Error() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        //noinspection NullArgumentToVariableArgMethod
        assertThat(processInstance).hasPassed(null);
      }
    });
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassed("ok", null);
      }
    });
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassed(null, "ok");
      }
    });
    expect(new Failure() {
      @Override
      public void when() {
        String[] args = new String[]{};
        assertThat(processInstance).hasPassed(args);
      }
    });
  }

}
