package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.AbstractObjectAssert;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstanceQuery;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.assertions.AbstractProcessAssert;
import org.camunda.bpm.engine.test.assertions.ProcessEngineTests;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This base class provides common functions used or provided by all child classes.
 *
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
public abstract class AbstractPlanItemAssert<S extends AbstractPlanItemAssert<S, A>, A extends AbstractPlanItemHolder> extends AbstractProcessAssert<S, A> {

  protected AbstractPlanItemAssert(ProcessEngine engine, A actual, Class<?> selfType) {
    super(engine, actual, selfType);
  }

  public S isInState(CaseExecutionState expectedState) {
    assertThat(actualState())
      .overridingErrorMessage("Expecting %s to be active, but it is %s", actualType(), actualState())
      .isEqualTo(expectedState);
    return (S) this;
  }

  public CaseExecutionState actualState() {
    return getActual().actualState();
  }

  public String actualType() {
    return getActual().actualType();
  }

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

  @Override
  protected A getCurrent() {
    CaseInstance caseInstance = caseInstanceQuery().caseInstanceId(getActual().getCaseInstanceId()).singleResult();
    return (A) ProcessEngineTests.planItemHolder(getActual().getId(), caseInstance, actualType(), getActual().getClass());
  }

  @Override
  protected String toString(A object) {
    return object.toString();
  }

}
