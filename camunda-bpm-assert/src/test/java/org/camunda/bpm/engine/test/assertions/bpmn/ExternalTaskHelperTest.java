package org.camunda.bpm.engine.test.assertions.bpmn;

import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.fail;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;

public class ExternalTaskHelperTest {

  public static final String WORKER_ID = "testWorker";
  public static final String TOPIC = "testTopic";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {"bpmn/ExternalTaskHelperTest-fetchAndLock.bpmn"})
  public void shouldFetchAndLockASingleExternalTaskByTopic() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ExternalTaskHelperTest-fetchAndLock"
    );

    // When
    List<LockedExternalTask> lockedExternalTasks = BpmnAwareTests.fetchAndLock(WORKER_ID, TOPIC, 1);

    // Then
    assertThat(lockedExternalTasks).hasSize(1);
    assertThat(lockedExternalTasks.get(0).getWorkerId()).isEqualTo(WORKER_ID);
  }

  @Test
  public void fetchAndLockShouldThrowIllegalArgumentException() {
    try {
      BpmnAwareTests.fetchAndLock(null, TOPIC, 1);
      fail("IllegalArgumentException expected as workerId is null");
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessageContaining("Illegal call of fetchAndLock(workerId = 'null', topic = 'testTopic') - must" +
        " not be" +
        " " +
        "null");
    }
  }

  @Test
  @Deployment(resources = {"bpmn/ExternalTaskHelperTest-fetchAndLock.bpmn"})
  public void shouldCompleteASingleExternalTask() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ExternalTaskHelperTest-fetchAndLock"
    );
    List<LockedExternalTask> lockedExternalTasks = BpmnAwareTests.fetchAndLock(WORKER_ID, TOPIC, 1);
    assertThat(processInstance).hasNotPassed("externalTask");

    // When
    BpmnAwareTests.completeExternalTask(lockedExternalTasks.get(0));

    // Then
    assertThat(processInstance).hasPassed("externalTask");
  }

  @Test
  public void completeAndLockShouldThrowIllegalArgumentException() {
    LockedExternalTask lockedExternalTask = null;
    try {
      BpmnAwareTests.completeExternalTask(lockedExternalTask);
      fail("IllegalArgumentException expected as workerId is null");
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessageContaining("Illegal call of complete(lockedExternalTask = 'null') - must not be null");
    }
  }

}