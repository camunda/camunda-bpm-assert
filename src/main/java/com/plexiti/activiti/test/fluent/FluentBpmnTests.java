package com.plexiti.activiti.test.fluent;

import com.plexiti.activiti.test.fluent.assertions.*;
import com.plexiti.activiti.test.fluent.engine.*;
import org.camunda.bpm.engine.repository.DiagramLayout;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import java.util.List;

/**
 * Convenience class to access all fluent Activiti assertions.
 *
 * In your code use
 *
 *    import static com.plexiti.activiti.test.fluent.FluentBpmnTests.*;
 *
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentBpmnTests extends org.fest.assertions.api.Assertions {

    public static interface Move { void along(); }

    public static FluentBpmnProcessInstance newProcessInstance(String processDefinitionId, Move move) {
        FluentBpmnProcessInstanceImpl fluentBpmnProcessInstance = (FluentBpmnProcessInstanceImpl) FluentBpmnProcessInstanceLookup.newProcessInstance(processDefinitionId);
        fluentBpmnProcessInstance.moveAlong(move);
        return fluentBpmnProcessInstance;
    }

    public static FluentBpmnProcessInstance newProcessInstance(String processDefinitionId) {
        return FluentBpmnProcessInstanceLookup.newProcessInstance(processDefinitionId);
    }

    public static FluentBpmnProcessInstance processInstance() {
        return FluentBpmnProcessInstanceLookup.processInstance();
    }

    public static FluentBpmnProcessVariable processVariable(String id) {
        return FluentBpmnProcessInstanceLookup.processInstance().variable(id);
    }

    public static FluentBpmnTask processTask() {
        return FluentBpmnProcessInstanceLookup.processInstance().task();
    }

    public static FluentBpmnJob processJob() {
        return FluentBpmnProcessInstanceLookup.processInstance().job();
    }

    // TODO From here on more "FluentBpmn" interfaces and wrappers have to be implemented

    public static ProcessDefinition processDefinition(String processDefinitionId) {
        return FluentBpmnProcessInstanceLookup.processDefinition(processDefinitionId);
    }

    // Assertions

    public static TaskAssert assertThat(Task actual) {
        return TaskAssert.assertThat(actual);
    }

    public static ProcessDefinitionAssert assertThat(ProcessDefinition actual) {
        return ProcessDefinitionAssert.assertThat(actual);
    }

    public static TestProcessInstanceAssert assertThat(FluentBpmnProcessInstance actual) {
        return TestProcessInstanceAssert.assertThat(actual);
    }

    public static TestProcessVariableAssert assertThat(FluentBpmnProcessVariable actual) {
        return TestProcessVariableAssert.assertThat(actual);
    }

}
