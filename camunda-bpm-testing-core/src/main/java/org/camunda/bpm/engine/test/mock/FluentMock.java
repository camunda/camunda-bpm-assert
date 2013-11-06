package org.camunda.bpm.engine.test.mock;

import java.util.Map;
import java.util.Map.Entry;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.fluent.support.ProcessVariableMaps;
import org.mockito.Mockito;

abstract class FluentMock<T, P extends VariableScope> {

  protected final T mock;
  protected final Class<P> parameterType;

  protected FluentMock(final T delegate, final Class<P> parameterType) {
    this.mock = delegate;
    this.parameterType = parameterType;
  }

  protected void setVariables(final VariableScope variableScope, final Map<String, Object> variables) {
    for (final Entry<String, Object> variable : variables.entrySet()) {
      variableScope.setVariable(variable.getKey(), variable.getValue());
    }
  }

  public final void onExecutionSetVariables(final Object... keyValuePairs) {
    onExecutionSetVariables(ProcessVariableMaps.parseMap(keyValuePairs));
  }

  public abstract void onExecutionSetVariables(Map<String, Object> variables);

  public void onExecutionThrowBpmnError(final String errorCode) {
    onExecutionThrowBpmnError(new BpmnError(errorCode));
  }

  public void onExecutionThrowBpmnError(final String errorCode, final String message) {
    onExecutionThrowBpmnError(new BpmnError(errorCode, message));
  }

  public abstract void onExecutionThrowBpmnError(final BpmnError bpmnError);

  public T getMock() {
    return mock;
  }

  protected P any() {
    return Mockito.any(parameterType);
  }
}
