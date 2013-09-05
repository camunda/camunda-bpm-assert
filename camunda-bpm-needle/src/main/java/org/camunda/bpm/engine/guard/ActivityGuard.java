package org.camunda.bpm.engine.guard;

import java.io.Serializable;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

/**
 * Listener as a guard for services.
 * 
 * @author Simon Zambrovski, Holisticon AG
 * @author Jan Galinski, Holisticon AG
 */
public abstract class ActivityGuard implements ExecutionListener, Serializable, Guard {

  private static final long serialVersionUID = 1L;

  @Override
  public void checkPostcondions(final DelegateExecution execution) throws IllegalStateException {
    // intentionally empty
  }

  @Override
  public void checkPreconditions(final DelegateExecution execution) throws IllegalStateException {
    // intentionally empty
  }

  @Override
  public void notify(final DelegateExecution delegateExecution) throws Exception {
    GuardSupport.dispatch(this, delegateExecution.getEventName(), delegateExecution);
  }

}