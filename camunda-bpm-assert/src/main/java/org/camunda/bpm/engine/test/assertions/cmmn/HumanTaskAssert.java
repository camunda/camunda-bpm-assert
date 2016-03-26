package org.camunda.bpm.engine.test.assertions.cmmn;

import java.util.Arrays;
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
   * under test of this AbstractCaseAssert.
   * 
   * @return MapAssert<String, Object> inspecting the case instance variables. Inspecting an empty map in case no such variables
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

  /**
   * Verifies the expectation that the {@link CaseExecution} holds one or 
   * more case variables with the specified names. 
   *
   * @param   names the names of the case variables expected to exist. In
   *          case no variable name is given, the existence of at least one
   *          variable will be verified.
   * @return  this {@link AbstractCaseAssert}
   */
  public HumanTaskAssert hasVariables(final String... names) {
    return hasVars(names);
  }

  /**
   * Verifies the expectation that the {@link CaseExecution} holds no 
   * case variables at all.
   *
   * @return  this {@link AbstractCaseAssert}
   */
  public HumanTaskAssert hasNoVariables() {
    return hasVars(null);
  }

  private HumanTaskAssert hasVars(String[] names) {
    boolean shouldHaveVariables = names != null;
    boolean shouldHaveSpecificVariables = names != null && names.length > 0;

    Map<String, Object> vars = vars();
    StringBuffer message = new StringBuffer();
    message.append("Expecting %s to hold ");
    message.append(shouldHaveVariables ? "case variables" + (shouldHaveSpecificVariables ? " %s, "
        : ", ")
        : "no variables at all, ");
    message.append("instead we found it to hold " + (vars.isEmpty() ? "no variables at all."
        : "the variables %s."));
    if (vars.isEmpty() && getCurrent() == null)
      message.append(" (Please make sure you have set the history " + "service of the engine to at least 'audit' or a higher level "
          + "before making use of this assertion for historic instances!)");

    MapAssert<String, Object> assertion = variables().overridingErrorMessage(
        message.toString(),
        toString(actual),
        shouldHaveSpecificVariables ? Arrays.asList(names)
            : vars.keySet(),
        vars.keySet());
    if (shouldHaveVariables) {
      if (shouldHaveSpecificVariables) {
        assertion.containsKeys(names);
      } else {
        assertion.isNotEmpty();
      }
    } else {
      assertion.isEmpty();
    }
    return this;
  }

}
