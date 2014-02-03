package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.*;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.assertj.core.api.Assertions;

import java.util.Date;

/**
 * Assertions for a {@link Task}
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael@cordones.me>
 */
public class TaskAssert extends AbstractProcessAssert<TaskAssert, Task> {

  protected TaskAssert(final ProcessEngine engine, final Task actual) {
    super(engine, actual, TaskAssert.class);
  }

  protected static TaskAssert assertThat(final ProcessEngine engine, final Task actual) {
    return new TaskAssert(engine, actual);
  }

  @Override
  protected Task getCurrent() {
    return taskQuery().taskId(actual.getId()).singleResult();
  }

  /**
   * Verifies the expectation that the {@link Task} is currently not assigned to 
   * any particular user.
   * @return this {@link TaskAssert}
   */  
  public TaskAssert isNotAssigned() {
    Task current = getExistingCurrent();
    Assertions.assertThat(current.getAssignee())
      .overridingErrorMessage("Expecting %s not to be assigned, but found it to be assigned to user '%s'!",
        toString(current),
        current.getAssignee())
      .isNull();
    return this;
  }

  /**
   * Verifies the expectation that the {@link Task} is currently assigned to 
   * the specified user.
   * @param userId id of the user the task should be currently assigned to.
   * @return this {@link TaskAssert}
   */
  public TaskAssert isAssignedTo(final String userId) {
    Task current = getExistingCurrent();
    Assertions
      .assertThat(current.getAssignee())
      .overridingErrorMessage("Expecting %s to be assigned to user '%s', but found it to be assigned to '%s'!", 
        toString(current), 
        userId,
        current.getAssignee())
      .isEqualTo(userId);
    return this;
  }

  /**
   * Verifies the expectation that the {@link Task} is currently waiting to 
   * be assigned to a user of the specified candidate group.
   * @param candidateGroupId id of the candidate group the task is assigned to
   * @return this {@link TaskAssert}
   */
  public TaskAssert hasCandidateGroup(final String candidateGroupId) {
    Assertions.assertThat(candidateGroupId).isNotNull();
    final Task current = getExistingCurrent();
    final Task inGroup = taskQuery().taskId(actual.getId()).taskCandidateGroup(candidateGroupId).singleResult();
    Assertions.assertThat(inGroup)
        .overridingErrorMessage("Expecting %s to have candidate group '%s', but found it not to have that candidate group!",
          toString(current),
          candidateGroupId)
      .isNotNull();
    return this;
  }

  /**
   * Verifies the due date of a {@link Task}.
   * @param dueDate the date the task should be due at
   * @return this {@link TaskAssert}
   */
  public TaskAssert hasDueDate(final Date dueDate) {
    Task current = getExistingCurrent();
    Assertions.assertThat(dueDate).isNotNull();
    Assertions.assertThat(current.getDueDate())
        .overridingErrorMessage("Expecting %s to be due at '%s', but found it to be due at '%s'!",
          toString(current),
          dueDate, 
          current.getDueDate()
        )
      .isEqualTo(dueDate);
    return this;
  }

  /**
   * Verifies the definition key of a {@link Task}. This key can be found
   * in the &lt;userTask id="myTaskDefinitionKey" .../&gt; element of the
   * process definition BPMN 2.0 XML file.
   * @param taskDefinitionKey the expected value of the task/@id attribute
   * @return this {@link TaskAssert}
   */
  public TaskAssert hasDefinitionKey(final String taskDefinitionKey) {
    Task current = getExistingCurrent();
    Assertions.assertThat(taskDefinitionKey).isNotNull();
    Assertions.assertThat(current.getTaskDefinitionKey())
      .overridingErrorMessage("Expecting %s to have definition key '%s', but found it to have '%s'!",
        toString(current),
        taskDefinitionKey,
        current.getTaskDefinitionKey()
      ).isEqualTo(taskDefinitionKey);
    return this;
  }

  /**
   * Verifies the internal id of a {@link Task}.
   * @param id the expected value of the internal task id
   * @return this {@link TaskAssert}
   */
  public TaskAssert hasId(final String id) {
    Task current = getExistingCurrent();
    Assertions.assertThat(id).isNotNull();
    Assertions.assertThat(current.getId())
      .overridingErrorMessage("Expecting %s to have internal id '%s', but found it to be '%s'!",
        toString(current),
        id,
        current.getId()
      ).isEqualTo(id);
    return this;
  }

  /**
   * Assertion on the name or title of the
   * {@link org.camunda.bpm.engine.task.Task}.
   * 
   * @param expectedName
   *          the task name or title
   * 
   * @return a {@link TaskAssert} that can be further configured before starting
   *         the process instance
   * 
   * @see org.camunda.bpm.engine.task.Task#getName()
   */
  public TaskAssert hasName(final String expectedName) {
    isNotNull();
    Assertions.assertThat(actual.getName()).overridingErrorMessage("Expecting task with name '%s', but was '%s'", expectedName, actual.getName())
        .isEqualTo(expectedName);
    return this;
  }

  /**
   * Assertion on the free text description of the
   * {@link org.camunda.bpm.engine.task.Task}.
   * 
   * @param expectedDescription
   *          the free text description of the task
   * 
   * @return a {@link TaskAssert} that can be further configured before starting
   *         the process instance
   * 
   * @see org.camunda.bpm.engine.task.Task#getDescription()
   */
  public TaskAssert hasDescription(final String expectedDescription) {
    isNotNull();
    final String actualDescription = actual.getDescription();
    Assertions.assertThat(actualDescription)
        .overridingErrorMessage("Expecting task '%s' to have '%s' as name but has '%s'", actual.getName(), expectedDescription, actualDescription)
        .isEqualTo(expectedDescription);
    return this;
  }

  @Override
  protected String toString(Task task) {
    return task != null ? 
      String.format("actual %s {" +
        "id='%s', " +
        "processInstanceId='%s', " +
        "taskDefinitionKey='%s', " +
        "name='%s'" +
        "}",
        Task.class.getSimpleName(),
        task.getId(),
        task.getProcessInstanceId(),
        task.getTaskDefinitionKey(),
        task.getName()
      ) : null;
  }

  /* TaskQuery, automatically narrowed to {@link ProcessInstance} of actual 
   * {@link Task} 
   */
  @Override
  protected TaskQuery taskQuery() {
    return super.taskQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* JobQuery, automatically narrowed to {@link ProcessInstance} of actual 
   * {@link Task} 
   */
  @Override
  protected JobQuery jobQuery() {
    return super.jobQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* ProcessInstanceQuery, automatically narrowed to {@link ProcessInstance} of 
   * actual {@link Task} 
   */
  @Override
  protected ProcessInstanceQuery processInstanceQuery() {
    return super.processInstanceQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* ExecutionQuery, automatically narrowed to {@link ProcessInstance} of 
   * actual {@link Task} 
   */
  @Override
  protected ExecutionQuery executionQuery() {
    return super.executionQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* VariableInstanceQuery, automatically narrowed to {@link ProcessInstance} of 
   * actual {@link Task} 
   */
  @Override
  protected VariableInstanceQuery variableInstanceQuery() {
    return super.variableInstanceQuery().processInstanceIdIn(actual.getProcessInstanceId());
  }

  /* HistoricActivityInstanceQuery, automatically narrowed to {@link ProcessInstance} of actual {@link Task} */
  @Override
  protected HistoricActivityInstanceQuery historicActivityInstanceQuery() {
    return super.historicActivityInstanceQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* HistoricDetailQuery, automatically narrowed to {@link ProcessInstance} of 
   * actual {@link Task} 
   */
  @Override
  protected HistoricDetailQuery historicDetailQuery() {
    return super.historicDetailQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* HistoricProcessInstanceQuery, automatically narrowed to {@link ProcessInstance} 
   * of actual {@link Task} 
   */
  @Override
  protected HistoricProcessInstanceQuery historicProcessInstanceQuery() {
    return super.historicProcessInstanceQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* HistoricTaskInstanceQuery, automatically narrowed to {@link ProcessInstance} 
   * of actual {@link Task} 
   */
  @Override
  protected HistoricTaskInstanceQuery historicTaskInstanceQuery() {
    return super.historicTaskInstanceQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* HistoricVariableInstanceQuery, automatically narrowed to {@link ProcessInstance} 
   * of actual {@link Task} 
   */
  @Override
  protected HistoricVariableInstanceQuery historicVariableInstanceQuery() {
    return super.historicVariableInstanceQuery().processInstanceId(actual.getProcessInstanceId());
  }

  /* ProcessDefinitionQuery, automatically narrowed to {@link ProcessDefinition} 
   * of {@link ProcessInstance} of actual {@link Task} 
   */
  @Override
  protected ProcessDefinitionQuery processDefinitionQuery() {
    return super.processDefinitionQuery().processDefinitionId(actual.getProcessDefinitionId());
  }

}
