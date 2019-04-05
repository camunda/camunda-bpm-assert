/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH 
 * under one or more contributor license agreements. See the NOTICE file 
 * distributed with this work for additional information regarding copyright 
 * ownership.
 *  
 * Camunda licenses this file to you under the Apache License, Version 2.0; 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package org.camunda.bpm.engine.test.assertions.examples;

import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Sanity test to verify the example process is syntactical correct and can be
 * deployed to the engine. All "real" tests should be implemented in the
 * according modules (jbehave, assertions, needle, ...).
 */
public class ReceiveInvoiceProcessTest {

  @Rule
  public final ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = "camunda-testing-invoice-en.bpmn")
  public void shouldDeployWithoutErrors() throws Exception {
    // nothing here, test successful if deployment works
  }
}
