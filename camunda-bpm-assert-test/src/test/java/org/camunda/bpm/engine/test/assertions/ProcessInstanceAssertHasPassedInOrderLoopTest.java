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
public class ProcessInstanceAssertHasPassedInOrderLoopTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();
  
  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasPassedInOrder-loop.bpmn"
  })
  public void testHasPassedInOrder_SeveralActivities_HistoricInstance() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassedInOrder-loop", 
      withVariables("exit", false)
    );
    // When
    complete(taskQuery().taskDefinitionKey("UserTask_1").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_3").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_4").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_3").singleResult());
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_4").singleResult(), withVariables("exit", true));
    // And
    complete(taskQuery().taskDefinitionKey("UserTask_5").singleResult());
    // Then
    assertThat(processInstance).hasPassedInOrder("UserTask_1", "UserTask_2", "UserTask_5");
    // And
    assertThat(processInstance).hasPassedInOrder("UserTask_1", "UserTask_3", "UserTask_4", "UserTask_3", "UserTask_4", "UserTask_5");
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).hasPassedInOrder("UserTask_1", "UserTask_3", "UserTask_4", "UserTask_3", "UserTask_4", "UserTask_3", "UserTask_4", "UserTask_5");
      }
    });
  }

}
