package org.camunda.bpm.engine.test.assertions.cmmn_new;

import org.camunda.bpm.engine.ProcessEngine;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tha TaskAssert encapsulates all inspections on Human, Case and Process Tasks.
 * <p/>
 * Depending on the state of the task, it works either on a CaseExecution or on a HistoricCaseActivityInstance.
 * The actual class worked on is hidden, so that the test code is independent of this information.
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
    assertThat(taskHolder.getActualCaseExecution()).isNotNull();
    assertThat(taskHolder.getActualCaseExecution().isActive()).isTrue();
    return this;
  }

  public TaskAssert isAvailable() {
    return this;
  }

  public TaskAssert isCompleted() {
    return this;
  }

  public TaskAssert isEnabled() {
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
