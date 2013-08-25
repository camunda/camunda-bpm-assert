package org.camunda.bdd.examples.simple.guard;

import static org.camunda.bpm.engine.guard.GuardSupport.checkIsSet;
import org.camunda.bdd.examples.simple.SimpleProcess.Variables;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.guard.ActivityGuard;

/**
 * Guard of the isAutomatic variable.
 */
public class AutomaticProcessingGuard extends ActivityGuard {

    private static final long serialVersionUID = 1L;

    @Override
    public void checkPostcondions(final DelegateExecution execution) throws IllegalStateException {
        checkIsSet(execution, Variables.IS_AUTOMATIC);
    }
}
