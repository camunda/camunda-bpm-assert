package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Assertions for Human, Case and Process Tasks.
 *
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
public class TaskAssert extends AbstractCaseAssert<TaskAssert, TaskHolder> {

  private final TaskHolder taskHolder;

  public TaskAssert(ProcessEngine engine, TaskHolder taskHolder) {
    super(engine, taskHolder, TaskAssert.class);
    this.taskHolder = taskHolder;
  }

  public TaskAssert hasAssignee() {
    return this;
  }

  public TaskAssert hasCandidateGroup() {
    return this;
  }

  public TaskAssert hasCandidateUser() {
    return this;
  }

  public TaskAssert isActive() {
    //TODO move CaseExecution access to TaskHolder
    assertThat(taskHolder.getActualCaseExecution()).isNotNull();
    assertThat(taskHolder.getActualCaseExecution().isActive()).isTrue();
    return this;
  }

  public TaskAssert isAvailable() {
    //TODO move CaseExecution access to TaskHolder
    assertThat(taskHolder.getActualCaseExecution()).isNotNull();
    assertThat(taskHolder.getActualCaseExecution().isAvailable()).isTrue();
    return this;
  }

  public TaskAssert isCompleted() {
    //TODO move HistoricCaseExecutionInstance access to TaskHolder
    assertThat(taskHolder.getActualHistoricCaseActivityInstance()).isNotNull();
    assertThat(taskHolder.getActualHistoricCaseActivityInstance().isCompleted()).isTrue();
    return this;
  }

  public TaskAssert isEnabled() {
    //TODO move CaseExecution access to TaskHolder
    assertThat(taskHolder.getActualCaseExecution()).isNotNull();
    assertThat(taskHolder.getActualCaseExecution().isEnabled()).isTrue();
    return this;
  }

  @Override
  protected TaskHolder getCurrent() {
    return null;
  }

  @Override
  protected String toString(TaskHolder object) {
    return null;
  }
}
