package org.camunda.bpm.engine.test.assertions.cmmn_new;

import org.camunda.bpm.engine.ProcessEngine;

/**
 * Created by Malte on 18.09.2015.
 */
public class MilestoneAssert extends AbstractCaseAssert<MilestoneAssert, Object> {

  protected MilestoneAssert(ProcessEngine engine, Object actual, Class selfType) {
    super(engine, actual, selfType);
  }

  public MilestoneAssert isAvailable() {
    return this;
  }

  public MilestoneAssert isCompleted() {
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
