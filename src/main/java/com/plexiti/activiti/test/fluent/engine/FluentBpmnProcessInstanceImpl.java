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

import com.plexiti.activiti.test.fluent.FluentBpmnTests;
import com.plexiti.activiti.test.fluent.assertions.ExecutionAssert;
import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

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

    private ProcessInstance delegate;
    protected String processDefinitionId;
    protected Map<String, Object> processVariables = new HashMap<String, Object>();
    protected FluentBpmnTests.Move move = new FluentBpmnTests.Move() { public void along() {} };

    public FluentBpmnProcessInstanceImpl(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    protected FluentBpmnProcessInstanceImpl(ProcessInstance delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getProcessDefinitionId() {
        return getDelegate() != null ? getDelegate().getProcessDefinitionId() : processDefinitionId;
    }

    @Override
    public String getBusinessKey() {
        return getDelegate().getBusinessKey();
    }

    @Override
    public boolean isSuspended() {
        return getDelegate().isSuspended();
    }

    @Override
    public String getId() {
        return getDelegate().getId();
    }

    @Override
    public boolean isEnded() {
        return getDelegate().isEnded();
    }

    @Override
    public String getProcessInstanceId() {
        return getDelegate().getProcessInstanceId();
    }

    @Override
    public ProcessInstance getDelegate() {
        if (delegate == null)
            throw new IllegalStateException("Process instance not yet started. You cannot access that method before starting the processInstance instance.");
        return delegate;
    }

    @Override
    public FluentBpmnTask task() {
        List<Task> tasks = tasks();
        assertThat(tasks)
                .as("By calling processTask() you implicitly assumed that exactly one such object exists.")
                .hasSize(1);
        return new FluentBpmnTaskImpl(tasks.get(0));
    }

    @Override
    public List<Task> tasks() {
        return FluentBpmnLookups.createTaskQuery().list();
    }

    @Override
    public FluentBpmnJob job() {
        List<Job> jobs = jobs();
        assertThat(jobs)
                .as("By calling processJob() you implicitly assumed that exactly one such object exists.")
                .hasSize(1);
        return new FluentBpmnJobImpl(jobs.get(0));
    }

    @Override
    public List<Job> jobs() {
        return FluentBpmnLookups.createJobQuery().list();
    }

    @Override
    public DiagramLayout diagramLayout() {
        DiagramLayout diagramLayout = FluentBpmnLookups.getRepositoryService().getProcessDiagramLayout(getDelegate().getProcessDefinitionId());
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
                .as("By calling processExecution() you implicitly assumed that at most one such object exists.")
                .hasSize(1);
        return executions.get(0);
    }

    @Override
    public List<Execution> executions() {
        return FluentBpmnLookups.createExecutionQuery().processInstanceId(getDelegate().getId()).list();
    }

    public void moveAlong(FluentBpmnTests.Move move) {
        this.move = move;
    }

    @Override
    public void startAndMoveTo(String activity) {
        try {
            ExecutionAssert.setMoveToActivityId(activity);
            move.along();
        } catch (ExecutionAssert.MoveToActivityIdException e) {
        }
    }

    @Override
    public FluentBpmnProcessInstance start() {
        delegate = FluentBpmnLookups.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionId, processVariables);
        log.info("Started processInstance '" + processDefinitionId + "' (definition id: '" + getDelegate().getProcessDefinitionId() + "', instance id: '" + getDelegate().getId() + "').");
        return this;
    }

    @Override
    public FluentBpmnProcessInstanceImpl withVariable(String name, Object value) {
        assertThat(delegate).overridingErrorMessage("Process already started. Call start() after having set up all necessary processInstance variables.").isNull();
        this.processVariables.put(name, value);
        return this;
    }

    @Override
    public FluentBpmnProcessInstance withVariables(Map<String, Object> variables) {
        assertThat(delegate).overridingErrorMessage("Process already started. Call start() after having set up all necessary processInstance variables.").isNull();
        for (String name: variables.keySet()) {
            this.processVariables.put(name, variables.get(name));
        }

        return this;
    }

    @Override
    public FluentBpmnProcessVariable variable(String variableName) {
        Object variableValue = FluentBpmnLookups.getRuntimeService().getVariable(getDelegate().getId(), variableName);

        assertThat(variableValue)
                .overridingErrorMessage("Unable to find processInstance processVariable '%s'", variableName)
                .isNotNull();
        return new FluentBpmnProcessVariable(variableName, variableValue);
    }

}
