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
public class JobAssertHasExecutionIdTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "JobAssert-hasExecutionId.bpmn"
  })
  public void testHasExecutionId_Success() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasExecutionId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    assertThat(jobQuery().singleResult()).hasExecutionId(jobQuery().singleResult().getExecutionId());
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasExecutionId.bpmn"
  })
  public void testHasExecutionId_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasExecutionId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasExecutionId("otherExecutionId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasExecutionId.bpmn"
  })
  public void testHasExecutionId_Error_Null() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasExecutionId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasExecutionId(null);
      }
    });
  }

}
