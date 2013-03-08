package com.plexiti.activiti.test.fluent;

import com.plexiti.activiti.test.fluent.assertions.ProcessDefinitionAssert;
import com.plexiti.activiti.test.fluent.assertions.ProcessInstanceAssert;
import com.plexiti.activiti.test.fluent.assertions.ProcessVariableAssert;
import com.plexiti.activiti.test.fluent.assertions.TaskAssert;
import com.plexiti.activiti.test.fluent.engine.*;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.Task;

/**
 * Convenience class to access all fluent Activiti assertions.
 *
 * In your code use
 *
 *    import static com.plexiti.activiti.test.fluent.FluentProcessEngineTests.*;
 *
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentProcessEngineTests extends org.fest.assertions.api.Assertions {

    public static interface Move { void along(); }

    public static FluentProcessInstance newProcessInstance(String processDefinitionId, Move move) {
        FluentProcessInstanceImpl fluentBpmnProcessInstance = (FluentProcessInstanceImpl) FluentProcessInstanceLookup.newProcessInstance(processDefinitionId);
        fluentBpmnProcessInstance.moveAlong(move);
        return fluentBpmnProcessInstance;
    }

    /**
     * Creates a new {@link FluentProcessInstance} which provides fluent methods to declare
     * process variables before actually starting a process instance.
     *
     * @param processDefinitionId the value of the "id" attribute in the process definition BPMN 2.0 XML file
     * @return a {@link FluentProcessInstance} that can be further configured before starting the process instance
     *
     * @see com.plexiti.activiti.test.fluent.engine.FluentProcessInstance#withVariable(String, Object)
     * @see com.plexiti.activiti.test.fluent.engine.FluentProcessInstance#withVariables(java.util.Map)
     * @see com.plexiti.activiti.test.fluent.engine.FluentProcessInstance#start()
     */
    public static FluentProcessInstance newProcessInstance(String processDefinitionId) {
        return FluentProcessInstanceLookup.newProcessInstance(processDefinitionId);
    }

    public static FluentProcessInstance processInstance() {
        return FluentProcessInstanceLookup.processInstance();
    }

    public static FluentProcessVariable processVariable(String id) {
        return FluentProcessInstanceLookup.processInstance().variable(id);
    }

    public static FluentTask processTask() {
        return FluentProcessInstanceLookup.processInstance().task();
    }

    public static FluentJob processJob() {
        return FluentProcessInstanceLookup.processInstance().job();
    }

    // TODO From here on more "Fluent" interfaces and wrappers have to be implemented

    public static ProcessDefinition processDefinition(String processDefinitionId) {
        return FluentProcessInstanceLookup.processDefinition(processDefinitionId);
    }

    // Assertions

    public static TaskAssert assertThat(Task actual) {
        return TaskAssert.assertThat(actual);
    }

    public static ProcessDefinitionAssert assertThat(ProcessDefinition actual) {
        return ProcessDefinitionAssert.assertThat(actual);
    }

    public static ProcessInstanceAssert assertThat(FluentProcessInstance actual) {
        return ProcessInstanceAssert.assertThat(actual);
    }

    public static ProcessVariableAssert assertThat(FluentProcessVariable actual) {
        return ProcessVariableAssert.assertThat(actual);
    }

}
