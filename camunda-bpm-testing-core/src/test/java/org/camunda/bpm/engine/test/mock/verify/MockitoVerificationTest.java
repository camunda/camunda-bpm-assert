package org.camunda.bpm.engine.test.mock.verify;

import static org.camunda.bpm.engine.test.DelegateExpressions.verifyJavaDelegate;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.test.Expressions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class MockitoVerificationTest {

  private static final String JAVA_DELEGATE = "javaDelegate";

  private JavaDelegate javaDelegate = Expressions.registerMockInstance(JavaDelegate.class);

  @Mock
  private DelegateExecution delegateExecution;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @Test
  public void shouldVerifyExecuteCalled() throws Exception {
    javaDelegate.execute(delegateExecution);

    verifyJavaDelegate(JAVA_DELEGATE).executed();
  }

  @Test
  public void shouldVerifyExecuteCalledTwice() throws Exception {
    javaDelegate.execute(delegateExecution);
    javaDelegate.execute(delegateExecution);

    verifyJavaDelegate(JAVA_DELEGATE).executed(times(2));
  }

  @Test
  public void shouldVerifyExecuteNotCalled() throws Exception {

    verifyJavaDelegate(JAVA_DELEGATE).executedNever();
  }
}
