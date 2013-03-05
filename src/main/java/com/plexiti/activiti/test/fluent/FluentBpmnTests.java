package com.plexiti.activiti.test.fluent;

import com.plexiti.activiti.test.fluent.assertions.*;
import com.plexiti.activiti.test.fluent.engine.*;

import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

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

    public static ExecutionAssert assertThat(Execution actual) {
        return ExecutionAssert.assertThat(actual);
    }

    public static ExecutionsAssert assertThat(List<Execution> actual) {
        return ExecutionsAssert.assertThat(actual);
    }

    public static TaskAssert assertThat(Task actual) {
        return TaskAssert.assertThat(actual);
    }

    public static TasksAssert assertThat(List<Task> actual) {
        return TasksAssert.assertThat(actual);
    }

    public static ProcessInstanceAssert assertThat(ProcessInstance actual) {
        return ProcessInstanceAssert.assertThat(actual);
    }

    public static ProcessDefinitionAssert assertThat(ProcessDefinition actual) {
        return ProcessDefinitionAssert.assertThat(actual);
    }

    public static DiagramLayoutAssert assertThat(DiagramLayout actual) {
        return DiagramLayoutAssert.assertThat(actual);
    }

    public static TestProcessInstanceAssert assertThat(FluentBpmnProcessInstance actual) {
        return TestProcessInstanceAssert.assertThat(actual);
    }

    public static TestProcessVariableAssert assertThat(FluentBpmnProcessVariable actual) {
        return TestProcessVariableAssert.assertThat(actual);
    }

    public static FluentBpmnProcessInstance start(FluentBpmnProcessInstanceImpl testProcess) {
        return FluentBpmnProcessInstanceLookup.start(testProcess);
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

    // TODO From here on more "FluentBpmn" interfaces and wrappers have to be implemented

    public static ProcessDefinition processDefinition(String processDefinitionName) {
        return FluentBpmnProcessInstanceLookup.processDefinition(processDefinitionName);
    }

    public static DiagramLayout processDiagramLayout() {
        return FluentBpmnProcessInstanceLookup.processInstance().diagramLayout();
    }

    public static Execution processExecution() {
        return FluentBpmnProcessInstanceLookup.processInstance().execution();
    }

}
