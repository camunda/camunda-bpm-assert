package org.camunda.bpm.engine.test.fluent;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.fluent.FluentProcessDefinition;
import org.camunda.bpm.engine.fluent.FluentProcessEngine;
import org.camunda.bpm.engine.fluent.FluentProcessInstance;
import org.camunda.bpm.engine.fluent.FluentProcessVariable;
import org.camunda.bpm.engine.impl.fluent.FluentProcessEngineImpl;
import org.camunda.bpm.engine.impl.fluent.FluentProcessInstanceImpl;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessDefinitionAssert;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessInstanceAssert;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessVariableAssert;
import org.camunda.bpm.engine.test.fluent.assertions.TaskAssert;

/**
 * Convenience class to access all fluent Activiti assertions.
 * In your code use
 * import static FluentProcessEngineTests.*;
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentProcessEngineTests extends org.fest.assertions.api.Assertions {

    private static ThreadLocal<FluentProcessEngine> fluentProcessEngineLocal = new ThreadLocal<FluentProcessEngine>();
    private static ThreadLocal<Map<String, FluentProcessInstance>> testProcessInstancesLocal = new ThreadLocal<Map<String, FluentProcessInstance>>();

    public static void before(final FluentProcessEngine fluentProcessEngine) {
        FluentProcessEngineTests.fluentProcessEngineLocal.set(fluentProcessEngine);
        FluentProcessEngineTests.testProcessInstancesLocal.set(new HashMap<String, FluentProcessInstance>());
    }

    public static void before(final ProcessEngine processEngine) {
        before(new FluentProcessEngineImpl(processEngine));
    }

    public static void after() {
        FluentProcessEngineTests.fluentProcessEngineLocal.set(null);
        FluentProcessEngineTests.testProcessInstancesLocal.set(null);
    }

    protected static Map<String, FluentProcessInstance> getTestProcessInstances() {
        return testProcessInstancesLocal.get();
    }

    protected static FluentProcessEngine processEngine() {
        return fluentProcessEngineLocal.get();
    }

    public static interface Move extends FluentProcessInstance.Move {}

    public static FluentProcessInstance newProcessInstance(final String processDefinitionKey, final Move move, final String targetActivityId) {
        final FluentProcessInstanceImpl fluentBpmnProcessInstance = (FluentProcessInstanceImpl)newProcessInstance(processDefinitionKey);
        ProcessInstanceAssert.setMoveToActivityId(targetActivityId);
        fluentBpmnProcessInstance.moveAlong(move);
        return fluentBpmnProcessInstance;
    }

    /**
     * Creates a new {@link FluentProcessInstance} which provides fluent methods to declare
     * process variables before actually starting a process instance.
     * @param processDefinitionKey the value of the "id" attribute in the process definition BPMN 2.0 XML file
     * @return a {@link FluentProcessInstance} that can be further configured before starting the process instance
     * @throws IllegalArgumentException in case there is no such process definition deployed with the given key
     * @see org.camunda.bpm.engine.fluent.FluentProcessInstance#setVariable(String, Object)
     * @see org.camunda.bpm.engine.fluent.FluentProcessInstance#start()
     */
    public static FluentProcessInstance newProcessInstance(final String processDefinitionKey) {
        if (!getTestProcessInstances().containsKey(processDefinitionKey)) {
            getTestProcessInstances().put(processDefinitionKey, processEngine().getProcessInstanceRepository().newProcessInstance(processDefinitionKey));
        }
        return getTestProcessInstances().get(processDefinitionKey);
    }

    /**
     * Returns the one and only {@link FluentProcessInstance} started from within and
     * bound to the current thread running the test scenario - in case just one such
     * instance has been started.
     * @return the one and only {@link FluentProcessInstance} bound to the current thread
     * @throws IllegalStateException in case no process instance has been started yet in the
     *         context of the current thread
     * @throws IllegalStateException in case more than one process instance has been started
     *         in the context of the current thread running the test scenario.
     */
    public static FluentProcessInstance processInstance() {
        if (getTestProcessInstances().isEmpty())
            throw new IllegalStateException(
                    "No process instance has been started yet in the context of the current thread. Call newProcessinstance(String) first.");
        if (getTestProcessInstances().size() > 1)
            throw new IllegalStateException("More than one process instance has been started in the context of the current thread.");
        return getTestProcessInstances().values().iterator().next();
    }

    /**
     * Returns the first {@link FluentProcessInstance} started with the given processDefinitionKey
     * - hence based on this processDefinition - and started from within and bound to the current
     * thread running the test scenario. In the seldom case more than one {@link FluentProcessInstance} per processDefinitionKey need to be started from within
     * the same test scenario, it is the
     * recommended approach to hold those {@link FluentProcessInstance} objects within local test
     * variables.
     * @param processDefinitionKey the value of the "id" attribute in the process definition BPMN 2.0 XML file
     * @return the first {@link FluentProcessInstance} bound to the current thread
     * @throws IllegalStateException in case no such process instance (started with the given
     *         processDefinitionKey) has been started yet in the context of the current thread running the
     *         test scenario.
     */
    public static FluentProcessInstance processInstance(final String processDefinitionKey) {
        if (!getTestProcessInstances().containsKey(processDefinitionKey))
            throw new IllegalStateException("No such process instance (started with the given processDefinitionKey '" + processDefinitionKey
                    + "') has been started yet in the context of the current thread. Call newProcessinstance(String) first.");
        return getTestProcessInstances().values().iterator().next();
    }

    /**
     * Returns the latest deployed version of the {@link FluentProcessDefinition} with the given
     * processDefinitionKey
     * @param processDefinitionKey the value of the "id" attribute in the process definition BPMN 2.0 XML file
     * @return the latest deployed version of the {@link FluentProcessDefinition}
     * @throws IllegalArgumentException in case no such process definition was deployed yet.
     */
    public static FluentProcessDefinition processDefinition(final String processDefinitionKey) {
        return processEngine().getProcessDefinitionRepository().processDefinition(processDefinitionKey);
    }

    // Assertions

    public static TaskAssert assertThat(final Task actual) {
        return TaskAssert.assertThat(processEngine(), actual);
    }

    public static ProcessDefinitionAssert assertThat(final ProcessDefinition actual) {
        return ProcessDefinitionAssert.assertThat(processEngine(), actual);
    }

    public static ProcessInstanceAssert assertThat(final ProcessInstance actual) {
        return ProcessInstanceAssert.assertThat(processEngine(), actual);
    }

    public static ProcessVariableAssert assertThat(final FluentProcessVariable actual) {
        return ProcessVariableAssert.assertThat(processEngine(), actual);
    }

}
