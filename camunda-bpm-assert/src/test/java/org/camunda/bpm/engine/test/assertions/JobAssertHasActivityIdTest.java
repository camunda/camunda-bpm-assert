package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAssertHasActivityIdTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "JobAssert-hasActivityId.bpmn"
  })
  public void testHasActivityId_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "JobAssert-hasActivityId"
    );
    // And
    assertThat(processInstance).isNotNull();    
    // Then
    assertThat(job()).isNotNull();
    // And
    assertThat(job()).hasActivityId("ServiceTask_1");
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasActivityId.bpmn"
  })
  public void testHasActivityId_Failure() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "JobAssert-hasActivityId"
    );
    // And
    assertThat(processInstance).isNotNull();
    // Then
    assertThat(job()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(job()).hasActivityId("otherDeploymentId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasActivityId.bpmn"
  })
  public void testHasActivityId_Error_Null() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "JobAssert-hasActivityId"
    );
    // And
    assertThat(processInstance).isNotNull();
    // Then
    assertThat(job()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(job()).hasActivityId(null);
      }
    });
  }

}
