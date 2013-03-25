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
package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessInstanceAssert;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Martin Schimak <martin.schimak@camunda.com>
 * @author Rafael Cordones <rafael.cordones@camunda.com>
 */
public class FluentProcessInstanceImpl implements FluentProcessInstance {

    private static Logger log = Logger.getLogger(FluentProcessInstanceImpl.class.getName());

    private ProcessInstance delegate;
    protected String processDefinitionId;
    protected Map<String, Object> processVariables = new HashMap<String, Object>();
    protected FluentProcessEngineTests.Move move = new FluentProcessEngineTests.Move() { public void along() {} };

    public FluentProcessInstanceImpl(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
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
        return FluentLookups.createExecutionQuery().processInstanceId(getDelegate().getId()).list().isEmpty() || getDelegate().isEnded();
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
    public FluentTask task() {
        List<Task> tasks = tasks();
        assertThat(tasks)
                .as("By calling processTask() you implicitly assumed that exactly one such object exists.")
                .hasSize(1);
        return new FluentTaskImpl(tasks.get(0));
    }

    @Override
    public List<Task> tasks() {
        return FluentLookups.createTaskQuery().list();
    }

    @Override
    public FluentJob job() {
        List<Job> jobs = jobs();
        assertThat(jobs)
                .as("By calling processJob() you implicitly assumed that exactly one such object exists.")
                .hasSize(1);
        return new FluentJobImpl(jobs.get(0));
    }

    @Override
    public List<Job> jobs() {
        return FluentLookups.createJobQuery().list();
    }

    public void moveAlong(FluentProcessEngineTests.Move move) {
        this.move = move;
    }

    @Override
    public FluentProcessInstance startAndMoveTo(String activity) {
        try {
            ProcessInstanceAssert.setMoveToActivityId(activity);
            move.along();
        } catch (ProcessInstanceAssert.MoveToActivityIdException e) {
        }
        return this;
    }

    @Override
    public FluentProcessInstance start() {
        delegate = FluentLookups.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionId, processVariables);
        log.info("Started processInstance '" + processDefinitionId + "' (definition id: '" + getDelegate().getProcessDefinitionId() + "', instance id: '" + getDelegate().getId() + "').");
        return this;
    }

    @Override
    public FluentProcessInstanceImpl withVariable(String name, Object value) {
        assertThat(delegate).overridingErrorMessage("Process already started. Call start() after having set up all necessary processInstance variables.").isNull();
        this.processVariables.put(name, value);
        return this;
    }

    @Override
    public FluentProcessInstance withVariables(Map<String, Object> variables) {
        assertThat(delegate).overridingErrorMessage("Process already started. Call start() after having set up all necessary processInstance variables.").isNull();
        for (String name: variables.keySet()) {
            this.processVariables.put(name, variables.get(name));
        }

        return this;
    }

    @Override
    public FluentProcessVariable variable(String variableName) {
        Object variableValue = FluentLookups.getRuntimeService().getVariable(getDelegate().getId(), variableName);

        assertThat(variableValue)
                .overridingErrorMessage("Unable to find processInstance processVariable '%s'", variableName)
                .isNotNull();
        return new FluentProcessVariable(variableName, variableValue);
    }

}
