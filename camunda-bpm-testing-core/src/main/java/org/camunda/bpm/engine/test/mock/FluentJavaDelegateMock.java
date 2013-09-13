package org.camunda.bpm.engine.test.mock;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Throwables.propagate;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.Map;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public final class FluentJavaDelegateMock implements JavaDelegate {

  /**
   * @param key
   *          juel bean name to register the mock ("fooServiceBean").
   * @return fluent delegate for further use
   */
  public static FluentJavaDelegateMock registerMockDelegate(final String key) {
    return new FluentJavaDelegateMock().register(key);
  }

  /**
   * Helper method to get JavaDelegate from Mocks.
   * 
   * @param key
   *          the juel bean name used to register the delegate
   * @return the registered JavaDelegate
   */
  public static JavaDelegate getRegisteredDelegate(final String key) {
    return (JavaDelegate) Mocks.get(key);
  }

  public void onExecutionSetProcessVariables(final Map<String, Object> variables) {
    doAnswerWith(JavaDelegateAnswer.answerForProcessVariables(variables));
  }

  public void doAnswerWith(final JavaDelegate javaDelegate) {
    doAnswerWith(new JavaDelegateAnswer(javaDelegate));
  }

  public void doAnswerWith(final JavaDelegateAnswer javaDelegateAnswer) {
    try {
      doAnswer(javaDelegateAnswer).when(javaDelegateMock).execute(delegateExecution());
    } catch (final Exception e) {
      propagate(e);
    }
  }

  public void onExecutionThrowBpmnError(final String errorCode) {
    onExecutionThrowBpmnError(new BpmnError(errorCode));
  }

  public void onExecutionThrowBpmnError(final String errorCode, final String message) {
    onExecutionThrowBpmnError(new BpmnError(errorCode, message));
  }

  public void onExecutionThrowBpmnError(final BpmnError bpmnError) {
    checkArgument(bpmnError != null, "bpmnError must not be null!");

    try {
      doThrow(bpmnError).when(javaDelegateMock).execute(delegateExecution());
    } catch (final Exception e) {
      propagate(e);
    }
  }

  public FluentJavaDelegateMock register(final String key) {
    Mocks.register(key, javaDelegateMock);
    return this;
  }

  private final JavaDelegate javaDelegateMock = mock(JavaDelegate.class);

  /**
   * 
   * @return stub for any delegate execution
   */
  private final DelegateExecution delegateExecution() {
    return any(DelegateExecution.class);
  }

  @Override
  public void execute(final DelegateExecution execution) throws Exception {

    javaDelegateMock.execute(execution);
  }

}
