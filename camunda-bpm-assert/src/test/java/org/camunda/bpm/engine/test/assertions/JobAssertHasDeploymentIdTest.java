package org.camunda.bpm.engine.test.assertions;

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
public class JobAssertHasDeploymentIdTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "JobAssert-hasDeploymentId.bpmn"
  })
  public void testHasDeploymentId_Success() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasDeploymentId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    assertThat(jobQuery().singleResult()).hasDeploymentId(processDefinitionQuery().processDefinitionId(processInstanceQuery().singleResult().getProcessDefinitionId()).singleResult().getDeploymentId());
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasDeploymentId.bpmn"
  })
  public void testHasDeploymentId_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasDeploymentId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(jobQuery().singleResult()).hasDeploymentId("otherDeploymentId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasDeploymentId.bpmn"
  })
  public void testHasDeploymentId_Error_Null() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasDeploymentId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(jobQuery().singleResult()).hasDeploymentId(null);
      }
    });
  }

}
