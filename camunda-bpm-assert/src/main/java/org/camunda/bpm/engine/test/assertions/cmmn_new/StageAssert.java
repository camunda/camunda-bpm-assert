package org.camunda.bpm.engine.test.assertions.cmmn_new;

import org.camunda.bpm.engine.ProcessEngine;

/**
 * Created by Malte on 18.09.2015.
 */
public class StageAssert extends AbstractCaseAssert<StageAssert, Object> {

  protected StageAssert(ProcessEngine engine, Object actual, Class<?> selfType) {
    super(engine, actual, selfType);
  }

  public StageAssert isActive() {
    return this;
  }

  public StageAssert isAvailable() {
    return this;
  }

  public StageAssert isCompleted() {
    return this;
  }

  public StageAssert isEnabled() {
    return this;
  }

  public TaskAssert task(String id) {
    return null;
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
