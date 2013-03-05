package com.plexiti.activiti.test.fluent.engine;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentBpmnProcessInstanceLookup {

    private static ThreadLocal<Map<String, FluentBpmnProcessInstance>> testProcessInstances = new ThreadLocal<Map<String, FluentBpmnProcessInstance>>();

    protected static void init(Object junitTest) {
        testProcessInstances.set(null);
    }

    protected static Map<String, FluentBpmnProcessInstance> getTestProcessInstances() {
        Map<String, FluentBpmnProcessInstance> processes = testProcessInstances.get();
        if (processes == null)
            testProcessInstances.set(processes = new HashMap<String, FluentBpmnProcessInstance>());
        return processes;
    }

    public static FluentBpmnProcessInstance start(FluentBpmnProcessInstanceImpl testProcess) {
        if (getTestProcessInstances().containsKey(testProcess.processDefinitionId)) {
            return getTestProcessInstances().get(testProcess.processDefinitionId);
        } else {
            getTestProcessInstances().put(testProcess.processDefinitionId, testProcess);
            testProcess.start();
            return testProcess;
        }
    }

    public static FluentBpmnProcessInstance process(String processDefinitionKey) {
        return getTestProcessInstances().get(processDefinitionKey);
    }

    public static FluentBpmnProcessInstance process() {
        assertThat(getTestProcessInstances()).hasSize(1);
        return getTestProcessInstances().values().iterator().next();
    }

    public static ProcessDefinition processDefinition(String processDefinitionName) {
        List<ProcessDefinition> definitions = FluentBpmnLookups.getRepositoryService().createProcessDefinitionQuery()
                .processDefinitionName(processDefinitionName).list();

        assertThat(definitions)
                .overridingErrorMessage("Unable to find a deployed process definition with name '%s'", processDefinitionName)
                .hasSize(1);

        return definitions.get(0);
    }

    public static void startProcessInstanceByKey(String processKey, Map<String, Object> processVariables) {
        // TODO: Assert that a process definition with that key is already deployed
        FluentBpmnProcessInstance testInstance = new FluentBpmnProcessInstanceImpl(processKey);
        testInstance.withVariables(processVariables);
        testInstance.start();
        getTestProcessInstances().put(processKey, testInstance);
    }

    public static Task findTaskByTaskId(String taskId) {
        ProcessInstance pi = process().getDelegate();
        List<Task> tasks = FluentBpmnLookups.getTaskService().createTaskQuery().processInstanceId(pi.getId()).list();
        assertThat(tasks.size())
                .overridingErrorMessage("Unable to find a task with id '%s'", taskId)
                .isEqualTo(1);
        return tasks.get(0);
    }

}
