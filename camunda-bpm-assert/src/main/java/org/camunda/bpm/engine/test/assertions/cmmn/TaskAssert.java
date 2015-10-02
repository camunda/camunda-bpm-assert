package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;

/**
 * Assertions for Human, Case and Process Tasks.
 *
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
public class TaskAssert extends AbstractPlanItemAssert<TaskAssert, TaskHolder> {

  private final TaskHolder taskHolder;

  public TaskAssert(ProcessEngine engine, TaskHolder taskHolder) {
    super(engine, taskHolder, TaskAssert.class);
    this.taskHolder = taskHolder;
  }

  public TaskAssert isActive() {
    return isInState(CaseExecutionState.ACTIVE);
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


}
