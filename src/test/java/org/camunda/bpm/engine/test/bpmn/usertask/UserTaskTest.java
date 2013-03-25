/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camunda.bpm.engine.test.bpmn.usertask;

import java.util.List;

import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTestCase;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;

import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;

/**
 * @author Joram Barrez
 */
public class UserTaskTest extends FluentProcessEngineTestCase {
  
  @Deployment
  public void testTaskPropertiesNotNull() {
      newProcessInstance("oneTaskProcess").start();

      assertThat(processTask())
              .isNotNull()
              .hasDefinitionKey("theTask")
              .hasName("my task")
              .hasDescription("Very important")
              .isAssignedTo("kermit");

      assertThat(processTask().getPriority()).isGreaterThan(0);

      assertThat(processInstance().getId()).isEqualTo(processTask().getProcessInstanceId());
      assertThat(processInstance().getId()).isEqualTo(processTask().getExecutionId());

      assertThat(processTask().getProcessDefinitionId()).isNotNull();
      assertThat(processTask().getTaskDefinitionKey()).isNotNull();
      assertThat(processTask().getCreateTime()).isNotNull();

    // the next test verifies that if an execution creates a task, that no events are created during creation of the task.
// TODO: processEngineConfiguration is not available in the API so far
//    if (processEngineConfiguration.getHistoryLevel() >= ProcessEngineConfigurationImpl.HISTORYLEVEL_ACTIVITY) {
//      assertEquals(0, taskService.getTaskEvents(task.getId()).size());
//    }
  }
  
  @Deployment
  public void testQuerySortingWithParameter() {
      // TODO: getDelegate() does not sound very user friendly, maybe getProcessInstance()?
    ProcessInstance processInstance = newProcessInstance("oneTaskProcess").start().getDelegate();
      // TODO: what is the intent of this test?
    assertEquals(1, taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().size());
  }
  
  @Deployment
  public void testCompleteAfterParallelGateway() throws InterruptedException {
	  // related to http://jira.codehaus.org/browse/ACT-1054
	  
	  // start the process
    runtimeService.startProcessInstanceByKey("ForkProcess");
      // FIXME: how do we do assertions on more than one task, i.e. task lists?
    List<Task> taskList = taskService.createTaskQuery().list();
    assertNotNull(taskList);
    assertEquals(2, taskList.size());
	
    // make sure user task exists
    Task task = taskService.createTaskQuery().taskDefinitionKey("SimpleUser").singleResult();
  	assertNotNull(task);
	
  	// attempt to complete the task and get PersistenceException pointing to "referential integrity constraint violation"
  	taskService.complete(task.getId());
	}
}
