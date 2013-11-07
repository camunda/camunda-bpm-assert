package org.camunda.bpm.engine.test.mock.verify;

import static org.mockito.Mockito.verify;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.mockito.verification.VerificationMode;

public class TaskListenerVerification extends AbstractMockitoVerification<TaskListener, DelegateTask> {

  public TaskListenerVerification(final TaskListener mock) {
    super(mock, DelegateTask.class);
  }

  @Override
  protected void doVerify(final VerificationMode verificationMode) throws Exception {
    verify(mock, verificationMode).notify(argumentCaptor.capture());
  }

}
