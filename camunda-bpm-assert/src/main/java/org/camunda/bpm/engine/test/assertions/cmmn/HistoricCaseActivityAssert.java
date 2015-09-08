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
// TODO check if this class should be renamed to HistoricCaseActivityAssert
public class HistoricCaseActivityAssert extends AbstractCaseActivityAssert<HistoricCaseActivityAssert, HistoricCaseActivityInstance> {

	public static HistoricCaseActivityAssert assertThat(final ProcessEngine engine,
			final HistoricCaseActivityInstance actual) {
		return new HistoricCaseActivityAssert(engine, actual);
	}

	protected HistoricCaseActivityAssert(final ProcessEngine engine,
                                       final HistoricCaseActivityInstance actual) {
		super(engine, actual, HistoricCaseActivityAssert.class);
	}

	protected void assertInState(CaseExecutionState expectedState) {
		HistoricCaseActivityInstanceEntity actual = (HistoricCaseActivityInstanceEntity) getActual();
		int currentState = actual.getCaseActivityInstanceState();
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

	@Override
	protected CaseExecutionQuery caseExecutionQuery() {
		return super.caseExecutionQuery().activityId(
      getActual().getCaseActivityId());
	}

	@Override
	protected HistoricCaseActivityInstance getCurrent() {
		return historicCaseActivityInstanceQuery().singleResult();
	}

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
}
