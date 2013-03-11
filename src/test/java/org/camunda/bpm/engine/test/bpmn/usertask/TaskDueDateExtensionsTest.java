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

import java.text.SimpleDateFormat;
import java.util.Date;

import com.plexiti.activiti.test.fluent.FluentProcessEngineTestCase;
import org.camunda.bpm.engine.test.Deployment;
import static com.plexiti.activiti.test.fluent.FluentProcessEngineTests.*;

/**
 * @author Frederik Heremans
 */
public class TaskDueDateExtensionsTest extends FluentProcessEngineTestCase {

  @Deployment
  public void testDueDateExtension() throws Exception {

    Date date = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse("06-07-1986 12:10:00");

    // Start process-instance, passing date that should be used as dueDate as a Date
    newProcessInstance("dueDateExtension")
            .withVariable("dateVariable", date)
            .start();

    /*
     * The intent of this code was to retrieve the current task and assert that the task has the correct due date
     *
     * Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
     * assertNotNull(task.getDueDate());
     * assertEquals(date, task.getDueDate());
     */
    assertThat(processInstance()).isWaitingAt("theTask");
      // FIME: I would actually prefer currentTask() here instead oc processTask()
    assertThat(processTask()).hasDueDate(date);
  }

  @Deployment
  public void testDueDateStringExtension() throws Exception {

    // Start process-instance, passing date that should be used as dueDate as a String
    newProcessInstance("dueDateExtension")
        .withVariable("dateVariable", "1986-07-06T12:10:00")
        .start();

    assertThat(processInstance()).isWaitingAt("theTask");
    // alternative way of testing the same
    assertThat(processTask()).hasDefinitionKey("theTask");
    /*
     * Please note that Task.getId() returns the database id of the task and not the
     * id of the <usertask id='xxx'> element in that process definition file!
     */
    //assertThat(processTask().getId()).isEqualTo("theTask");
    assertThat(processTask().getName()).isEqualTo("my task");

    Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("06-07-1986 12:10:00");
    // FIXME: I would actually prefer currentTask() here instead oc processTask()
    assertThat(processTask().getDueDate()).isEqualTo(date);
  }
}
