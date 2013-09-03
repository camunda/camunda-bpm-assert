package org.camunda.bpm.engine.guard;

import java.io.Serializable;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

/**
 * Listener as a guard for tasks.
 * 
 * @author Simon Zambrovski, Holisticon AG
 * @author Jan Galinski, Holisticon AG
 */
public class TaskGuard implements TaskListener, Guard, Serializable {

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
	public void notify(final DelegateTask delegateTask) {
		GuardSupport.dispatch(this, delegateTask.getEventName(), delegateTask.getExecution());
	}

}
