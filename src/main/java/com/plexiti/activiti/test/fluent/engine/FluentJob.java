package com.plexiti.activiti.test.fluent.engine;

import org.camunda.bpm.engine.runtime.Job;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public interface FluentJob extends FluentDelegate<Job>, Job {

    void execute();

}
