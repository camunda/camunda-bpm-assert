package com.plexiti.activiti.test.fluent.assertions;

import com.plexiti.activiti.test.fluent.TestProcessInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 * Convenience class to access all fluent Activiti assertions.
 *
 * In your code use
 *
 *    import com.plexiti.activiti.test.fluent.assertions.Assertions.*;
 *
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public class Assertions extends org.fest.assertions.api.Assertions {
    public static ExecutionAssert assertThat(Execution actual) {
        return new ExecutionAssert(actual);
    }

    public static TaskAssert assertThat(Task actual) {
        return new TaskAssert(actual);
    }

    public static ProcessInstanceAssert assertThat(ProcessInstance actual) {
        return new ProcessInstanceAssert(actual);
    }

    public static TestProcessInstanceAssert assertThat(TestProcessInstance actual) {
        return new TestProcessInstanceAssert(actual);
    }
}
