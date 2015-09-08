package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstanceQuery;
import org.camunda.bpm.engine.impl.cmmn.entity.runtime.CaseExecutionEntity;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseExecutionQuery;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class CaseExecutionAssert extends AbstractCaseActivityAssert<CaseExecutionAssert, CaseExecution> {

  protected CaseExecutionAssert(final ProcessEngine engine,
                                final CaseExecution actual) {
    super(engine, actual, CaseExecutionAssert.class);
  }

  public static CaseExecutionAssert assertThat(final ProcessEngine engine,
                                               final CaseExecution actual) {
    return new CaseExecutionAssert(engine, actual);
  }

  @Override
  protected void assertInState(CaseExecutionState expectedState) {
    int actualState = getActualState();
    Assertions
      .assertThat(actualState)
      .overridingErrorMessage(
        "Expected task "
          + toString(getActual())
          + " to be "
          + expectedState
          + ", but it is "
          + CaseExecutionState.CASE_EXECUTION_STATES
          .get(actualState))
      .isEqualTo(expectedState.getStateCode());
  }

  protected int getActualState() {return ((CaseExecutionEntity) actual).getState();}

  @Override
  protected CaseExecution getCurrent() {
    return caseExecutionQuery().singleResult();
  }

  @Override
  protected String toString(CaseExecution caseExecution) {
    return caseExecution != null ? String.format("%s {" + "id='%s', "
        + "caseDefinitionId='%s', " + "activityType='%s'" + "}",
      caseExecution.getClass().getSimpleName(),
      caseExecution.getId(), caseExecution.getCaseDefinitionId(),
      caseExecution.getActivityType()) : null;
  }

  @Override
  protected CaseExecutionQuery caseExecutionQuery() {
    return super.caseExecutionQuery().caseExecutionId(actual.getId());
  }

  //FIXME: untested in CaseExecutionAssertTest
  @Override
  protected HistoricCaseActivityInstanceQuery historicCaseActivityInstanceQuery() {
    return historyService().createHistoricCaseActivityInstanceQuery()
      .caseActivityInstanceId(getActual().getId());
  }
}
