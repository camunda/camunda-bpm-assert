package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.runtime.Job;

/**
 * @author Martin Schimak <martin.schimak@camunda.com>
 * @author Rafael Cordones <rafael.cordones@camunda.com>
 */
public interface FluentJob extends FluentDelegate<Job>, Job {

    FluentJob execute();

}
