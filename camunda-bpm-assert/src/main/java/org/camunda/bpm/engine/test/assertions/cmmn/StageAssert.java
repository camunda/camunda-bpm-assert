package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;

/**
 * Assertions for Stages.
 *
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
public class StageAssert extends AbstractPlanItemAssert<StageAssert, StageHolder> {

  private final StageHolder stageHolder;

  public StageAssert(ProcessEngine engine, StageHolder stageHolder) {
    super(engine, stageHolder, StageAssert.class);
    this.stageHolder = stageHolder;
  }

  public StageAssert isActive() {
    return isInState(CaseExecutionState.ACTIVE);
  }

  public StageAssert isAvailable() {
    return isInState(CaseExecutionState.AVAILABLE);
  }

  public StageAssert isCompleted() {
    return isInState(CaseExecutionState.COMPLETED);
  }

  public StageAssert isEnabled() {
    return isInState(CaseExecutionState.ENABLED);
  }

}
