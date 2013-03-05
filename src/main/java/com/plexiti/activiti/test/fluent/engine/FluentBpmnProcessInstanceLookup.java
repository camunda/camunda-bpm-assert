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

    public static FluentBpmnProcessInstance newProcessInstance(String processDefinitionId) {
        if (!getTestProcessInstances().containsKey(processDefinitionId)) {
            getTestProcessInstances().put(processDefinitionId, new FluentBpmnProcessInstanceImpl(processDefinitionId));
        }
        return getTestProcessInstances().get(processDefinitionId);
    }

    public static FluentBpmnProcessInstance processInstance() {
        assertThat(getTestProcessInstances()).hasSize(1);
        return getTestProcessInstances().values().iterator().next();
    }

    public static ProcessDefinition processDefinition(String processDefinitionId) {
        List<ProcessDefinition> definitions = FluentBpmnLookups.getRepositoryService().createProcessDefinitionQuery()
                .processDefinitionName(processDefinitionId).list();

        assertThat(definitions)
                .overridingErrorMessage("Unable to find a deployed processInstance definition with name '%s'", processDefinitionId)
                .hasSize(1);

        return definitions.get(0);
    }

    public static void startProcessInstanceByKey(String processKey, Map<String, Object> processVariables) {
        // TODO: Assert that a processInstance definition with that key is already deployed
        FluentBpmnProcessInstance testInstance = new FluentBpmnProcessInstanceImpl(processKey);
        testInstance.withVariables(processVariables);
        testInstance.start();
        getTestProcessInstances().put(processKey, testInstance);
    }

    public static Task findTaskByTaskId(String taskId) {
        ProcessInstance pi = processInstance().getDelegate();
        List<Task> tasks = FluentBpmnLookups.getTaskService().createTaskQuery().processInstanceId(pi.getId()).list();
        assertThat(tasks.size())
                .overridingErrorMessage("Unable to find a processTask with id '%s'", taskId)
                .isEqualTo(1);
        return tasks.get(0);
    }

}
