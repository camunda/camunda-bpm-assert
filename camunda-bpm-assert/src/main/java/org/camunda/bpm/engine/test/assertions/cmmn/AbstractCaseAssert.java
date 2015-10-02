package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.AbstractObjectAssert;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstanceQuery;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.test.assertions.AbstractProcessAssert;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This base class provides common functions used or provided by all child classes.
 *
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
public abstract class AbstractCaseAssert<S extends AbstractCaseAssert<S, A>, A> extends AbstractProcessAssert<S, A> {

  protected AbstractCaseAssert(ProcessEngine engine, A actual, Class<?> selfType) {
    super(engine, actual, selfType);
  }

  public S isInState(CaseExecutionState expectedState) {
    assertThat(actualState())
      .overridingErrorMessage("Expecting %s to be active, but it is %s", actualType(), actualState())
      .isEqualTo(expectedState);
    return (S) this;
  }

  protected abstract CaseExecutionState actualState();

  protected abstract String actualType();

  /**
   * Returns an Assert object for the given variable. Fails if the variable is not found.
   *
   * @param name
   * 		name of the variable
   * @return Assert from the AssertJ framework
   */
  public AbstractObjectAssert<? extends AbstractObjectAssert, Object> variable(String name) {
    HistoricVariableInstance historicVariableInstance = getHistoricVariableInstanceQuery().variableName(name).singleResult();
    assertThat(historicVariableInstance).isNotNull();
    return assertThat(historicVariableInstance.getValue());
  }

  protected HistoricVariableInstanceQuery getHistoricVariableInstanceQuery() {return historyService().createHistoricVariableInstanceQuery();}
}
