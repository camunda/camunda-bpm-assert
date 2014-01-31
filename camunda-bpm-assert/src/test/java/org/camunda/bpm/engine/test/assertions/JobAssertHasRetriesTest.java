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
public class JobAssertHasRetriesTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "JobAssert-hasRetries.bpmn"
  })
  public void testHasRetries_Success() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasRetries"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    assertThat(jobQuery().singleResult()).hasRetries(3);
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasRetries.bpmn"
  })
  public void testHasRetries_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasRetries"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasRetries(2);
      }
    });
  }

}
