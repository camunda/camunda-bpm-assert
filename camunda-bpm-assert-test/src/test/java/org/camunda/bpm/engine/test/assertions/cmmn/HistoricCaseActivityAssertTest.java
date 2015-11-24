package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstanceQuery;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.runtime.CaseExecutionQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
@RunWith(MockitoJUnitRunner.class)
public class HistoricCaseActivityAssertTest {


  public static final String ACTUAL_ACTIVITY_ID = "f00";

  @Mock
  ProcessEngine engine;

  @Mock
  HistoricCaseActivityInstance actual;

  HistoricCaseActivityAssert historicCaseActivityAssert;

  @Test
  public void assertInState_should_return_silently_when_actual_state_matches_expected_state() {
    //given: actual state is completed
    doReturn(CaseExecutionState.COMPLETED.getStateCode()).when(historicCaseActivityAssert).getActualState();

    //when asserting actual state is completed
    historicCaseActivityAssert.assertInState(CaseExecutionState.COMPLETED);

    //then nothing happens
  }

  @Test(expected = AssertionError.class)
  public void assertInState_should_throw_Exception_when_actual_state_does_not_match_expected_state() {
    //given: actual state is completed
    doReturn(CaseExecutionState.COMPLETED.getStateCode()).when(historicCaseActivityAssert).getActualState();

    //when asserting actual state is active
    historicCaseActivityAssert.assertInState(CaseExecutionState.ACTIVE);

    //then an exception is thrown
  }

  @Test
  public void caseExecutionQuery_should_use_actuals_activity_id() {
    //given some HistoricCaseActivityAssert for an HistoricCaseActivity with activity ID ACTUAL_ACTIVITY_ID
    CaseService caseService = mock(CaseService.class);
    doReturn(caseService).when(engine).getCaseService();

    CaseExecutionQuery caseExecutionQuery = mock(CaseExecutionQuery.class);
    doReturn(caseExecutionQuery).when(caseService).createCaseExecutionQuery();

    doReturn(ACTUAL_ACTIVITY_ID).when(actual).getCaseActivityId();

    //when calling caseExecutionQuery
    historicCaseActivityAssert.caseExecutionQuery();

    //then only the actual's ID should be used in the query
    verify(caseExecutionQuery).activityId(ACTUAL_ACTIVITY_ID);
    verifyNoMoreInteractions(caseExecutionQuery);
  }

  @Test
  public void getCurrent_should_use_historicCaseActivityQuery_to_fetch_most_current_data() {
    //given some CaseExecutionAssert for an existing CaseExecution
    HistoricCaseActivityInstanceQuery historicCaseActivityInstanceQuery = mock(HistoricCaseActivityInstanceQuery.class);
    doReturn(historicCaseActivityInstanceQuery).when(historicCaseActivityAssert).historicCaseActivityInstanceQuery();
    HistoricCaseActivityInstance expectedCurrent = mock(HistoricCaseActivityInstance.class);
    doReturn(expectedCurrent).when(historicCaseActivityInstanceQuery).singleResult();

    //when calling getCurrent
    HistoricCaseActivityInstance actualCurrent = historicCaseActivityAssert.getCurrent();
    //actualCurrent - i like that name :)

    //then the single result from the caseExecutionQuery is returned
    verify(historicCaseActivityInstanceQuery).singleResult();
    assertThat(actualCurrent).isSameAs(expectedCurrent);
  }

  @Before
  public void prepareHistoricCaseActivityAssert() {
    historicCaseActivityAssert = spy(new HistoricCaseActivityAssert(engine, actual));
  }

}
