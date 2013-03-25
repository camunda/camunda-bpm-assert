package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;

/**
 * @author Martin Schimak <martin.schimak@camunda.com>
 * @author Rafael Cordones <rafael.cordones@camunda.com>
 */
public interface FluentTask extends FluentDelegate<Task>, Task {

    FluentTask claim(String userId);

    FluentTask claim(User user);

    FluentTask complete(Object... variables);

}
