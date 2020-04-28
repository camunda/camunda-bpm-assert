package org.camunda.bpm.engine.test.assertions.bpmn;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.execute;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.job;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

public class ProcessInstanceAssertIsWaitingAtActivityTreeTests extends ProcessAssertTestCase {
  
  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {"bpmn/ProcessInstanceAssert-isWaitingAt-ActivityTreeTests.bpmn"
  })
  public void testIsWaitingAt_AsyncBefore() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingAt-ActivityTreeTests"
    );
    // Then
    assertThat(processInstance).isWaitingAt("AsyncServiceTask");
  }
  
  @Test
  @Deployment(resources = {"bpmn/ProcessInstanceAssert-isWaitingAt-ActivityTreeTests.bpmn"})
  public void testIsWaitingAt_Subprocess() {
    // When
    final ProcessInstance processInstance = runtimeService()
        .createProcessInstanceByKey("ProcessInstanceAssert-isWaitingAt-ActivityTreeTests")
        .startAfterActivity("AsyncServiceTask")
        .execute();
    // Then
    assertThat(processInstance).isWaitingAt("SubProcess", "SubSubProcess", "NestedAsyncServiceTask");
  }
  
  @Test
  @Deployment(resources = {"bpmn/ProcessInstanceAssert-isWaitingAt-Subprocesses.bpmn"})
  public void testIsWaitingAtNestedUserTask() {
    // When
    final ProcessInstance processInstance = runtimeService()
        .startProcessInstanceByKey("ProcessInstanceAssert-isWaitingAt-Subprocesses");
    
    // Then
    assertThat(processInstance).isWaitingAtExactly("SubProcess", "SubSubProcess", "NestedUserTask");
  }
  
  @Test
  @Deployment(resources = {"bpmn/ProcessInstanceAssert-isWaitingAt-AsyncUserTask.bpmn"})
  public void testIsWaitingAtAsyncUserTask() {
    // When
    ProcessInstance processInstance = runtimeService()
        .startProcessInstanceByKey("ProcessInstanceAssert-isWaitingAt-AsyncUserTask");
    
    // Then
    assertThat(processInstance).isWaitingAt("AsyncUserTask");
    
    // And when
    execute(job()); //async before
    
    //Then
    assertThat(processInstance).isWaitingAt("AsyncUserTask");
    
    // And when
    complete(task());
    
    // Then
    assertThat(processInstance).isWaitingAt("AsyncUserTask");
    
    // And when
    execute(job()); // async after
    
    // Then
    assertThat(processInstance).isEnded();
  }

}
