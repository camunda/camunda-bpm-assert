package com.plexiti.activiti.test.fluent.engine;

import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public interface FluentProcessInstance extends FluentDelegate<ProcessInstance>, ProcessInstance {

    FluentProcessInstance start();

    void startAndMoveTo(String activity);

    FluentProcessInstanceImpl withVariable(String name, Object value);

    FluentProcessInstance withVariables(Map<String, Object> variables);

    FluentProcessVariable variable(String variableName);

    FluentTask task();

    FluentJob job();

    // TODO From here on more "FluentBpmn" interfaces and wrappers have to be implemented

    List<Task> tasks();

    List<Job> jobs();

}
