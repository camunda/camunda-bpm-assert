package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertTest {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Before
  public void setUp() {
    init(processEngineRule.getProcessEngine());
  }

  @After
  public void tearDown() {
    Mocks.reset();
    reset();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAt_Only_Activity_Success() {
    // When
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // Then
    assertThat(processInstance).isWaitingAt("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAt_Only_Activity_Failure() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // When
    try {
      assertThat(processInstance).isWaitingAt("UserTask_2");
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAt_Non_Existing_Activity_Failure() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // When
    try {
      assertThat(processInstance).isWaitingAt("NonExistingUserTask");
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAt_One_Of_Two_Activities_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    assertThat(processInstance).isWaitingAt("UserTask_2");
    assertThat(processInstance).isWaitingAt("UserTask_3");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAt_One_Of_Two_Activities_Failure() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    // And
    complete(taskQuery().singleResult());
    // When
    try {
      assertThat(processInstance).isWaitingAt("UserTask_1");
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingAt.bpmn"
  })
  public void testIsWaitingAt_Null_Error() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    try {
    // When
      assertThat(processInstance).isWaitingAt(null);
      fail("expected an assertion error to be thrown, but did not see any");
    // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

}
