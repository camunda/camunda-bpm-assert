package org.camunda.bpm.engine.test.mock.answer;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
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
public class JavaDelegateAnswer extends AbstractAnswer<DelegateExecution> implements JavaDelegate {

  private final JavaDelegate javaDelegate;

  public JavaDelegateAnswer(final JavaDelegate javaDelegate) {
    this.javaDelegate = javaDelegate;
  }

  @Override
  public void execute(final DelegateExecution execution) throws Exception {
    javaDelegate.execute(execution);
  }

  @Override
  protected void answer(final DelegateExecution delegateExecution) throws Exception {
    execute(delegateExecution);
  }

}
