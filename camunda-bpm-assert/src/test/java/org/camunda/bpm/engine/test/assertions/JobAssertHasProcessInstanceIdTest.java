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
public class JobAssertHasProcessInstanceIdTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "JobAssert-hasProcessInstanceId.bpmn"
  })
  public void testHasProcessInstanceId_Success() {
    // When
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "JobAssert-hasProcessInstanceId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    assertThat(jobQuery().singleResult()).hasProcessInstanceId(processInstance.getProcessInstanceId());
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasProcessInstanceId.bpmn"
  })
  public void testHasProcessInstanceId_Failure() {
    // When
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasProcessInstanceId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasProcessInstanceId("someOtherId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasProcessInstanceId.bpmn"
  })
  public void testHasProcessInstanceId_Error_Null() {
    // When
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasProcessInstanceId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasProcessInstanceId(null);
      }
    });
  }

}
