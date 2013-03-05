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
public class FluentBpmnProcessInstanceImpl implements FluentBpmnProcessInstance {

    private static Logger log = Logger.getLogger(FluentBpmnProcessInstanceImpl.class.getName());

    protected String processDefinitionId;
    protected ProcessInstance delegate;
    protected Map<String, Object> processVariables = new HashMap<String, Object>();

    @Override
    public String getProcessDefinitionId() {
        return delegate != null ? delegate.getProcessDefinitionId() : processDefinitionId;
    }

    @Override
    public String getBusinessKey() {
        checkDelegate();
        return delegate.getBusinessKey();
    }

    @Override
    public boolean isSuspended() {
        checkDelegate();
        return delegate.isSuspended();
    }

    @Override
    public String getId() {
        checkDelegate();
        return delegate.getId();
    }

    @Override
    public boolean isEnded() {
        checkDelegate();
        return delegate.isEnded();
    }

    @Override
    public String getProcessInstanceId() {
        checkDelegate();
        return delegate.getProcessInstanceId();
    }

    private void checkDelegate() {
        if (delegate == null)
            throw new IllegalStateException("Process instance not yet started. You cannot access that method before starting the process instance.");
    }

    public FluentBpmnProcessInstanceImpl(String processDefinitionKey) {
        this.processDefinitionId = processDefinitionKey;
    }

    protected FluentBpmnProcessInstanceImpl(ProcessInstance delegate) {
        this.delegate = delegate;
    }

    @Override
    public ProcessInstance getDelegate() {
        return delegate;
    }

    protected ExecutionQuery activitiExecutionQuery() {
        return FluentBpmnLookups.getRuntimeService().createExecutionQuery();
    }

    protected TaskQuery activitiTaskQuery() {
        return FluentBpmnLookups.getTaskService().createTaskQuery();
    }

    @Override
    public FluentBpmnTask task() {
        List<Task> tasks = tasks();
        assertThat(tasks)
                .as("By calling task() you implicitly assumed that exactly one such object exists.")
                .hasSize(1);
        return new FluentBpmnTaskImpl(tasks.get(0));
    }

    @Override
    public List<Task> tasks() {
        return activitiTaskQuery().list();
    }

    @Override
    public DiagramLayout diagramLayout() {
        DiagramLayout diagramLayout = FluentBpmnLookups.getRepositoryService().getProcessDiagramLayout(processInstance().getProcessDefinitionId());
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
        assertThat(delegate)
                .overridingErrorMessage("No process instantiated yet. Call start(process) first!")
                .isNotNull();
        return delegate;
    }

    @Override
    public FluentBpmnProcessInstance start() {
        delegate = FluentBpmnLookups.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionId, processVariables);
        log.info("Started process '" + processDefinitionId + "' (definition id: '" + delegate.getProcessDefinitionId() + "', instance id: '" + delegate.getId() + "').");
        return this;
    }

    @Override
    public FluentBpmnProcessInstanceImpl withVariable(String name, Object value) {
        assertThat(delegate).overridingErrorMessage("Process already started. Call start() after having set up all necessary process variables.").isNull();
        this.processVariables.put(name, value);
        return this;
    }

    @Override
    public FluentBpmnProcessInstance withVariables(Map<String, Object> variables) {
        assertThat(delegate).overridingErrorMessage("Process already started. Call start() after having set up all necessary process variables.").isNull();
        for (String name: variables.keySet()) {
            this.processVariables.put(name, variables.get(name));
        }

        return this;
    }

    @Override
    public FluentBpmnProcessVariable variable(String variableName) {
        Object variableValue = FluentBpmnLookups.getRuntimeService().getVariable(delegate.getId(), variableName);

        assertThat(variableValue)
                .overridingErrorMessage("Unable to find process variable '%s'", variableName)
                .isNotNull();
        return new FluentBpmnProcessVariable(variableName, variableValue);
    }

    public static class ActivitiTargetActivityReached extends RuntimeException {
        private static final long serialVersionUID = 2282185191899085294L;
    }
}
