package com.plexiti.activiti.test.fluent.assertions;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.fest.assertions.api.AbstractAssert;

/**
 * Fluent assertions for {@link org.camunda.bpm.engine.runtime.ProcessInstance}
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class ProcessInstanceAssert extends AbstractAssert<ProcessInstanceAssert, ProcessInstance> {

    protected ProcessInstanceAssert(ProcessInstance actual) {
        super(actual, ProcessInstance.class);
    }

    public static ProcessInstanceAssert assertThat(ProcessInstance actual) {
        return new ProcessInstanceAssert(actual);
    }
}
