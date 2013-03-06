package com.plexiti.activiti.test.fluent.engine;

import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public interface FluentTask extends FluentDelegate<Task>, Task {

    FluentTask claim(String userId);

    FluentTask claim(User user);

    FluentTask complete(Object... variables);

}
