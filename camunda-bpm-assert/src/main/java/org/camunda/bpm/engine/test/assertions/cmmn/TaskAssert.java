package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;

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
    return isInState(CaseExecutionState.ACTIVE);
  }

  public TaskAssert isInState(CaseExecutionState expectedState) {
    assertThat(actualState())
      .overridingErrorMessage("Expecting %s to be active, but it is %s", actualType(), actualState())
      .isEqualTo(expectedState);
    return this;
  }

  public CaseExecutionState actualState() {
    return taskHolder.actualState();
  }

  public String actualType() {
    return taskHolder.actualType();
  }

  public TaskAssert isAvailable() {
    return isInState(CaseExecutionState.AVAILABLE);
  }

  public TaskAssert isCompleted() {
    return isInState(CaseExecutionState.COMPLETED);
  }

  public TaskAssert isEnabled() {
    return isInState(CaseExecutionState.ENABLED);
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
