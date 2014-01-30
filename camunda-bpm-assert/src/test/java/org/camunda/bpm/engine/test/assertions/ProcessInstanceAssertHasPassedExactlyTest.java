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
public class ProcessInstanceAssertHasPassedExactlyTest extends FailingTestCaseHelper {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassedExactly_OnlyActivity_RunningInstance_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    assertThat(processInstance).hasPassedExactly("StartEvent_1", "UserTask_1", "ParallelGateway_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassedExactly_OnlyActivity_RunningInstance_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // Then
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassedExactly("UserTask_1");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassedExactly_ParallelActivities_RunningInstance_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    // Then
    assertThat(processInstance).hasPassedExactly("StartEvent_1", "UserTask_1", "ParallelGateway_1", "UserTask_2");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassedExactly_ParallelActivities_RunningInstance_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // When
    complete(taskQuery().singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    // Then
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassedExactly("UserTask_3");
      }
    });
    // And
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassedExactly("UserTask_4");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassedExactly_SeveralActivities_RunningInstance_Success() {
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
    assertThat(processInstance).hasPassedExactly("StartEvent_1", "UserTask_1", "ParallelGateway_1", "UserTask_2", "UserTask_3");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassedExactly_SeveralActivities_RunningInstance_Failure() {
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
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassedExactly("UserTask_1");
      }
    });
    // And
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassedExactly("UserTask_2");
      }
    });
    // And
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassedExactly("UserTask_3");
      }
    });
  }
  
  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassedExactly_SeveralActivities_HistoricInstance_Success() {
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
    assertThat(processInstance).hasPassedExactly("StartEvent_1", "UserTask_1", "ParallelGateway_1", "UserTask_2", "UserTask_3", "UserTask_4", "EndEvent_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassed.bpmn"
  })
  public void testHasPassedExactly_SeveralActivities_HistoricInstance_Failure() {
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
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassedExactly("UserTask_1");
      }
    });
    // And
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassedExactly("UserTask_2");
      }
    });
    // And
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassedExactly("UserTask_3");
      }
    });
    // And
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassedExactly("UserTask_4");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testHasPassedExactly_Null_Error() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // Then
    failure(new Check() {
      @Override
      public void when() {
        //noinspection NullArgumentToVariableArgMethod
        assertThat(processInstance).hasPassedExactly(null);
      }
    });
    // And
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassedExactly("ok", null);
      }
    });
    // And
    failure(new Check() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassedExactly(null, "ok");
      }
    });
    // And
    failure(new Check() {
      @Override
      public void when() {
        String [] args = new String[] {};
        assertThat(processInstance).hasPassedExactly(args);
      }
    });

  }

}
