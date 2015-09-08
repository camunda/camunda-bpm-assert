package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstanceQuery;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricCaseActivityInstanceEntity;
import org.camunda.bpm.engine.runtime.CaseExecutionQuery;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class HistoricCaseActivityAssert extends AbstractCaseActivityAssert<HistoricCaseActivityAssert, HistoricCaseActivityInstance> {

  protected HistoricCaseActivityAssert(final ProcessEngine engine,
                                       final HistoricCaseActivityInstance actual) {
    super(engine, actual, HistoricCaseActivityAssert.class);
  }

	public static HistoricCaseActivityAssert assertThat(final ProcessEngine engine,
			final HistoricCaseActivityInstance actual) {
		return new HistoricCaseActivityAssert(engine, actual);
	}

  @Override
  protected void assertInState(CaseExecutionState expectedState) {
    int currentState = getActualState();
    Assertions
      .assertThat(currentState)
      .overridingErrorMessage(
        "Expected task "
          + toString(getActual())
          + " to be "
          + expectedState
          + ", but it is "
          + CaseExecutionState.CASE_EXECUTION_STATES
          .get(currentState))
      .isEqualTo(expectedState.getStateCode());
	}

  protected int getActualState() {return ((HistoricCaseActivityInstanceEntity) getActual()).getCaseActivityInstanceState();}

	@Override
  protected HistoricCaseActivityInstance getCurrent() {
    return historicCaseActivityInstanceQuery().singleResult();
  }

  //FIXME: untested in HistoricCaseActivityAssertTest
  @Override
  protected HistoricCaseActivityInstanceQuery historicCaseActivityInstanceQuery() {
    return historyService().createHistoricCaseActivityInstanceQuery()
      .caseActivityInstanceId(getActual().getId());
  }

	@Override
	protected String toString(HistoricCaseActivityInstance caseExecution) {
		return caseExecution != null ? String.format("%s {" + "id='%s', "
				+ "caseDefinitionId='%s', " + "activityType='%s'" + "}",
				caseExecution.getClass().getSimpleName(),
				caseExecution.getId(), caseExecution.getCaseDefinitionId(),
				caseExecution.getCaseActivityType()) : null;
  }

  @Override
  protected CaseExecutionQuery caseExecutionQuery() {
    return super.caseExecutionQuery().activityId(
      getActual().getCaseActivityId());
  }
}
