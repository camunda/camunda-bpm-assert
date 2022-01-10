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

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.externaltask.ExternalTask;
import org.camunda.bpm.engine.externaltask.ExternalTaskQuery;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.*;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;

import java.util.List;
import java.util.Map;

public interface WithBpmnAssertions {

  /**
   * @see AbstractAssertions#processEngine()
   */
  default ProcessEngine processEngine() {
    return AbstractAssertions.processEngine();
  }

  /**
   * @see BpmnAwareTests#assertThat(ProcessDefinition)
   */
  default ProcessDefinitionAssert assertThat(ProcessDefinition actual) {
    return BpmnAwareTests.assertThat(actual);
  }

  /**
   * @see BpmnAwareTests#assertThat(ProcessInstance)
   */
  default ProcessInstanceAssert assertThat(ProcessInstance actual) {
    return BpmnAwareTests.assertThat(actual);
  }

  /**
   * @see BpmnAwareTests#assertThat(Task)
   */
  default TaskAssert assertThat(Task actual) {
    return BpmnAwareTests.assertThat(actual);
  }

  /**
   * @see BpmnAwareTests#assertThat(ExternalTask)
   */
  default ExternalTaskAssert assertThat(ExternalTask actual) {
    return BpmnAwareTests.assertThat(actual);
  }

  /**
   * @see BpmnAwareTests#assertThat(Job)
   */
  default JobAssert assertThat(Job actual) {
    return BpmnAwareTests.assertThat(actual);
  }

  /**
   * @see BpmnAwareTests#runtimeService()
   */
  default RuntimeService runtimeService() {
    return BpmnAwareTests.runtimeService();
  }

  /**
   * @see BpmnAwareTests#authorizationService()
   */
  default AuthorizationService authorizationService() {
    return BpmnAwareTests.authorizationService();
  }

  /**
   * @see BpmnAwareTests#formService()
   */
  default FormService formService() {
    return BpmnAwareTests.formService();
  }

  /**
   * @see BpmnAwareTests#historyService()
   */
  default HistoryService historyService() {
    return BpmnAwareTests.historyService();
  }

  /**
   * @see BpmnAwareTests#identityService()
   */
  default IdentityService identityService() {
    return BpmnAwareTests.identityService();
  }

  /**
   * @see BpmnAwareTests#managementService()
   */
  default ManagementService managementService() {
    return BpmnAwareTests.managementService();
  }

  /**
   * @see BpmnAwareTests#repositoryService()
   */
  default RepositoryService repositoryService() {
    return BpmnAwareTests.repositoryService();
  }

  /**
   * @see BpmnAwareTests#taskService()
   */
  default TaskService taskService() {
    return BpmnAwareTests.taskService();
  }

  /**
   * @see BpmnAwareTests#externalTaskService()
   */
  default ExternalTaskService externalTaskService() {
    return BpmnAwareTests.externalTaskService();
  }

  /**
   * @see BpmnAwareTests#decisionService()
   */
  default DecisionService decisionService() {
    return BpmnAwareTests.decisionService();
  }

  /**
   * @see BpmnAwareTests#taskQuery()
   */
  default TaskQuery taskQuery() {
    return BpmnAwareTests.taskQuery();
  }

  /**
   * @see BpmnAwareTests#externalTaskQuery()
   */
  default ExternalTaskQuery externalTaskQuery() {
    return BpmnAwareTests.externalTaskQuery();
  }

  /**
   * @see BpmnAwareTests#jobQuery()
   */
  default JobQuery jobQuery() {
    return BpmnAwareTests.jobQuery();
  }

  /**
   * @see BpmnAwareTests#processInstanceQuery()
   */
  default ProcessInstanceQuery processInstanceQuery() {
    return BpmnAwareTests.processInstanceQuery();
  }

  /**
   * @see BpmnAwareTests#processDefinitionQuery()
   */
  default ProcessDefinitionQuery processDefinitionQuery() {
    return BpmnAwareTests.processDefinitionQuery();
  }

  /**
   * @see BpmnAwareTests#executionQuery()
   */
  default ExecutionQuery executionQuery() {
    return BpmnAwareTests.executionQuery();
  }

  /**
   * @see BpmnAwareTests#withVariables(String, Object, Object...)
   */
  default Map<String, Object> withVariables(String key, Object value, Object... furtherKeyValuePairs) {
    return BpmnAwareTests.withVariables(key, value, furtherKeyValuePairs);
  }

  /**
   * @see BpmnAwareTests#task()
   */
  default Task task() {
    return BpmnAwareTests.task();
  }

  /**
   * @see BpmnAwareTests#task(ProcessInstance)
   */
  default Task task(ProcessInstance processInstance) {
    return BpmnAwareTests.task(processInstance);
  }

  /**
   * @see BpmnAwareTests#task(String)
   */
  default Task task(String taskDefinitionKey) {
    return BpmnAwareTests.task(taskDefinitionKey);
  }

  /**
   * @see BpmnAwareTests#task(String, ProcessInstance)
   */
  default Task task(String taskDefinitionKey, ProcessInstance processInstance) {
    return BpmnAwareTests.task(taskDefinitionKey, processInstance);
  }

  /**
   * @see BpmnAwareTests#task(TaskQuery)
   */
  default Task task(TaskQuery taskQuery) {
    return BpmnAwareTests.task(taskQuery);
  }

  /**
   * @see BpmnAwareTests#task(TaskQuery, ProcessInstance)
   */
  default Task task(TaskQuery taskQuery, ProcessInstance processInstance) {
    return BpmnAwareTests.task(taskQuery, processInstance);
  }

  /**
   * @see BpmnAwareTests#externalTask()
   */
  default ExternalTask externalTask() {
    return BpmnAwareTests.externalTask();
  }

  /**
   * @see BpmnAwareTests#externalTask(ProcessInstance)
   */
  default ExternalTask externalTask(ProcessInstance processInstance) {
    return BpmnAwareTests.externalTask(processInstance);
  }

  /**
   * @see BpmnAwareTests#externalTask(String)
   */
  default ExternalTask externalTask(String activityId) {
    return BpmnAwareTests.externalTask(activityId);
  }

  /**
   * @see BpmnAwareTests#externalTask(String, ProcessInstance)
   */
  default ExternalTask externalTask(String activityId, ProcessInstance processInstance) {
    return BpmnAwareTests.externalTask(activityId, processInstance);
  }

  /**
   * @see BpmnAwareTests#externalTask(ExternalTaskQuery)
   */
  default ExternalTask externalTask(ExternalTaskQuery externalTaskQuery) {
    return BpmnAwareTests.externalTask(externalTaskQuery);
  }

  /**
   * @see BpmnAwareTests#externalTask(ExternalTaskQuery, ProcessInstance)
   */
  default ExternalTask externalTask(ExternalTaskQuery externalTaskQuery, ProcessInstance processInstance) {
    return BpmnAwareTests.externalTask(externalTaskQuery, processInstance);
  }

  /**
   * @see BpmnAwareTests#processDefinition()
   */
  default ProcessDefinition processDefinition() {
    return BpmnAwareTests.processDefinition();
  }

  /**
   * @see BpmnAwareTests#processDefinition(ProcessInstance)
   */
  default ProcessDefinition processDefinition(ProcessInstance processInstance) {
    return BpmnAwareTests.processDefinition(processInstance);
  }

  /**
   * @see BpmnAwareTests#processDefinition(String)
   */
  default ProcessDefinition processDefinition(String processDefinitionKey) {
    return BpmnAwareTests.processDefinition(processDefinitionKey);
  }

  /**
   * @see BpmnAwareTests#processDefinition(ProcessDefinitionQuery)
   */
  default ProcessDefinition processDefinition(ProcessDefinitionQuery processDefinitionQuery) {
    return BpmnAwareTests.processDefinition(processDefinitionQuery);
  }

  /**
   * @see BpmnAwareTests#calledProcessInstance()
   */
  default ProcessInstance calledProcessInstance() {
    return BpmnAwareTests.calledProcessInstance();
  }

  /**
   * @see BpmnAwareTests#calledProcessInstance(ProcessInstance)
   */
  default ProcessInstance calledProcessInstance(ProcessInstance processInstance) {
    return BpmnAwareTests.calledProcessInstance(processInstance);
  }

  /**
   * @see BpmnAwareTests#calledProcessInstance(String)
   */
  default ProcessInstance calledProcessInstance(String processDefinitionKey) {
    return BpmnAwareTests.calledProcessInstance(processDefinitionKey);
  }

  /**
   * @see BpmnAwareTests#calledProcessInstance(String, ProcessInstance)
   */
  default ProcessInstance calledProcessInstance(String processDefinitionKey, ProcessInstance processInstance) {
    return BpmnAwareTests.calledProcessInstance(processDefinitionKey, processInstance);
  }

  /**
   * @see BpmnAwareTests#calledProcessInstance(ProcessInstanceQuery)
   */
  default ProcessInstance calledProcessInstance(ProcessInstanceQuery processInstanceQuery) {
    return BpmnAwareTests.calledProcessInstance(processInstanceQuery);
  }

  /**
   * @see BpmnAwareTests#calledProcessInstance(ProcessInstanceQuery, ProcessInstance)
   */
  default ProcessInstance calledProcessInstance(ProcessInstanceQuery processInstanceQuery, ProcessInstance processInstance) {
    return BpmnAwareTests.calledProcessInstance(processInstanceQuery, processInstance);
  }

  /**
   * @see BpmnAwareTests#job()
   */
  default Job job() {
    return BpmnAwareTests.job();
  }

  /**
   * @see BpmnAwareTests#job(ProcessInstance)
   */
  default Job job(ProcessInstance processInstance) {
    return BpmnAwareTests.job(processInstance);
  }

  /**
   * @see BpmnAwareTests#job(String)
   */
  default Job job(String activityId) {
    return BpmnAwareTests.job(activityId);
  }

  /**
   * @see BpmnAwareTests#job(String, ProcessInstance)
   */
  default Job job(String activityId, ProcessInstance processInstance) {
    return BpmnAwareTests.job(activityId, processInstance);
  }

  /**
   * @see BpmnAwareTests#job(JobQuery)
   */
  default Job job(JobQuery jobQuery) {
    return BpmnAwareTests.job(jobQuery);
  }

  /**
   * @see BpmnAwareTests#job(JobQuery, ProcessInstance)
   */
  default Job job(JobQuery jobQuery, ProcessInstance processInstance) {
    return BpmnAwareTests.job(jobQuery, processInstance);
  }

  /**
   * @see BpmnAwareTests#claim(Task, String)
   */
  default Task claim(Task task, String assigneeUserId) {
    return BpmnAwareTests.claim(task, assigneeUserId);
  }

  /**
   * @see BpmnAwareTests#unclaim(Task)
   */
  default Task unclaim(Task task) {
    return BpmnAwareTests.unclaim(task);
  }

  /**
   * @see BpmnAwareTests#complete(Task, Map)
   */
  default void complete(Task task, Map<String, Object> variables) {
    BpmnAwareTests.complete(task, variables);
  }

  /**
   * @see BpmnAwareTests#complete(Task)
   */
  default void complete(Task task) {
    BpmnAwareTests.complete(task);
  }

  /**
   * @see BpmnAwareTests#complete(ExternalTask)
   */
  default void complete(ExternalTask externalTask) {
    BpmnAwareTests.complete(externalTask);
  }

  /**
   * @see BpmnAwareTests#complete(ExternalTask, Map)
   */
  default void complete(ExternalTask externalTask, Map<String, Object> variables) {
    BpmnAwareTests.complete(externalTask, variables);
  }

  /**
   * @see BpmnAwareTests#fetchAndLock(String, String, int)
   */
  default List<LockedExternalTask> fetchAndLock(String topic, String workerId, int maxResults) {
    return BpmnAwareTests.fetchAndLock(topic, workerId, maxResults);
  }

  /**
   * @see BpmnAwareTests#complete(LockedExternalTask)
   */
  default void complete(LockedExternalTask lockedExternalTask) {
    BpmnAwareTests.complete(lockedExternalTask);
  }

  /**
   * @see BpmnAwareTests#complete(ExternalTask, Map)
   */
  default void complete(LockedExternalTask lockedExternalTask, Map<String, Object> variables) {
    BpmnAwareTests.complete(lockedExternalTask, variables);
  }

  /**
   * @see BpmnAwareTests#execute(Job)
   */
  default void execute(Job job) {
    BpmnAwareTests.execute(job);
  }

  /**
   * @see BpmnAwareTests#findId(String)
   */
  default String findId(String name) {
    return BpmnAwareTests.findId(name);
  }
}
