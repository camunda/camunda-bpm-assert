package com.plexiti.activiti.test.fluent.assertions;

import com.plexiti.activiti.test.fluent.engine.FluentLookups;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;

import java.util.Date;

/**
 * Fluent assertions for {@link org.camunda.bpm.engine.task.Task}
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class TaskAssert extends AbstractAssert<TaskAssert, Task> {

    protected TaskAssert(Task actual) {
        super(actual, TaskAssert.class);
    }

    public static TaskAssert assertThat(Task actual) {
        return new TaskAssert(actual);
    }

    public TaskAssert isUnassigned() {
        isNotNull();

        Assertions.assertThat(actual.getAssignee())
                .overridingErrorMessage("Expected processTask '%s' to be unassigned but it is assigned to '%s'",
                                        actual.getName(), actual.getAssignee())
                .isNull();

        return this;
    }

    public TaskAssert isAssignedTo(String userId) {
        isNotNull();

        Assertions.assertThat(actual.getAssignee())
                .overridingErrorMessage("Expected processTask '%s' to be assigned to user '%s' but it is assigned to '%s'",
                                        actual.getName(), userId, actual.getAssignee())
                .isEqualTo(userId);

        return this;
    }

    public TaskAssert hasCandidateGroup(String candidateGroupId) {
        isNotNull();

        TaskService taskService = FluentLookups.getTaskService();
        TaskQuery query = taskService.createTaskQuery()
                                     .taskId(actual.getId()).taskCandidateGroup(candidateGroupId);
        // FIXME: what happens if there is more than one result. It would throw an exception we need to take care of.
        Assertions.assertThat(query.singleResult())
                .overridingErrorMessage("Expected processTask '%s' to have '%s' as a candidate group",
                                        actual.getName(), candidateGroupId);

        return this;
    }

    /**
     * Assertion on the due date of the {@link org.camunda.bpm.engine.task.Task}.
     *
     * @param dueDate the due date
     *
     * @return a {@link com.plexiti.activiti.test.fluent.assertions.TaskAssert} that can be further configured before starting the process instance
     *
     * @see org.camunda.bpm.engine.task.Task#getDueDate()
     */
    public TaskAssert hasDueDate(Date dueDate) {
        isNotNull();
        TaskService taskService = FluentLookups.getTaskService();
        // FIXME: what happens if there is more than one result. It would throw an exception we need to take care of.
        Task task = taskService.createTaskQuery().taskId(actual.getId()).singleResult();
        Assertions.assertThat(task)
                .overridingErrorMessage("Expected task '%s' to have '%s' as due date but has '%s'",
                        actual.getName(), dueDate, task.getDueDate());
        return this;
    }
}
