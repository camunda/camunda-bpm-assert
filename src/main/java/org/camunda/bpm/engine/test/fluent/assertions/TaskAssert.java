package org.camunda.bpm.engine.test.fluent.assertions;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.fluent.FluentProcessEngine;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.fest.assertions.api.Assertions;

import java.util.Date;

/**
 * Fluent assertions for {@link org.camunda.bpm.engine.task.Task}
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class TaskAssert extends AbstractProcessAssert<TaskAssert, Task> {

    protected TaskAssert(FluentProcessEngine engine, Task actual) {
        super(engine, actual, TaskAssert.class);
    }

    public static TaskAssert assertThat(FluentProcessEngine engine, Task actual) {
        return new TaskAssert(engine, actual);
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

    /**
     * Assertion on the candidate group a {@link org.camunda.bpm.engine.task.Task} is assigned to.
     *
     * @param candidateGroupId the candidate group
     *
     * @return a {@link TaskAssert} that can be further configured before starting the process instance
     *
     * @see org.camunda.bpm.engine.task.Task
     */
    public TaskAssert hasCandidateGroup(String candidateGroupId) {
        isNotNull();
        TaskService taskService = engine.getTaskService();
        TaskQuery query = taskService.createTaskQuery()
                                     .taskId(actual.getId()).taskCandidateGroup(candidateGroupId);
        Task task = query.singleResult();
        /*
         * TODO: There does not seem to be a way to find out the candidate groups of a given task. The TaskQuery API
         *       only offers to look up tasks which have a given candidate group but not the other way around.
         */
        Assertions.assertThat(task)
                .overridingErrorMessage("Expected task '%s' to have '%s' as a candidate group but id doesn't",
                        actual.getName(), candidateGroupId);

        return this;
    }

    /**
     * Assertion on the due date of the {@link org.camunda.bpm.engine.task.Task}.
     *
     * @param dueDate the due date
     *
     * @return a {@link TaskAssert} that can be further configured before starting the process instance
     *
     * @see org.camunda.bpm.engine.task.Task#getDueDate()
     */
    public TaskAssert hasDueDate(Date dueDate) {
        isNotNull();
        Task task = findCurrentTaskById(actual.getId());
        Assertions.assertThat(task)
                .overridingErrorMessage("Expected task '%s' to have '%s' as due date but has '%s'",
                        actual.getName(), dueDate, task.getDueDate());
        return this;
    }

    /**
     * Assertion on the id of the &lt;userTask id="xxx" .../&gt; element in the process definition BPMN 2.0 XML file.
     *
     * @param key the value of the id attribute in the process definition
     *
     * @return a {@link TaskAssert} that can be further configured before starting the process instance
     *
     * @see org.camunda.bpm.engine.task.Task#getTaskDefinitionKey()
     */
    public TaskAssert hasDefinitionKey(String key) {
        isNotNull();
        Task task = findCurrentTaskById(actual.getId());
        Assertions.assertThat(task)
                .overridingErrorMessage("Expected task '%s' to have '%s' as process definition key but has '%s'",
                        actual.getTaskDefinitionKey(), key, task.getTaskDefinitionKey());
        return this;
    }

    /**
     * Assertion on the id of the <usertask> element in the process definition BPMN 2.0 XML file.
     *
     * Please note that the method {@link org.camunda.bpm.engine.task.Task#getId()} returns the
     * database id of the task and not the value of the attribute 'id' of the task in the process definition file.
     *
     * @param id the task id
     *
     * @return a {@link TaskAssert} that can be further configured before starting the process instance
     */
    public TaskAssert hasId(String id) {
        isNotNull();
        Task task = findCurrentTaskById(actual.getId());
        Assertions.assertThat(task)
                .overridingErrorMessage("Expected task '%s' to have '%s' as id but has '%s'",
                        actual.getId(), id, task.getId());
        return this;
    }

    /**
     * Assertion on the name or title of the {@link org.camunda.bpm.engine.task.Task}.
     *
     * @param name the task name or title
     *
     * @return a {@link TaskAssert} that can be further configured before starting the process instance
     *
     * @see org.camunda.bpm.engine.task.Task#getName()
     */
    public TaskAssert hasName(String name) {
        isNotNull();
        Task task = findCurrentTaskById(actual.getId());
        Assertions.assertThat(task)
                .overridingErrorMessage("Expected task '%s' to have '%s' as name but has '%s'",
                                        actual.getName(), name, task.getName());
        return this;
    }

    /**
     * Assertion on the free text description of the {@link org.camunda.bpm.engine.task.Task}.
     *
     * @param description the free text description of the task
     *
     * @return a {@link TaskAssert} that can be further configured before starting the process instance
     *
     * @see org.camunda.bpm.engine.task.Task#getDescription()
     */
    public TaskAssert hasDescription(String description) {
        isNotNull();
        Task task = findCurrentTaskById(actual.getId());
        Assertions.assertThat(task)
                .overridingErrorMessage("Expected task '%s' to have '%s' as name but has '%s'",
                        actual.getName(), description, task.getDescription());
        return this;
    }

    /*
     * Utility methods
     */
    protected Task findCurrentTaskById(String taskId) {
        TaskService taskService = engine.getTaskService();
        // FIXME: what happens if there is more than one result. It would throw an exception we need to take care of.
        Task task = taskService.createTaskQuery().taskId(actual.getId()).singleResult();
        return task;
    }

}
