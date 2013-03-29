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

import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessInstanceAssert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentProcessInstanceImpl extends AbstractFluentDelegate<ProcessInstance> implements FluentProcessInstance {

    private static Logger log = Logger.getLogger(FluentProcessInstanceImpl.class.getName());

    protected String processDefinitionKey;
    protected Map<String, Object> processVariables = new HashMap<String, Object>();
    protected FluentProcessEngineTests.Move move = new FluentProcessEngineTests.Move() { public void along() {} };

    public FluentProcessInstanceImpl(FluentProcessEngine engine, String processDefinitionKey) {
        super(engine, null);
        this.processDefinitionKey = processDefinitionKey;
        boolean processDefinitionKeyExists = !engine.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).list().isEmpty();
        if (!processDefinitionKeyExists)
            throw new IllegalArgumentException("Process Definition with processDefinitionKey '" + processDefinitionKey + "' is not deployed.");
    }

    @Override
    public String getProcessDefinitionId() {
        return delegate != null ? delegate.getProcessDefinitionId() : null;
    }

    @Override
    public String getBusinessKey() {
        return delegate.getBusinessKey();
    }

    @Override
    public boolean isSuspended() {
        return delegate.isSuspended();
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public boolean isEnded() {
        return engine.getRuntimeService().createExecutionQuery().processInstanceId(delegate.getId()).list().isEmpty() || delegate.isEnded();
    }

    @Override
    public String getProcessInstanceId() {
        return delegate.getProcessInstanceId();
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
        return new FluentTaskImpl(engine, tasks.get(0));
    }

    @Override
    public List<Task> tasks() {
        return engine.getTaskService().createTaskQuery().list();
    }

    @Override
    public FluentJob job() {
        List<Job> jobs = jobs();
        assertThat(jobs)
                .as("By calling processJob() you implicitly assumed that exactly one such object exists.")
                .hasSize(1);
        return new FluentJobImpl(engine, jobs.get(0));
    }

    @Override
    public List<Job> jobs() {
        return engine.getManagementService().createJobQuery().list();
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
        this.delegate = engine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey, processVariables);
        log.info("Started processInstance (definition key '" + processDefinitionKey + "', definition id: '" + delegate.getProcessDefinitionId() + "', instance id: '" + delegate.getId() + "').");
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
        Object variableValue = engine.getRuntimeService().getVariable(delegate.getId(), variableName);

        assertThat(variableValue)
                .overridingErrorMessage("Unable to find processInstance processVariable '%s'", variableName)
                .isNotNull();
        return new FluentProcessVariable(variableName, variableValue);
    }

}
