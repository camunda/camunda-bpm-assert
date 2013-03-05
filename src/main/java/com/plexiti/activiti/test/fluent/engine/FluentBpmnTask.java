package com.plexiti.activiti.test.fluent.engine;

import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public interface FluentBpmnTask extends FluentBpmnDelegate<Task>, Task {

    void claim(String userId);

    void claim(User user);

    void complete(Object... variables);

}
