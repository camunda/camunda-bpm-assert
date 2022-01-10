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
package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.repository.CaseDefinition;
import org.camunda.bpm.engine.repository.CaseDefinitionQuery;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseExecutionQuery;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.runtime.CaseInstanceQuery;

import java.util.Map;

public interface WithCmmnAssertions {

  /**
   * @see CmmnAwareTests#assertThat(CaseInstance)
   */
  default CaseInstanceAssert assertThat(CaseInstance actual) {
    return CmmnAwareTests.assertThat(actual);
  }

  /**
   * @see CmmnAwareTests#assertThat(CaseExecution)
   */
  default CaseExecutionAssert assertThat(CaseExecution actual) {
    return CmmnAwareTests.assertThat(actual);
  }

  /**
   * @see CmmnAwareTests#assertThat(CaseDefinition)
   */
  default CaseDefinitionAssert assertThat(CaseDefinition actual) {
    return CmmnAwareTests.assertThat(actual);
  }

  /**
   * @see CmmnAwareTests#caseService()
   */
  default CaseService caseService() {
    return CmmnAwareTests.caseService();
  }

  /**
   * @see CmmnAwareTests#caseInstanceQuery()
   */
  default CaseInstanceQuery caseInstanceQuery() {
    return CmmnAwareTests.caseInstanceQuery();
  }

  /**
   * @see CmmnAwareTests#caseExecutionQuery()
   */
  default CaseExecutionQuery caseExecutionQuery() {
    return CmmnAwareTests.caseExecutionQuery();
  }

  /**
   * @see CmmnAwareTests#caseDefinitionQuery()
   */
  default CaseDefinitionQuery caseDefinitionQuery() {
    return CmmnAwareTests.caseDefinitionQuery();
  }

  /**
   * @see CmmnAwareTests#complete(CaseExecution)
   */
  default void complete(CaseExecution caseExecution) {
    CmmnAwareTests.complete(caseExecution);
  }

  /**
   * @see CmmnAwareTests#disable(CaseExecution)
   */
  default void disable(CaseExecution caseExecution) {
    CmmnAwareTests.disable(caseExecution);
  }

  /**
   * @see CmmnAwareTests#manuallyStart(CaseExecution)
   */
  default void manuallyStart(CaseExecution caseExecution) {
    CmmnAwareTests.manuallyStart(caseExecution);
  }

  /**
   * @see CmmnAwareTests#caseExecution(String, CaseInstance)
   */
  default CaseExecution caseExecution(String activityId, CaseInstance caseInstance) {
    return CmmnAwareTests.caseExecution(activityId, caseInstance);
  }

  /**
   * @see CmmnAwareTests#caseExecution(CaseExecutionQuery, CaseInstance)
   */
  default CaseExecution caseExecution(CaseExecutionQuery caseExecutionQuery, CaseInstance caseInstance) {
    return CmmnAwareTests.caseExecution(caseExecutionQuery, caseInstance);
  }

  /**
   * @see CmmnAwareTests#complete(CaseExecution, Map)
   */
  default void complete(CaseExecution caseExecution, Map<String, Object> variables) {
    CmmnAwareTests.complete(caseExecution, variables);
  }
}
