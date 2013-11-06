package org.camunda.bpm.engine.test.mock;

import static org.mockito.Mockito.mock;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.test.Expressions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FluentJavaDelegateMockTest {

  private static final String BEAN_NAME = "foo";
  private static final String MESSAGE = "message";

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void shouldThrowBpmnError() throws Exception {

    // expect exception
    thrown.expect(BpmnError.class);
    thrown.expectMessage(MESSAGE);

    Expressions.registerMockJavaDelegate(BEAN_NAME).onExecutionThrowBpmnError("code", MESSAGE);

    final JavaDelegate registeredDelegate = Expressions.getRegisteredJavaDelegate(BEAN_NAME);

    // test succeeds when exception is thrown
    registeredDelegate.execute(mock(DelegateExecution.class));
  }

}
