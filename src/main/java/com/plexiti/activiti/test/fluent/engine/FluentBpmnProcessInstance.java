package com.plexiti.activiti.test.fluent.engine;

import org.camunda.bpm.engine.repository.DiagramLayout;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public interface FluentBpmnProcessInstance extends FluentBpmnDelegate<ProcessInstance>, ProcessInstance {

    FluentBpmnProcessInstance start();

    void startAndMoveTo(String activity);

    FluentBpmnProcessInstanceImpl withVariable(String name, Object value);

    FluentBpmnProcessInstance withVariables(Map<String, Object> variables);

    FluentBpmnProcessVariable variable(String variableName);

    FluentBpmnTask task();

    FluentBpmnJob job();

    // TODO From here on more "FluentBpmn" interfaces and wrappers have to be implemented

    List<Task> tasks();

    List<Job> jobs();

}
