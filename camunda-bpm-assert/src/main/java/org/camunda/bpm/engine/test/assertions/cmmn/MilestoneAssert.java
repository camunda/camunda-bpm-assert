package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;

/**
 * Assertions for Milestones.
 *
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
public class MilestoneAssert extends AbstractPlanItemAssert<MilestoneAssert, MilestoneHolder> {

  public MilestoneAssert(ProcessEngine engine, MilestoneHolder actual) {
    super(engine, actual, MilestoneAssert.class);
  }

  public MilestoneAssert hasOccurred() {
    return isInState(CaseExecutionState.COMPLETED);
  }

}
