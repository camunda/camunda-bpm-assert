package org.camunda.bpm.engine.test.mock;

import java.util.Map;
import java.util.Map.Entry;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * This is a specialized {@link Answer} that delegates to the given
 * {@link JavaDelegate}. When using an JavaDelegate-Mock, this Answer can be
 * used to implement internal behavior of the mock by delegating the method call
 * to the given delegate instance.
 * 
 * @author Jan Galinski, Holisticon AG
 * 
 */
public class JavaDelegateAnswer implements Answer<Void>, JavaDelegate {

  public static JavaDelegateAnswer answerForProcessVariables(final Map<String, Object> variables) {
    return new JavaDelegateAnswer(new JavaDelegate() {

      @Override
      public void execute(final DelegateExecution execution) throws Exception {
        for (final Entry<String, Object> variable : variables.entrySet()) {
          execution.setVariable(variable.getKey(), variable.getValue());
        }
      }
    });
  }

  private final JavaDelegate javaDelegate;

  public JavaDelegateAnswer(final JavaDelegate javaDelegate) {
    this.javaDelegate = javaDelegate;
  }

  @Override
  public Void answer(final InvocationOnMock invocation) throws Throwable {
    execute((DelegateExecution) invocation.getArguments()[0]);

    return null;
  }

  @Override
  public void execute(final DelegateExecution execution) throws Exception {
    javaDelegate.execute(execution);
  }

}
