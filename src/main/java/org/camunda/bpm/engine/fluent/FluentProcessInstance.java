package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * @author Martin Schimak <martin.schimak@camunda.com>
 * @author Rafael Cordones <rafael.cordones@camunda.com>
 */
public interface FluentProcessInstance extends FluentDelegate<ProcessInstance>, ProcessInstance {

    /**
     * Starts a process instance ({@link org.camunda.bpm.engine.runtime.ProcessInstance}).
     *
     * @return a fluent {@link FluentProcessInstance} to make assertions against
     *
     * @see FluentProcessInstance
     */
    FluentProcessInstance start();

    FluentProcessInstance startAndMoveTo(String activity);

    /**
     * Adds a process variable to a not yet started process instance ({@link FluentProcessInstance}).
     *
     * @param name the name of the process variable to define
     * @param value the value for te process variable
     *
     * @return the same {@link FluentProcessInstance} to be further configured and eventually started
     *
     * @see FluentProcessInstance#withVariables(java.util.Map)
     */
    FluentProcessInstance withVariable(String name, Object value);

    FluentProcessInstance withVariables(Map<String, Object> variables);

    FluentProcessVariable variable(String key);

    FluentTask task();

    FluentJob job();

    // TODO From here on more "Fluent*" interfaces and wrappers have to be implemented

    List<Task> tasks();

    List<Job> jobs();

}
