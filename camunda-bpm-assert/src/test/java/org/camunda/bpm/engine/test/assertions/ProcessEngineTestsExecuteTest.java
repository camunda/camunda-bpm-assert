package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.runtime.Job;
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
public class ProcessEngineTestsExecuteTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-execute.bpmn"
  })
  public void testExecute_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-execute"
    );
    // Then
    assertThat(processInstance).isNotEnded();
    // And
    assertThat(job()).isNotNull();
    // When
    execute(job());
    // Then
    assertThat(job()).isNull();
    // And
    assertThat(processInstance).isEnded();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-execute.bpmn"
  })
  public void testExecute_Failure() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-execute"
    );
    // And
    assertThat(processInstance).isNotEnded();
    // And
    final Job job = job();
    execute(job);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        execute(job);
      }
    }, IllegalStateException.class);
  }

}
