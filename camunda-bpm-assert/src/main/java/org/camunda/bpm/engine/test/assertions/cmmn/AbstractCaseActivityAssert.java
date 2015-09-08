package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;

/**
 * Created by Malte on 28.08.2015.
 */
public abstract class AbstractCaseActivityAssert<S extends AbstractCaseActivityAssert<S, A>, A> extends AbstractCaseAssert<S, A> {

  protected AbstractCaseActivityAssert(ProcessEngine engine, A actual, Class<?> selfType) {
    super(engine, actual, selfType);
  }

  @Override
  public A getActual() {
    return super.getActual();
  }

  public S isAvailable() {
    assertInState(CaseExecutionState.AVAILABLE);
    return (S) this;
  }

  protected abstract void assertInState(CaseExecutionState available);

  public S isActive() {
    assertInState(CaseExecutionState.ACTIVE);
    return (S) this;
  }

  public S isCompleted() {
    assertInState(CaseExecutionState.COMPLETED);
    return (S) this;
  }

  public S isEnabled() {
    assertInState(CaseExecutionState.ENABLED);
    return (S) this;
  }

  public S isTerminated() {
    assertInState(CaseExecutionState.TERMINATED);
    return (S) this;
  }

}
