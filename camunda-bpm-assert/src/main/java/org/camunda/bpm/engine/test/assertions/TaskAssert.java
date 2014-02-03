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
  public Task getRefreshedActual() {
    return taskQuery().taskId(actual.getId()).singleResult();
  }

  /**
   * Verifies the expectation that the {@link Task} is currently not assigned to 
   * any particular user.
   * @return this {@link TaskAssert}
   */  
  public TaskAssert isNotAssigned() {
    isNotNull();
    Task task = getRefreshedActual();
    Assertions.assertThat(task).isNotNull();
    Assertions.assertThat(task.getAssignee())
      .overridingErrorMessage("Expected %s not to be assigned, but found it to be assigned to user '%s'!",
        toString(task),
        task.getAssignee())
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
    isNotNull();
    Task task = getRefreshedActual();
    Assertions.assertThat(task).isNotNull();
    Assertions
      .assertThat(task.getAssignee())
      .overridingErrorMessage("Expected %s to be assigned to user '%s', but found it to be assigned to '%s'!", 
        toString(task), 
        userId,
        task.getAssignee())
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
    isNotNull();
    final Task task = getRefreshedActual();
    Assertions.assertThat(task).isNotNull();
    final Task inGroup = taskQuery().taskId(actual.getId()).taskCandidateGroup(candidateGroupId).singleResult();
    Assertions.assertThat(inGroup)
        .overridingErrorMessage("Expected %s to have candidate group '%s', but found it not to have that candidate group!", 
          toString(task), 
          candidateGroupId)
      .isNotNull();
    return this;
  }

  /**
   * Assertion on the due date of the {@link org.camunda.bpm.engine.task.Task}.
   * 
   * @param expectedDueDate
   *          the due date
   * 
   * @return a {@link TaskAssert} that can be further configured before starting
   *         the process instance
   * 
   * @see org.camunda.bpm.engine.task.Task#getDueDate()
   */
  public TaskAssert hasDueDate(final Date expectedDueDate) {
    isNotNull();
    Assertions.assertThat(actual.getDueDate())
        .overridingErrorMessage("Expected task '%s' to have '%s' as due date but has '%s'", actual.getName(), expectedDueDate, actual.getDueDate())
        .equals(expectedDueDate);
    return this;
  }

  /**
   * Assertion on the id of the &lt;userTask id="xxx" .../&gt; element in the
   * process definition BPMN 2.0 XML file.
   * 
   * @param expectedTaskDefinitionKey
   *          the value of the id attribute in the process definition
   * 
   * @return a {@link TaskAssert} that can be further configured before starting
   *         the process instance
   * 
   * @see org.camunda.bpm.engine.task.Task#getTaskDefinitionKey()
   */
  public TaskAssert hasDefinitionKey(final String expectedTaskDefinitionKey) {
    isNotNull();
    final String actualTaskDefinitionKey = actual.getTaskDefinitionKey();

    Assertions.assertThat(actualTaskDefinitionKey)
        .overridingErrorMessage("Expected task definitionKey to be '%1$s', but was '%2$s'", expectedTaskDefinitionKey, actualTaskDefinitionKey)
        .isEqualTo(expectedTaskDefinitionKey);
    return this;
  }

  /**
   * Assertion on the id of the <usertask> element in the process definition
   * BPMN 2.0 XML file.
   * 
   * Please note that the method
   * {@link org.camunda.bpm.engine.task.Task#getId()} returns the database id of
   * the task and not the value of the attribute 'id' of the task in the process
   * definition file.
   * 
   * @param id
   *          the task id
   * 
   * @return a {@link TaskAssert} that can be further configured before starting
   *         the process instance
   */
  public TaskAssert hasId(final String id) {
    isNotNull();
    final String actualId = actual.getId();
    Assertions.assertThat(actualId).overridingErrorMessage("Expected task '%s' to have '%s' as id but has '%s'", actual.getName(), actualId, id).isEqualTo(id);
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
    Assertions.assertThat(actual.getName()).overridingErrorMessage("Expected task with name '%s', but was '%s'", expectedName, actual.getName())
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
        .overridingErrorMessage("Expected task '%s' to have '%s' as name but has '%s'", actual.getName(), expectedDescription, actualDescription)
        .isEqualTo(expectedDescription);
    return this;
  }

  private String toString(Task task) {
    return task != null ? 
      String.format("actual %s {id='%s', processInstanceId='%s', taskDefinitionKey='%s', taskName='%s'}",
        Task.class.getName(),
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
