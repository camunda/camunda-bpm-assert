package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.model.cmmn.instance.CaseTask;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class CaseTaskAssert extends AbstractCaseAssert<CaseTaskAssert, CaseExecution> {

  protected CaseTaskAssert(final ProcessEngine engine, final CaseExecution actual) {
    super(engine, actual, CaseTaskAssert.class);
  }

  protected static CaseTaskAssert assertThat(final ProcessEngine engine, final CaseExecution actual) {
    return new CaseTaskAssert(engine, actual);
  }

  /**
   * Verifies the expectation that the {@link CaseTask} is in {@link CaseExecutionState} 'available'.
   * 
   * @return this {@link CaseTaskAssert}
   */
  @Override
  public CaseTaskAssert isAvailable() {
    return super.isAvailable();
  }

  /**
   * Verifies the expectation that the {@link CaseTask} is in {@link CaseExecutionState} 'enabled'.
   * 
   * @return this {@link CaseTaskAssert}
   */
  @Override
  public CaseTaskAssert isEnabled() {
    return super.isEnabled();
  }

  /**
   * Verifies the expectation that the {@link CaseTask} is in {@link CaseExecutionState} 'disabled'.
   * 
   * @return this {@link CaseTaskAssert}
   */
  @Override
  public CaseTaskAssert isDisabled() {
    return super.isDisabled();
  }

  /**
   * Verifies the expectation that the {@link CaseTask} is in {@link CaseExecutionState} 'active'.
   * 
   * @return this {@link CaseTaskAssert}
   */
  @Override
  public CaseTaskAssert isActive() {
    return super.isActive();
  }

  /**
   * Verifies the expectation that the {@link CaseTask} is in {@link CaseExecutionState} 'suspended'.
   * 
   * @return this {@link CaseTaskAssert}
   */
  @Override
  public CaseTaskAssert isSuspended() {
    return super.isSuspended();
  }

  /**
   * Verifies the expectation that the {@link CaseTask} is in {@link CaseExecutionState} 'completed'.
   * 
   * @return this {@link CaseTaskAssert}
   */
  @Override
  public CaseTaskAssert isCompleted() {
    return super.isCompleted();
  }

  /**
   * Verifies the expectation that the {@link CaseTask} is in {@link CaseExecutionState} 'failed'.
   * 
   * @return this {@link CaseTaskAssert}
   */
  @Override
  public CaseTaskAssert isFailed() {
    return super.isFailed();
  }

  /**
   * Verifies the expectation that the {@link CaseTask} is in {@link CaseExecutionState} 'terminated'.
   * 
   * @return this {@link CaseTaskAssert}
   */
  @Override
  public CaseTaskAssert isTerminated() {
    return super.isTerminated();
  }

}
