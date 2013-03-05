package com.plexiti.activiti.test.fluent;

import com.plexiti.activiti.test.fluent.assertions.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * Convenience class to access all fluent Activiti assertions.
 *
 * In your code use
 *
 *    import static com.plexiti.activiti.test.fluent.api.ActivitiFluentTestAssertions.*;
 *
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class ActivitiFluentTestAssertions extends org.fest.assertions.api.Assertions {

    public static ExecutionAssert assertThat(Execution actual) {
        return ExecutionAssert.assertThat(actual);
    }

    public static ExecutionsAssert assertThat(List<Execution> actual) {
        return ExecutionsAssert.assertThat(actual);
    }

    public static TaskAssert assertThat(Task actual) {
        return TaskAssert.assertThat(actual);
    }

    public static TasksAssert assertThat(List<Task> actual) {
        return TasksAssert.assertThat(actual);
    }

    public static ProcessInstanceAssert assertThat(ProcessInstance actual) {
        return ProcessInstanceAssert.assertThat(actual);
    }

    public static ProcessDefinitionAssert assertThat(ProcessDefinition actual) {
        return ProcessDefinitionAssert.assertThat(actual);
    }

    public static TestProcessInstanceAssert assertThat(TestProcessInstance actual) {
        return TestProcessInstanceAssert.assertThat(actual);
    }

    public static TestProcessVariableAssert assertThat(TestProcessVariable actual) {
        return TestProcessVariableAssert.assertThat(actual);
    }
}
