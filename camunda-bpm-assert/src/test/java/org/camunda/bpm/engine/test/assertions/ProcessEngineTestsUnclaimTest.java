package org.camunda.bpm.engine.test.assertions;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.reset;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.task;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.unclaim;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jens Kanschik
 */
public class ProcessEngineTestsUnclaimTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @After
  public void tearDown() {
    reset();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-unclaim.bpmn"
  })
  public void testUnclaim_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-unclaim"
    );
    // When
    assertThat(task(processInstance)).isNotNull().isAssignedTo("fozzie");
    unclaim(task(processInstance));
    // Then
    assertThat(task(processInstance)).isNotNull().hasDefinitionKey("UserTask_1").isNotAssigned();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-unclaim.bpmn"
  })
  public void testUnclaim_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-unclaim"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        unclaim(task("UserTask_2", processInstance));
      }
    }, IllegalArgumentException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-unclaim.bpmn"
  })
  public void testUnclaim_AlreadyUnclaimed() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-unclaim"
    );
    // When
    unclaim(task(processInstance));
    unclaim(task(processInstance));
    // Then
    assertThat(task(processInstance)).isNotNull().hasDefinitionKey("UserTask_1").isNotAssigned();
  }

}
