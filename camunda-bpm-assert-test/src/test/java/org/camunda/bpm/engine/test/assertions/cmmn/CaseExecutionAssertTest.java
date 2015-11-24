package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cmmn.entity.runtime.CaseExecutionEntity;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseExecutionQuery;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
@RunWith(MockitoJUnitRunner.class)
public class CaseExecutionAssertTest {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  public static final String ACTUAL_CASE_EXECUTION_ID = "f00";

  @Mock
  ProcessEngine engine;

  @Mock
  CaseExecutionEntity actual;

  CaseExecutionAssert caseExecutionAssert;

  @Test
  public void assertInState_should_return_silently_when_actual_state_matches_expected_state() {
    //given: actual state is completed
    doReturn(CaseExecutionState.COMPLETED.getStateCode()).when(caseExecutionAssert).getActualState();

    //when asserting actual state is completed
    caseExecutionAssert.assertInState(CaseExecutionState.COMPLETED);

    //then nothing happens
  }

  @Test(expected = AssertionError.class)
  public void assertInState_should_throw_Exception_when_actual_state_does_not_match_expected_state() {
    //given: actual state is completed
    doReturn(CaseExecutionState.COMPLETED.getStateCode()).when(caseExecutionAssert).getActualState();

    //when asserting actual state is active
    caseExecutionAssert.assertInState(CaseExecutionState.ACTIVE);

    //then an expection is thrown
  }

  @Test
  public void caseExecutionQuery_should_use_actuals_id() {
    //given some CaseExecutionAssert for an CaseExecution with ID ACTUAL_CASE_EXECUTION_ID
    CaseService caseService = mock(CaseService.class);
    doReturn(caseService).when(engine).getCaseService();

    CaseExecutionQuery caseExecutionQuery = mock(CaseExecutionQuery.class);
    doReturn(caseExecutionQuery).when(caseService).createCaseExecutionQuery();

    doReturn(ACTUAL_CASE_EXECUTION_ID).when(actual).getId();

    //when calling caseExecutionQuery
    caseExecutionAssert.caseExecutionQuery();

    //then only the actual's ID should be used in the query
    verify(caseExecutionQuery).caseExecutionId(ACTUAL_CASE_EXECUTION_ID);
    verifyNoMoreInteractions(caseExecutionQuery);
  }

  @Test
  public void getActualState_should_use_actual_object() {
    //given an initialized CaseExecutionAssert

    //when calling getActualState
    caseExecutionAssert.getActualState();

    //then the actual objects state is used
    verify(actual).getState();
  }

  @Test
  public void getCurrent_should_use_caseExecutionQuery_to_fetch_most_current_data() {
    //given some CaseExecutionAssert for an existing CaseExecution
    CaseExecutionQuery caseExecutionQuery = mock(CaseExecutionQuery.class);
    doReturn(caseExecutionQuery).when(caseExecutionAssert).caseExecutionQuery();
    CaseExecution expectedCurrent = mock(CaseExecution.class);
    doReturn(expectedCurrent).when(caseExecutionQuery).singleResult();

    //when calling getCurrent
    CaseExecution actualCurrent = caseExecutionAssert.getCurrent();
    //actualCurrent - i like that name :)

    //then the single result from the caseExecutionQuery is returned
    verify(caseExecutionQuery).singleResult();
    assertThat(actualCurrent).isSameAs(expectedCurrent);
  }

  @Before
  public void prepareCaseExecutionAssert() {
    caseExecutionAssert = spy(new CaseExecutionAssert(engine, actual));
  }
}
