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

import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.assertj.core.api.Assertions.*;

public class ProcessEngineTestsFindTaskTest extends ProcessAssertTestCase {
  
  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = "bpmn/ProcessEngineTests-findTest.bpmn")
  public void testFindPlainTaskByName() {
    // Given
    runtimeService().startProcessInstanceByKey("Process_TestID");
    // When
    String id = find("Plain task");
    // Then 
    assertThat(id).isEqualTo("PlainTask_TestID");
  }

  @Test
  @Deployment(resources = "bpmn/ProcessEngineTests-findTest.bpmn")
  public void testFindEndEventByName() {
    // Given
    runtimeService().startProcessInstanceByKey("Process_TestID");
    // When
    String end = find("End");
    // Then 
    assertThat(end).isEqualTo("End_TestID");
  }

  @Test
  @Deployment(resources = "bpmn/ProcessEngineTests-findTest.bpmn")
  public void testFindAttachedEventByName() {
    // Given
    runtimeService().startProcessInstanceByKey("Process_TestID");
    // When
    String end = find("2 days");
    // Then 
    assertThat(end).isEqualTo("n2Days_TestID");
  }

  @Test
  @Deployment(resources = "bpmn/ProcessEngineTests-findTest.bpmn")
  public void testFindGatewayByName() {
    // Given
    runtimeService().startProcessInstanceByKey("Process_TestID");
    // When
    String end = find("Continue?");
    // Then 
    assertThat(end).isEqualTo("Continue_TestID");
  }

  @Test
  @Deployment(resources = "bpmn/ProcessEngineTests-findTest.bpmn")
  public void testElementbyNameNotFound() {
    // Given
    runtimeService().startProcessInstanceByKey("Process_TestID");
    // When
    String end = find("This should not be found");
    // Then 
    assertThat(end).isEqualTo("Element with name 'This should not be found' doesn't exist");
  }

}
