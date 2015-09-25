package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;

/**
 * Assertions for Milestones.
 *
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
public class MilestoneAssert extends AbstractCaseAssert<MilestoneAssert, Object> {

  public MilestoneAssert(ProcessEngine engine, MilestoneHolder actual) {
    super(engine, actual, MilestoneAssert.class);
  }

  public MilestoneAssert hasOccured() {
    return this;
  }

  @Override
  protected Object getCurrent() {
    return null;
  }

  @Override
  protected String toString(Object object) {
    return null;
  }
}
