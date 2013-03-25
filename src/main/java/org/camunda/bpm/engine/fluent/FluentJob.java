package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.runtime.Job;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public interface FluentJob extends FluentDelegate<Job>, Job {

    FluentJob execute();

}
