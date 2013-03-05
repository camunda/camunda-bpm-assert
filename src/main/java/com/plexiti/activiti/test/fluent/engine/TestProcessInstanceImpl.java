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
package com.plexiti.activiti.test.fluent.engine;

import com.plexiti.activiti.test.fluent.assertions.ExecutionAssert;
import com.plexiti.activiti.test.fluent.support.Maps;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.fest.assertions.api.Assertions.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class TestProcessInstanceImpl implements TestProcessInstance {

    private static Logger log = Logger.getLogger(TestProcessInstanceImpl.class.getName());

    protected static final String ActivitiTargetActivity = "ActivitiTargetActivity";

    protected String processDefinitionKey;

    /*
     * Reference to the Activiti process instance
     */
    protected ProcessInstance actualProcessInstance;
    protected Map<String, Object> processVariables = new HashMap<String, Object>();
    //protected TaskService taskService = ActivitiRuleHelper.get().getTaskService();

    public TestProcessInstanceImpl(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    @Override
    public List<String> activeActivities(Execution execution) {
        return TestLookups.getRuntimeService().getActiveActivityIds(execution.getId());
    }

    @Override
    public ProcessInstance getActualProcessInstance() {
        return actualProcessInstance;
    }

    @Override
    public String activeActivity(Execution execution) {
        List<String> activeActivities = activeActivities(execution);
        assertThat(activeActivities)
                .as("By calling execution() you implicitly assumed that exactly one such object exists.")
                .hasSize(1);
        return activeActivities.get(0);
    }

    protected ExecutionQuery activitiExecutionQuery() {
        return TestLookups.getRuntimeService().createExecutionQuery();
    }

    protected GroupQuery activitiGroupQuery() {
        return TestLookups.getIdentityService().createGroupQuery();
    }

    protected TaskQuery activitiTaskQuery() {
        return TestLookups.getTaskService().createTaskQuery();
    }

    protected UserQuery activitiUserQuery() {
        return TestLookups.getIdentityService().createUserQuery();
    }

    @Override
    public void claim(Task task, String userId) {
        TestLookups.getTaskService().claim(currentTask().getId(), userId);
    }

    @Override
    public void claim(Task task, User user) {
        claim(currentTask(), user.getId());
    }

    @Override
    public void complete(Task task, Object... variables) {
        TestLookups.getTaskService().complete(task.getId(), Maps.parseMap(variables));
    }

    @Override
    public Task currentTask() {
        List<Task> tasks = currentTasks();
        assertThat(tasks)
                .as("By calling task() you implicitly assumed that exactly one such object exists.")
                .hasSize(1);
        return tasks.get(0);
    }

    @Override
    public List<Task> currentTasks() {
        return activitiTaskQuery().list();
    }

    @Override
    public DiagramLayout diagramLayout() {
        DiagramLayout diagramLayout = TestLookups.getRepositoryService().getProcessDiagramLayout(processInstance().getProcessDefinitionId());
        assertThat(diagramLayout)
                .overridingErrorMessage("Fatal error. Could not retrieve diagram layout!")
                .isNotNull();
        return diagramLayout;
    }

    @Override
    public Execution execution() {
        List<Execution> executions = executions();
        if (executions.size() == 0)
            return null;
        assertThat(executions)
                .as("By calling execution() you implicitly assumed that at most one such object exists.")
                .hasSize(1);
        return executions.get(0);
    }

    @Override
    public List<Execution> executions() {
        return activitiExecutionQuery().processInstanceId(processInstance().getId()).list();
    }

    @Override
    public void moveAlong() {
    }

    @Override
    public void moveTo(String targetActivity) {
        try {
            ExecutionAssert.setMoveToActivityId(targetActivity);
            moveAlong();
        } catch (ActivitiTargetActivityReached e) {
        }
    }

    protected ProcessInstance processInstance() {
        assertThat(actualProcessInstance)
                .overridingErrorMessage("No process instantiated yet. Call start(process) first!")
                .isNotNull();
        return actualProcessInstance;
    }

    @Override
    public TestProcessInstance start() {
        // processVariables.put(ActivitiTargetActivity, null);
        actualProcessInstance = TestLookups.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey, processVariables);
        log.info("Started process '" + processDefinitionKey + "' (definition id: '" + actualProcessInstance.getProcessDefinitionId() + "', instance id: '" + actualProcessInstance.getId() + "').");
        return this;
    }

    @Override
    public TestProcessInstanceImpl withVariable(String name, Object value) {
        assertThat(actualProcessInstance).overridingErrorMessage("Process already started. Call start() after having set up all necessary process variables.").isNull();
        this.processVariables.put(name, value);
        return this;
    }

    @Override
    public TestProcessInstance withVariables(Map<String, Object> variables) {
        assertThat(actualProcessInstance).overridingErrorMessage("Process already started. Call start() after having set up all necessary process variables.").isNull();
        for (String name: variables.keySet()) {
            this.processVariables.put(name, variables.get(name));
        }

        return this;
    }

    @Override
    public TestProcessVariable variable(String variableName) {
        Object variableValue = TestLookups.getRuntimeService().getVariable(actualProcessInstance.getId(), variableName);

        assertThat(variableValue)
                .overridingErrorMessage("Unable to find process variable '%s'", variableName)
                .isNotNull();
        return new TestProcessVariable(variableName, variableValue);
    }

    public static class ActivitiTargetActivityReached extends RuntimeException {
        private static final long serialVersionUID = 2282185191899085294L;
    }
}
