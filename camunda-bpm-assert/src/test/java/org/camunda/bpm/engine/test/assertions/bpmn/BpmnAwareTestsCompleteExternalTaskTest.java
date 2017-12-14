package org.camunda.bpm.engine.test.assertions.bpmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.fail;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.completeExternalTask;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.fetchAndLock;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;

public class BpmnAwareTestsCompleteExternalTaskTest {

  public static final String WORKER_ID = "testWorker";
  public static final String TOPIC = "testTopic";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {"bpmn/BpmnAwareTestsFetchAndLockTest-completeExternalTask.bpmn"})
  public void shouldCompleteASingleExternalTask() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "BpmnAwareTestsFetchAndLockTest-completeExternalTask"
    );
    List<LockedExternalTask> lockedExternalTasks = fetchAndLock(WORKER_ID, TOPIC, 1);
    assertThat(processInstance).hasNotPassed("externalTask");

    // When
    completeExternalTask(lockedExternalTasks.get(0));

    // Then
    assertThat(processInstance).hasPassed("externalTask");
  }

  @Test
  public void completeAndLockShouldThrowIllegalArgumentException() {
    LockedExternalTask lockedExternalTask = null;
    try {
      completeExternalTask(lockedExternalTask);
      fail("IllegalArgumentException expected as workerId is null");
    } catch (IllegalArgumentException e) {
      Assertions.assertThat(e).hasMessageContaining("Illegal call of complete(lockedExternalTask = 'null') - must not be null");
    }
  }
}
