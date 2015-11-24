package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.verify;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractCaseActivityAssertTest {

  @Mock
  AbstractCaseActivityAssert abstractCaseActivityAssert;

  @Test
  public void isActive_should_delegate_to_assertInState() throws Exception {
    doCallRealMethod().when(abstractCaseActivityAssert).isActive();

    abstractCaseActivityAssert.isActive();

    verify(abstractCaseActivityAssert).assertInState(CaseExecutionState.ACTIVE);
  }

  @Test
  public void isAvailable_should_delegate_to_assertInState() throws Exception {
    doCallRealMethod().when(abstractCaseActivityAssert).isAvailable();

    abstractCaseActivityAssert.isAvailable();

    verify(abstractCaseActivityAssert).assertInState(CaseExecutionState.AVAILABLE);
  }

  @Test
  public void isCompleted_should_delegate_to_assertInState() throws Exception {
    doCallRealMethod().when(abstractCaseActivityAssert).isCompleted();

    abstractCaseActivityAssert.isCompleted();

    verify(abstractCaseActivityAssert).assertInState(CaseExecutionState.COMPLETED);
  }

  @Test
  public void isEnabled_should_delegate_to_assertInState() throws Exception {
    doCallRealMethod().when(abstractCaseActivityAssert).isEnabled();

    abstractCaseActivityAssert.isEnabled();

    verify(abstractCaseActivityAssert).assertInState(CaseExecutionState.ENABLED);
  }
}
