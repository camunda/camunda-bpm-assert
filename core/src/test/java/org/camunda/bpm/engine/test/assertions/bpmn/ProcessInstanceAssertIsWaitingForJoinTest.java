/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.test.assertions.bpmn;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.Failure;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Ingo Richtsmeier
 *
 */
public class ProcessInstanceAssertIsWaitingForJoinTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = "bpmn/ProcessInstanceAssert-isWaitingForJoinAt.bpmn")
  public void testIsWaitingForJoinAt() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingForJoinAt"
    );
    assertThat(processInstance).isWaitingAt("UserTask1", "UserTask2");
    complete(task("UserTask1"));
    
    // Then
    assertThat(processInstance).isWaitingForJoinAt("JoinGateway");
  }
  
  @Test
  @Deployment(resources = "bpmn/ProcessInstanceAssert-isWaitingForJoinAt.bpmn")
  public void testIsNotWaitingForJoin() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingForJoinAt"
    );
    
    // Then
    assertThat(processInstance).isNotWaitingForJoinAt("JoinGateway");
  }

  @Test
  @Deployment(resources = "bpmn/ProcessInstanceAssert-isWaitingForJoinAt2.bpmn")
  public void testNestedJoinGateways() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingForJoinAt2"
    );
    assertThat(processInstance).isWaitingAt("Task1", "Task2", "Task3", "Task4");
    complete(task("Task1"));
    complete(task("Task3"));

    // Then
    assertThat(processInstance).isWaitingForJoinAt("JoinGateway1");
    assertThat(processInstance).isWaitingForJoinAt("JoinGateway2");
    
    // And 
    complete(task("Task2"));
    assertThat(processInstance).isWaitingForJoinAt("JoinGateway2");
    assertThat(processInstance).isWaitingForJoinAt("JoinGateway3");
  }
  
  @Test
  @Deployment(resources = "bpmn/ProcessInstanceAssert-isWaitingForJoinAt3.bpmn")
  public void testNestedInclusiveGatewaysAll() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingForJoinAt3"
    );
    assertThat(processInstance).isWaitingAt("Task1", "Task2", "Task3");
    complete(task("Task1"));  
    
    // Then
    assertThat(processInstance).isWaitingForJoinAt("JoinInclusiveGateway1");
    
    // And
    complete(task("Task3"));
    assertThat(processInstance).isWaitingForJoinAt("JoinInclusiveGateway2");
  }
  
  @Test
  @Deployment(resources = "bpmn/ProcessInstanceAssert-isWaitingForJoinAt.bpmn")
  public void testIsNotWaitingForJoinFails() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingForJoinAt"
    );
    assertThat(processInstance).isWaitingAt("UserTask1", "UserTask2");
    complete(task("UserTask1"));
    
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isNotWaitingForJoinAt("JoinGateway");
      }
    }, "NOT to be waiting for join");
  }
  
  @Test
  @Deployment(resources = "bpmn/ProcessInstanceAssert-isWaitingForJoinAt.bpmn")
  public void testIsWaitingForJoinAtNull() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingForJoinAt"
    );
    
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingForJoinAt(null);        
      }
    }, "Expecting gatewayId not to be null and not to be empty");
  }

  @Test
  @Deployment(resources = "bpmn/ProcessInstanceAssert-isWaitingForJoinAt.bpmn")
  public void testIsWaitingForJoinAtEmpty() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingForJoinAt"
    );
    
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingForJoinAt("");        
      }
    }, "Expecting gatewayId not to be null and not to be empty");
  }
  
  @Test
  @Deployment(resources = "bpmn/ProcessInstanceAssert-isWaitingForJoinAt.bpmn")
  public void testIsWaitingForJoinAtWrongActivityId() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingForJoinAt"
    );
    assertThat(processInstance).isWaitingAt("UserTask1", "UserTask2");
    complete(task("UserTask1"));
    
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingForJoinAt("WrongGateway");
      }
    }, "Instead it is waiting at [");
    
  }

  @Test
  @Deployment(resources = "bpmn/ProcessInstanceAssert-isWaitingForJoinAt.bpmn")
  public void testProcessWithJoinInCompletedProcessInstance() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingForJoinAt"
    );
    assertThat(processInstance).isWaitingAt("UserTask1", "UserTask2");
    complete(task("UserTask1"));
    complete(task("UserTask2"));
    
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingForJoinAt("JoinGateway");
      }
    }, "maybe completed");
  }
}
