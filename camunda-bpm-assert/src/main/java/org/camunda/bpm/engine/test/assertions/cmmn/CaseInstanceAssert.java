package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstanceQuery;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.impl.cmmn.execution.CmmnActivityExecution;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseExecutionQuery;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.assertions.AbstractProcessAssert;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenter@holisticon.de>
 */
public class CaseInstanceAssert extends AbstractProcessAssert<CaseInstanceAssert, CaseInstance> {

  protected CaseInstanceAssert(final ProcessEngine engine, final CaseInstance actual) {
    super(engine, actual, CaseInstanceAssert.class);
  }

  public static CaseInstanceAssert assertThat(final ProcessEngine engine, final CaseInstance actual) {
    return new CaseInstanceAssert(engine, actual);
  }

  public CaseInstanceAssert isActive() {
    assertInState(CaseExecutionState.ACTIVE);
    return this;
  }

  public CaseInstanceAssert isCompleted() {
    assertInState(CaseExecutionState.COMPLETED);
    return this;
  }

  public CaseTaskAssert task(String activityId) {
    HistoricCaseActivityInstance activityInstance = historicCaseActivityInstanceQuery().caseActivityId(activityId).singleResult();
    Assertions.assertThat(activityInstance).overridingErrorMessage("Task '" + activityId + "' not found!").isNotNull();
    return new CaseTaskAssert(engine, activityInstance);
  }

  private void assertInState(CaseExecutionState expectedState) {
    CmmnActivityExecution caseExecution = (CmmnActivityExecution) caseExecutionQuery().caseExecutionId(getActual().getId()).singleResult();
    CaseExecutionState currentState = caseExecution.getCurrentState();
    Assertions.assertThat(currentState)
      .overridingErrorMessage("Expected case instance " + toString(getActual()) + " to be " + expectedState + ", but it is " + currentState)
      .isEqualTo(expectedState);
  }

  @Override
  protected CaseExecutionQuery caseExecutionQuery() {
    return super.caseExecutionQuery().caseInstanceId(getActual().getCaseInstanceId());
  }

  @Override
  protected CaseInstance getCurrent() {
    return caseInstanceQuery().singleResult();
  }

  private HistoricCaseActivityInstanceQuery historicCaseActivityInstanceQuery() {
    return historyService().createHistoricCaseActivityInstanceQuery().caseInstanceId(getActual().getCaseInstanceId());
  }

  @Override
  protected String toString(CaseInstance caseInstance) {
    return caseInstance != null ?
      String.format("%s {" +
          "id='%s', " +
          "caseDefinitionId='%s', " +
          "businessKey='%s'" +
          "}",
        CaseInstance.class.getSimpleName(),
        caseInstance.getId(),
        caseInstance.getCaseDefinitionId(),
        caseInstance.getBusinessKey())
      : null;
  }

}
