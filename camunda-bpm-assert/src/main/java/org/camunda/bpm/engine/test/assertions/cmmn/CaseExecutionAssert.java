package org.camunda.bpm.engine.test.assertions.cmmn;

import org.apache.ibatis.annotations.Case;
import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstanceQuery;
import org.camunda.bpm.engine.impl.cmmn.entity.runtime.CaseExecutionEntity;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricCaseActivityInstanceEntity;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseExecutionQuery;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
// TODO check if this class should be renamed to HistoricCaseActivityAssert
public class CaseExecutionAssert extends AbstractCaseActivityAssert<CaseExecutionAssert, CaseExecution> {

	public static CaseExecutionAssert assertThat(final ProcessEngine engine,
			final CaseExecution actual) {
		return new CaseExecutionAssert(engine, actual);
	}

	protected CaseExecutionAssert(final ProcessEngine engine,
                                final CaseExecution actual) {
		super(engine, actual, CaseExecutionAssert.class);
	}


	protected void assertInState(CaseExecutionState expectedState) {
		int currentState = ((CaseExecutionEntity)actual).getState();
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
		return super.caseExecutionQuery().caseExecutionId(actual.getId());
	}

	@Override
	protected CaseExecution getCurrent() {
		return caseExecutionQuery().singleResult();
	}

	protected HistoricCaseActivityInstanceQuery historicCaseActivityInstanceQuery() {
		return historyService().createHistoricCaseActivityInstanceQuery()
				.caseActivityInstanceId(getActual().getId());
	}

	@Override
	protected String toString(CaseExecution caseExecution) {
		return caseExecution != null ? String.format("%s {" + "id='%s', "
        + "caseDefinitionId='%s', " + "activityType='%s'" + "}",
      caseExecution.getClass().getSimpleName(),
      caseExecution.getId(), caseExecution.getCaseDefinitionId(),
      caseExecution.getActivityType()) : null;
	}
}
