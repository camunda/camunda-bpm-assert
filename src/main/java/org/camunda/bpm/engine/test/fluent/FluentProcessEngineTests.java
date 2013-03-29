package org.camunda.bpm.engine.test.fluent;

import org.camunda.bpm.engine.fluent.*;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessDefinitionAssert;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessInstanceAssert;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessVariableAssert;
import org.camunda.bpm.engine.test.fluent.assertions.TaskAssert;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.Task;

/**
 * Convenience class to access all fluent Activiti assertions.
 *
 * In your code use
 *
 *    import static FluentProcessEngineTests.*;
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentProcessEngineTests extends org.fest.assertions.api.Assertions {

    public static interface Move { void along(); }

    public static FluentProcessInstance newProcessInstance(String processDefinitionKey, Move move) {
        FluentProcessInstanceImpl fluentBpmnProcessInstance = (FluentProcessInstanceImpl) FluentProcessInstanceLookup.newProcessInstance(processDefinitionKey);
        fluentBpmnProcessInstance.moveAlong(move);
        return fluentBpmnProcessInstance;
    }

    /**
     * Creates a new {@link FluentProcessInstance} which provides fluent methods to declare
     * process variables before actually starting a process instance.
     *
     * @param processDefinitionKey the value of the "id" attribute in the process definition BPMN 2.0 XML file
     * @return a {@link FluentProcessInstance} that can be further configured before starting the process instance
     * @throws IllegalArgumentException in case there is no such process definition deployed with the given key
     *
     * @see org.camunda.bpm.engine.fluent.FluentProcessInstance#withVariable(String, Object)
     * @see org.camunda.bpm.engine.fluent.FluentProcessInstance#withVariables(java.util.Map)
     * @see org.camunda.bpm.engine.fluent.FluentProcessInstance#start()
     */
    public static FluentProcessInstance newProcessInstance(String processDefinitionKey) {
        return FluentProcessInstanceLookup.newProcessInstance(processDefinitionKey);
    }

    /**
     * Returns the one and only {@link FluentProcessInstance} started from within and 
     * bound to the current thread - in case just one such instance has been started.
     *
     * @return the one and only {@link FluentProcessInstance} bound to the current thread
     * @throws IllegalStateException in case no process instance has been started yet in the context of the current thread
     * @throws IllegalStateException in case more than one process instance has been started in the context of the current thread
     *
     * @see org.camunda.bpm.engine.fluent.FluentProcessInstance#withVariable(String, Object)
     * @see org.camunda.bpm.engine.fluent.FluentProcessInstance#withVariables(java.util.Map)
     * @see org.camunda.bpm.engine.fluent.FluentProcessInstance#start()
     */
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
