package org.camunda.bpm.engine.test.assertions.cmmn;

import java.util.Map;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.MapAssert;
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

  /**
   * Verifies the expectation that the {@link HumanTask} is in {@link CaseExecutionState} 'suspended'.
   * 
   * @return this {@link HumanTaskAssert}
   */
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

  /**
   * Enter into a chained map assert inspecting the variables currently available in the context of the case instance
   * under test of this HumanTaskAssert.
   * 
   * @return MapAssert<String, Object> inspecting the human task variables. Inspecting an empty map in case no such variables
   *         are available.
   */
  public MapAssert<String, Object> variables() {
    return (MapAssert<String, Object>) Assertions.assertThat(vars());
  }

  /* Return variables map - independent of running/historic instance status */
  protected Map<String, Object> vars() {
    CaseExecution current = getCurrent();
    if (current != null) {
      return caseService().getVariables(current.getId());
    } else {
      return getHistoricVariablesMap();
    }
  }

  protected Map<String, Object> getHistoricVariablesMap() {
    throw new UnsupportedOperationException();
  }

}
