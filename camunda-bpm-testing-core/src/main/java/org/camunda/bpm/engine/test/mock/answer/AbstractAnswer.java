package org.camunda.bpm.engine.test.mock.answer;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Specialized {@link Answer} that takes the single argument of an execute() or
 * notify() method and delegates to type safe call.
 * 
 * @param <T>
 *          can be {@link DelegateExecution} or {@link DelegateTask}
 */
abstract class AbstractAnswer<T extends VariableScope> implements Answer<Void> {

  @Override
  @SuppressWarnings("unchecked")
  public final Void answer(final InvocationOnMock invocation) throws Throwable {
    answer((T) invocation.getArguments()[0]);
    return null;
  }

  protected abstract void answer(T parameter) throws Exception;

}
