package com.plexiti.activiti.test.fluent.assertions;

import com.plexiti.activiti.test.fluent.TestProcessInstance;
import org.fest.assertions.api.AbstractAssert;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public class TestProcessInstanceAssert extends AbstractAssert<TestProcessInstanceAssert, TestProcessInstance> {

    protected TestProcessInstanceAssert(TestProcessInstance actual) {
        super(actual, TestProcessInstance.class);
    }

    public static TestProcessInstanceAssert assertThat(TestProcessInstance actual) {
        return new TestProcessInstanceAssert(actual);
    }
}
