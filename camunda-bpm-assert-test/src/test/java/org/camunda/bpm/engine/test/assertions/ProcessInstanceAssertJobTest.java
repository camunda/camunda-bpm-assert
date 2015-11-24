package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertJobTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_Single_Success() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // Then
    assertThat(processInstance).job().isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_SingleWithQuery_Success() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // Then
    assertThat(processInstance).job(jobQuery().executable()).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_MultipleWithQuery_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // Then
    assertThat(processInstance).job("ServiceTask_1").isNotNull();
    // And
    Mocks.register("serviceTask_1", "someService");
    // And
    execute(jobQuery().singleResult());
    // Then
    assertThat(processInstance).job("ServiceTask_2").isNotNull();
    // And
    assertThat(processInstance).job("ServiceTask_3").isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_NotYet_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).job("ServiceTask_2").isNotNull();
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_Passed_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // Then
    Mocks.register("serviceTask_1", "someService");
    // When
    execute(jobQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task("ServiceTask_1").isNotNull();
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_MultipleWithQuery_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // And
    Mocks.register("serviceTask_1", "someService");
    execute(jobQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).job(jobQuery().executable()).isNotNull();
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_MultipleWithTaskDefinitionKey_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // And
    Mocks.register("serviceTask_1", "someService");
    execute(jobQuery().singleResult());
    // Then
    assertThat(processInstance).job("ServiceTask_2").isNotNull();
    // And
    assertThat(processInstance).job("ServiceTask_3").isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_MultipleWithTaskDefinitionKey_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // And
    Mocks.register("serviceTask_1", "someService");
    execute(jobQuery().list().get(0));
    // And
    Mocks.register("serviceTask_2", "someService");
    execute(jobQuery().list().get(0));
    // And
    Mocks.register("serviceTask_3", "someService");
    execute(jobQuery().list().get(0));
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).job("ServiceTask_4").isNotNull();
      }
    }, ProcessEngineException.class);
  }

}
