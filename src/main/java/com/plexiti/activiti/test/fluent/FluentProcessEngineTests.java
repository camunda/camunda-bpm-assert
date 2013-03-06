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

    // TODO From here on more "FluentBpmn" interfaces and wrappers have to be implemented

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
