package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.model.cmmn.instance.HumanTask;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class HumanTaskAssert extends AbstractCaseAssert<HumanTaskAssert, CaseExecution> {

  protected HumanTaskAssert(final ProcessEngine engine, final CaseExecution actual) {
    super(engine, actual, HumanTaskAssert.class);
  }

  protected static HumanTaskAssert assertThat(final ProcessEngine engine, final CaseExecution actual) {
    return new HumanTaskAssert(engine, actual);
  }

  /**
   * Verifies the expectation that the {@link HumanTask} is in {@link CaseExecutionState} 'available'.
   * 
   * @return this {@link HumanTaskAssert}
   */
  @Override
  public HumanTaskAssert isAvailable() {
    return super.isAvailable();
  }

  /**
   * Verifies the expectation that the {@link HumanTask} is in {@link CaseExecutionState} 'enabled'.
   * 
   * @return this {@link HumanTaskAssert}
   */
  @Override
  public HumanTaskAssert isEnabled() {
    return super.isEnabled();
  }

  /**
   * Verifies the expectation that the {@link HumanTask} is in {@link CaseExecutionState} 'disabled'.
   * 
   * @return this {@link HumanTaskAssert}
   */
  @Override
  public HumanTaskAssert isDisabled() {
    return super.isDisabled();
  }

  /**
   * Verifies the expectation that the {@link HumanTask} is in {@link CaseExecutionState} 'active'.
   * 
   * @return this {@link HumanTaskAssert}
   */
  @Override
  public HumanTaskAssert isActive() {
    return super.isActive();
  }

  @Override
  public HumanTaskAssert isSuspended() {
    return super.isSuspended();
  }

  /**
   * Verifies the expectation that the {@link HumanTask} is in {@link CaseExecutionState} 'completed'.
   * 
   * @return this {@link HumanTaskAssert}
   */
  @Override
  public HumanTaskAssert isCompleted() {
    return super.isCompleted();
  }

  /**
   * Verifies the expectation that the {@link HumanTask} is in {@link CaseExecutionState} 'failed'.
   * 
   * @return this {@link HumanTaskAssert}
   */
  @Override
  public HumanTaskAssert isFailed() {
    return super.isFailed();
  }

  /**
   * Verifies the expectation that the {@link HumanTask} is in {@link CaseExecutionState} 'terminated'.
   * 
   * @return this {@link HumanTaskAssert}
   */
  @Override
  public HumanTaskAssert isTerminated() {
    return super.isTerminated();
  }

}
