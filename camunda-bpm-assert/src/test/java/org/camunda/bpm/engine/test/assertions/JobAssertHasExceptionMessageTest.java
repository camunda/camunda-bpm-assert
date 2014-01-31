package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngineException;
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
public class JobAssertHasExceptionMessageTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "JobAssert-hasExceptionMessage.bpmn"
  })
  public void testHasExceptionMessage_Success() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasExceptionMessage"
    );
    // When
    try {
      managementService().executeJob(jobQuery().singleResult().getId());
      fail ("expected ProcessEngineException to be thrown, but did not find any.");
    } catch (ProcessEngineException t) {}
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    assertThat(jobQuery().singleResult()).hasExceptionMessage();
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasExceptionMessage.bpmn"
  })
  public void testHasExceptionMessage_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasExceptionMessage"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasExceptionMessage();
      }
    });
  }

}
