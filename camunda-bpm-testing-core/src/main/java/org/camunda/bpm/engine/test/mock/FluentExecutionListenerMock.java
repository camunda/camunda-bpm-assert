package org.camunda.bpm.engine.test.mock;

import static com.google.common.base.Throwables.propagate;
import static org.mockito.Mockito.mock;

import java.util.Map;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.test.mock.answer.ExecutionListenerAnswer;
import org.mockito.Mockito;

public class FluentExecutionListenerMock extends FluentMock<ExecutionListener, DelegateExecution> implements ExecutionListener {

  public FluentExecutionListenerMock() {
    super(mock(ExecutionListener.class), DelegateExecution.class);
  }

  @Override
  public void onExecutionSetVariables(final Map<String, Object> variables) {
    doAnswer(new ExecutionListener() {

      @Override
      public void notify(final DelegateExecution execution) throws Exception {
        setVariables(execution, variables);
      }
    });
  }

  @Override
  public void onExecutionThrowBpmnError(final BpmnError bpmnError) {
    doAnswer(new ExecutionListener() {

      @Override
      public void notify(final DelegateExecution execution) throws Exception {
        throw bpmnError;
      }
    });
  }

  @Override
  public void notify(final DelegateExecution execution) throws Exception {
    mock.notify(execution);
  }

  private void doAnswer(final ExecutionListener executionListener) {
    try {
      Mockito.doAnswer(new ExecutionListenerAnswer(executionListener)).when(mock).notify(any());
    } catch (final Exception e) {
      propagate(e);
    }
  }

}
