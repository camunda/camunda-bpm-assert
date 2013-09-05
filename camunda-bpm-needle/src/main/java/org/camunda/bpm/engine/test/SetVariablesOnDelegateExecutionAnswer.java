package org.camunda.bpm.engine.test;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Throwables.propagate;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

import java.util.Map;
import java.util.Map.Entry;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.test.fluent.support.Maps;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class SetVariablesOnDelegateExecutionAnswer implements Answer<Void> {

  public static void doSetVariablesOnExecute(final JavaDelegate mockInstance, final Object... variables) {
    doSetVariablesOnExecute(mockInstance, Maps.parseMap(variables));
  }

  public static void doSetVariablesOnExecute(final JavaDelegate mockInstance, final Map<String, Object> variables) {
    try {
      doAnswer(new SetVariablesOnDelegateExecutionAnswer(variables)).when(mockInstance).execute(any(DelegateExecution.class));
    } catch (final Exception e) {
      propagate(e);
    }
  }

  private final Map<String, Object> variables;

  private SetVariablesOnDelegateExecutionAnswer(final Map<String, Object> variables) {
    checkArgument(variables != null, "variables must not be null!");
    this.variables = variables;
  }

  @Override
  public Void answer(final InvocationOnMock invocation) throws Throwable {
    final DelegateExecution execution = (DelegateExecution) invocation.getArguments()[0];

    for (final Entry<String, Object> variable : variables.entrySet()) {
      execution.setVariable(variable.getKey(), variable.getValue());
    }

    return null;
  }
}
