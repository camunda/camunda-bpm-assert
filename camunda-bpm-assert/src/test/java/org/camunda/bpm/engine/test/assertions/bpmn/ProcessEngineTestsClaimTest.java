package org.camunda.bpm.engine.test.assertions.bpmn;

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
public class ProcessEngineTestsClaimTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-claim.bpmn"
  })
  public void testClaim_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-claim"
    );
    // When
    claim(task(processInstance), "fozzie");
    // Then
    assertThat(task(processInstance)).isNotNull().hasDefinitionKey("UserTask_1").isAssignedTo("fozzie");
  }

  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-claim.bpmn"
  })
  public void testClaimNoTask_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-claim"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        claim(task("UserTask_2", processInstance), "fozzie");
      }
    }, IllegalArgumentException.class);
  }
  
  @Test
  @Deployment(resources = {"bpmn/ProcessEngineTests-claim.bpmn"
  })
  public void testClaimNoUser_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-claim"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        claim(task("UserTask_1", processInstance), null);
      }
    }, IllegalArgumentException.class);
  }

}
