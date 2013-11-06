package org.camunda.bpm.engine.test.mock;

import static com.google.common.base.Throwables.propagate;
import static org.mockito.Mockito.mock;

import java.util.Map;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.test.mock.answer.TaskListenerAnswer;
import org.mockito.Mockito;

public class FluentTaskListenerMock extends FluentMock<TaskListener, DelegateTask> implements TaskListener {

  public FluentTaskListenerMock() {
    super(mock(TaskListener.class), DelegateTask.class);
  }

  @Override
  public final void notify(final DelegateTask delegateTask) {
    mock.notify(delegateTask);
  }

  @Override
  public void onExecutionSetVariables(final Map<String, Object> variables) {
    doAnswer(new TaskListener() {

      @Override
      public void notify(final DelegateTask delegateTask) {
        setVariables(delegateTask, variables);
      }
    });
  }

  @Override
  public void onExecutionThrowBpmnError(final BpmnError bpmnError) {
    doAnswer(new TaskListener() {

      @Override
      public void notify(final DelegateTask delegateTask) {
        throw bpmnError;
      }
    });
  }

  private void doAnswer(final TaskListener taskListener) {
    try {
      Mockito.doAnswer(new TaskListenerAnswer(taskListener)).when(mock).notify(any());
    } catch (final Exception e) {
      propagate(e);
    }
  }
}
