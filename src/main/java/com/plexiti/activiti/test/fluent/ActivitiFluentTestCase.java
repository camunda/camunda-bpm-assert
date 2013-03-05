/*
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.plexiti.activiti.test.fluent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.plexiti.activiti.test.fluent.assertions.ProcessDefinitionAssert;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiTestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

import com.plexiti.activiti.test.fluent.mocking.Mockitos;

import static org.fest.assertions.api.Assertions.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public abstract class ActivitiFluentTestCase extends ActivitiTestCase {

    private Map<String, TestProcessInstance> processes = new HashMap<String, TestProcessInstance>();

    private ActivitiFluentTestRule activitiFluentTestRule = new ActivitiFluentTestRule(this);

    @Override @Before
    protected void setUp() throws Exception {
        super.setUp();
        activitiFluentTestRule.before();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    protected TestProcessInstance start(TestProcessInstance testProcess) {
        if (processes.containsKey(testProcess.processDefinitionKey)) {
            return processes.get(testProcess.processDefinitionKey);
        } else {
            processes.put(testProcess.processDefinitionKey, testProcess);
            testProcess.start();
            return testProcess;
        }
    }

    protected TestProcessInstance process(String processDefinitionKey) {
        return processes.get(processDefinitionKey);
    }

    protected TestProcessInstance process() {
        assertThat(processes).hasSize(1);
        return processes.values().iterator().next();
    }

    protected ProcessDefinition processDefinition(String processDefinitionName) {
        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery()
                .processDefinitionName(processDefinitionName).list();

        assertThat(definitions)
            .overridingErrorMessage("Unable to find a deployed process definition with name '%s'", processDefinitionName)
            .hasSize(1);

        return definitions.get(0);
    }

    protected void startProcessInstanceByKey(String processKey, Map<String, Object> processVariables) {
        // TODO: Assert that a process definition with that key is already deployed
        TestProcessInstance testInstance = new TestProcessInstance(processKey);
        testInstance.withVariables(processVariables);
        testInstance.start();
        processes.put(processKey, testInstance);
    }

    protected Task findTaskByTaskId(String taskId) {
        ProcessInstance pi = process().getActualProcessInstance();
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
        assertThat(tasks.size())
                .overridingErrorMessage("Unable to find a task with id '%s'", taskId)
                .isEqualTo(1);
        return tasks.get(0);
    }

}
