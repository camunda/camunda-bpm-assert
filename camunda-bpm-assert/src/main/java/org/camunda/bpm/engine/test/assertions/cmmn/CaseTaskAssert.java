package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstanceQuery;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.impl.cmmn.execution.CmmnActivityExecution;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricCaseActivityInstanceEntity;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseExecutionQuery;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.assertions.AbstractProcessAssert;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenter@holisticon.de>
 */
public class CaseTaskAssert extends AbstractProcessAssert<CaseTaskAssert, HistoricCaseActivityInstance> {

  protected CaseTaskAssert(final ProcessEngine engine, final HistoricCaseActivityInstance actual) {
    super(engine, actual, CaseTaskAssert.class);
  }

  public CaseTaskAssert isActive() {
    assertInState(CaseExecutionState.ACTIVE);
    return this;
  }

  public CaseTaskAssert isCompleted() {
    assertInState(CaseExecutionState.COMPLETED);
    return this;
  }

  private void assertInState(CaseExecutionState expectedState) {
    HistoricCaseActivityInstanceEntity actual = (HistoricCaseActivityInstanceEntity) getActual();
    int currentState = actual.getCaseActivityInstanceState();
    Assertions.assertThat(currentState)
      .overridingErrorMessage("Expected task " + toString(getActual()) + " to be " + expectedState + ", but it is " + CaseExecutionState.CASE_EXECUTION_STATES.get(actual))
      .isEqualTo(expectedState.getStateCode());
  }

  @Override
  protected CaseExecutionQuery caseExecutionQuery() {
    return super.caseExecutionQuery().activityId(getActual().getCaseActivityId());
  }

  @Override
  protected HistoricCaseActivityInstance getCurrent() {
    return historicCaseActivityInstanceQuery().singleResult();
  }

  private HistoricCaseActivityInstanceQuery historicCaseActivityInstanceQuery() {
    return historyService().createHistoricCaseActivityInstanceQuery().caseActivityInstanceId(getActual().getId());
  }

  @Override
  protected String toString(HistoricCaseActivityInstance caseExecution) {
    return caseExecution != null ?
      String.format("%s {" +
          "id='%s', " +
          "caseDefinitionId='%s', " +
          "activityType='%s'" +
          "}",
        caseExecution.getClass().getSimpleName(),
        caseExecution.getId(),
        caseExecution.getCaseDefinitionId(),
        caseExecution.getCaseActivityType())
      : null;
  }
}
