package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public abstract class AbstractCaseActivityAssert<S extends AbstractCaseActivityAssert<S, A>, A> extends AbstractCaseAssert<S, A> {

  protected AbstractCaseActivityAssert(ProcessEngine engine, A actual, Class<?> selfType) {
    super(engine, actual, selfType);
  }

  public S isActive() {
    assertInState(CaseExecutionState.ACTIVE);
    return (S) this;
  }

  protected abstract void assertInState(CaseExecutionState available);

  public S isAvailable() {
    assertInState(CaseExecutionState.AVAILABLE);
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

  public S isDisabled() {
    assertInState(CaseExecutionState.DISABLED);
    return (S) this;
  }

}
