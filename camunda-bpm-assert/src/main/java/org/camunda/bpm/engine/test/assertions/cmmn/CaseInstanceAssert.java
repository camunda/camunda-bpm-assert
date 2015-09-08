package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstanceQuery;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.impl.cmmn.execution.CmmnActivityExecution;
import org.camunda.bpm.engine.runtime.CaseExecutionQuery;
import org.camunda.bpm.engine.runtime.CaseInstance;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class
  CaseInstanceAssert extends
  AbstractCaseAssert<CaseInstanceAssert, CaseInstance> {

	protected CaseInstanceAssert(final ProcessEngine engine,
			final CaseInstance actual) {
		super(engine, actual, CaseInstanceAssert.class);
	}

	public static CaseInstanceAssert assertThat(final ProcessEngine engine,
			final CaseInstance actual) {
		return new CaseInstanceAssert(engine, actual);
	}

	public CaseInstanceAssert isActive() {
		assertInState(CaseExecutionState.ACTIVE);
		return this;
	}

	private void assertInState(CaseExecutionState expectedState) {
		CmmnActivityExecution caseExecution = (CmmnActivityExecution) caseExecutionQuery()
				.caseExecutionId(getActual().getId()).singleResult();
		CaseExecutionState currentState = caseExecution.getCurrentState();
		Assertions
				.assertThat(currentState)
				.overridingErrorMessage(
						"Expected case instance " + toString(getActual())
								+ " to be " + expectedState + ", but it is "
								+ currentState).isEqualTo(expectedState);
	}

  public CaseInstanceAssert isCompleted() {
    assertInState(CaseExecutionState.COMPLETED);
    return this;
  }

  //FIXME: untested in CaseInstanceAssertTest
  public CaseInstanceAssert isEnabled() {
    assertInState(CaseExecutionState.ENABLED);
    return this;
  }

  //FIXME: untested in CaseInstanceAssertTest
  @Override
  public AbstractCaseActivityAssert stage(String activityId) {
    return activity(activityId, CaseActivityType.STAGE);
  }

  @Override
  public AbstractCaseActivityAssert task(String activityId) {
    return activity(activityId, CaseActivityType.TASK);
  }

  //FIXME: untested in CaseInstanceAssertTest
  @Override
  protected HistoricCaseActivityInstanceQuery historicCaseActivityInstanceQuery() {
    return historyService().createHistoricCaseActivityInstanceQuery()
      .caseInstanceId(getActual().getCaseInstanceId());
  }

  //FIXME: untested in CaseInstanceAssertTest
  @Override
  protected CaseInstance getCurrent() {
    return caseInstanceQuery().singleResult();
  }

	@Override
	protected String toString(CaseInstance caseInstance) {
		return caseInstance != null ? String.format("%s {" + "id='%s', "
				+ "caseDefinitionId='%s', " + "businessKey='%s'" + "}",
				CaseInstance.class.getSimpleName(), caseInstance.getId(),
				caseInstance.getCaseDefinitionId(),
				caseInstance.getBusinessKey()) : null;
	}

  @Override
  protected CaseExecutionQuery caseExecutionQuery() {
    return super.caseExecutionQuery().caseInstanceId(
      getActual().getCaseInstanceId());
  }

}
