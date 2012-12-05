package com.plexiti.activiti.test.fluent.assertions;

import org.activiti.engine.task.Task;
import org.fest.assertions.api.AbstractAssert;

import java.util.List;

/**
 * Fluent assertions for a collection of {@link org.activiti.engine.task.Task}
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class TasksAssert extends AbstractAssert<TasksAssert, List<Task>> {

    protected TasksAssert(List<Task> actual) {
        super(actual, TasksAssert.class);
    }

    public static TasksAssert assertThat(List<Task> actual) {
        return new TasksAssert(actual);
    }
}
