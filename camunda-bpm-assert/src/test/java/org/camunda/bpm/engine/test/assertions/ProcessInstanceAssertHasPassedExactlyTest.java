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
public class ProcessInstanceAssertHasPassedExactlyTest {

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
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // When
    try {
      assertThat(processInstance).hasPassedExactly("UserTask_1");
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
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
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // And
    complete(taskQuery().singleResult());
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    // When
    try {
      assertThat(processInstance).hasPassedExactly("UserTask_3");
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
    try {
      assertThat(processInstance).hasPassedExactly("UserTask_4");
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
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
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
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
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // And
    complete(taskQuery().singleResult());
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    complete(taskQuery().taskDefinitionKey("UserTask_3").singleResult());
    // When
    try {
      assertThat(processInstance).hasPassedExactly("UserTask_1");
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
    try {
      assertThat(processInstance).hasPassedExactly("UserTask_2");
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
    try {
      assertThat(processInstance).hasPassedExactly("UserTask_3");
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
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
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    complete(taskQuery().taskDefinitionKey("UserTask_3").singleResult());
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
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasPassed"
    );
    // And
    complete(taskQuery().singleResult());
    complete(taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    complete(taskQuery().taskDefinitionKey("UserTask_3").singleResult());
    complete(taskQuery().taskDefinitionKey("UserTask_4").singleResult());
    // When
    try {
      assertThat(processInstance).hasPassedExactly("UserTask_1");
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
    try {
      assertThat(processInstance).hasPassedExactly("UserTask_2");
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
    try {
      assertThat(processInstance).hasPassedExactly("UserTask_3");
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
    try {
      assertThat(processInstance).hasPassedExactly("UserTask_4");
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
  public void testHasPassedExactly_Null_Error() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt"
    );
    try {
      // When
      //noinspection NullArgumentToVariableArgMethod
      assertThat(processInstance).hasPassedExactly(null);
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
    try {
      // When
      assertThat(processInstance).hasPassedExactly("ok", null);
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
    try {
      // When
      assertThat(processInstance).hasPassedExactly(null, "ok");
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
    try {
      // When
      String [] args = new String[] {};
      assertThat(processInstance).hasPassedExactly(args);
      fail("expected an assertion error to be thrown, but did not see any");
      // Then
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    }
  }

}
