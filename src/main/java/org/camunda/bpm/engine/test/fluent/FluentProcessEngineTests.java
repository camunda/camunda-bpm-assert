package org.camunda.bpm.engine.test.fluent;

import org.camunda.bpm.engine.fluent.*;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessDefinitionAssert;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessInstanceAssert;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessVariableAssert;
import org.camunda.bpm.engine.test.fluent.assertions.TaskAssert;

import java.util.HashMap;
import java.util.Map;

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

    private static ThreadLocal<FluentProcessEngineTestRule> testRule = new ThreadLocal<FluentProcessEngineTestRule>();
    private static ThreadLocal<Map<String, FluentProcessInstance>> testProcessInstances = new ThreadLocal<Map<String, FluentProcessInstance>>();

    protected static void before(FluentProcessEngineTestRule testRule) {
        FluentProcessEngineTests.testRule.set(testRule);
        FluentProcessEngineTests.testProcessInstances.set(new HashMap<String, FluentProcessInstance>());
    }

    protected static void after(FluentProcessEngineTestRule testRule) {
        FluentProcessEngineTests.testRule.set(null);
        FluentProcessEngineTests.testProcessInstances.set(null);
    }

    protected static Map<String, FluentProcessInstance> getTestProcessInstances() {
        return testProcessInstances.get();
    }

    protected static FluentProcessEngine processEngine() {
        return testRule.get().getEngine();
    }

    public static interface Move { void along(); }

    public static FluentProcessInstance newProcessInstance(String processDefinitionKey, Move move) {
        FluentProcessInstanceImpl fluentBpmnProcessInstance = (FluentProcessInstanceImpl) processEngine().getProcessInstanceRepository().newProcessInstance(processDefinitionKey);
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
        if (!getTestProcessInstances().containsKey(processDefinitionKey)) {
            getTestProcessInstances().put(processDefinitionKey, processEngine().getProcessInstanceRepository().newProcessInstance(processDefinitionKey));
        }
        return getTestProcessInstances().get(processDefinitionKey);
    }

    /**
     * Returns the one and only {@link FluentProcessInstance} started from within and 
     * bound to the current thread running the test scenario - in case just one such 
     * instance has been started.
     *
     * @return the one and only {@link FluentProcessInstance} bound to the current thread
     * @throws IllegalStateException in case no process instance has been started yet in the 
     * context of the current thread
     * @throws IllegalStateException in case more than one process instance has been started 
     * in the context of the current thread running the test scenario.
     */
    public static FluentProcessInstance processInstance() {
        if (getTestProcessInstances().isEmpty())
            throw new IllegalStateException("No process instance has been started yet in the context of the current thread. Call newProcessinstance(String) first.");
        if (getTestProcessInstances().size() > 1)
            throw new IllegalStateException("More than one process instance has been started in the context of the current thread.");
        return getTestProcessInstances().values().iterator().next();
    }

    /**
     * Returns the first {@link FluentProcessInstance} started with the given processDefinitionKey
     * - hence based on this processDefinition - and started from within and bound to the current 
     * thread running the test scenario. In the seldom case more than one {@link FluentProcessInstance} 
     * per processDefinitionKey need to be started from within the same test scenario, it is the 
     * recommended approach to hold those {@link FluentProcessInstance} objects within local test 
     * variables.
     *
     * @param processDefinitionKey the value of the "id" attribute in the process definition BPMN 2.0 XML file
     * @return the first {@link FluentProcessInstance} bound to the current thread
     * @throws IllegalStateException in case no such process instance (started with the given 
     * processDefinitionKey) has been started yet in the context of the current thread running the 
     * test scenario.
     */
    public static FluentProcessInstance processInstance(String processDefinitionKey) {
        if (!getTestProcessInstances().containsKey(processDefinitionKey))
            throw new IllegalStateException("No such process instance (started with the given processDefinitionKey '" + processDefinitionKey + "') has been started yet in the context of the current thread. Call newProcessinstance(String) first.");
        return getTestProcessInstances().values().iterator().next();
    }

    /**
     * Returns the latest deployed version of the {@link FluentProcessDefinition} with the given 
     * processDefinitionKey
     *
     * @param processDefinitionKey the value of the "id" attribute in the process definition BPMN 2.0 XML file
     * @return the latest deployed version of the {@link FluentProcessDefinition} 
     * @throws IllegalArgumentException in case no such process definition was deployed yet.
     */
    public static FluentProcessDefinition processDefinition(String processDefinitionKey) {
        return processEngine().getProcessDefinitionRepository().processDefinition(processDefinitionKey);
    }

    // Assertions

    public static TaskAssert assertThat(Task actual) {
        return TaskAssert.assertThat(processEngine(), actual);
    }

    public static ProcessDefinitionAssert assertThat(ProcessDefinition actual) {
        return ProcessDefinitionAssert.assertThat(processEngine(), actual);
    }

    public static ProcessInstanceAssert assertThat(FluentProcessInstance actual) {
        return ProcessInstanceAssert.assertThat(processEngine(), actual);
    }

    public static ProcessVariableAssert assertThat(FluentProcessVariable actual) {
        return ProcessVariableAssert.assertThat(processEngine(), actual);
    }

}
