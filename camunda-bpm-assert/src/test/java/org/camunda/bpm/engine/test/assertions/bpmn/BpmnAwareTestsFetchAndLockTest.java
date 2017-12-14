package org.camunda.bpm.engine.test.assertions.bpmn;

import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

public class BpmnAwareTestsFetchAndLockTest {

  public static final String WORKER_ID = "testWorker";
  public static final String TOPIC = "testTopic";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {"bpmn/BpmnAwareTestsFetchAndLockTest-fetchAndLock.bpmn"})
  public void shouldFetchAndLockASingleExternalTaskByTopic() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "BpmnAwareTestsFetchAndLockTest-fetchAndLock"
    );

    // When
    List<LockedExternalTask> lockedExternalTasks = fetchAndLock(WORKER_ID, TOPIC, 1);

    // Then
    assertThat(lockedExternalTasks).hasSize(1);
    assertThat(lockedExternalTasks.get(0).getWorkerId()).isEqualTo(WORKER_ID);
  }

  @Test
  public void fetchAndLockShouldThrowIllegalArgumentException() {
    try {
      fetchAndLock(null, TOPIC, 1);
      fail("IllegalArgumentException expected as workerId is null");
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessageContaining("Illegal call of fetchAndLock(workerId = 'null', topic = 'testTopic') - must" +
        " not be" +
        " " +
        "null");
    }
  }

}