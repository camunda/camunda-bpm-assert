package com.plexiti.activiti.test.fluent.assertions;

import com.plexiti.activiti.test.fluent.engine.FluentBpmnLookups;
import com.plexiti.activiti.test.fluent.engine.FluentBpmnProcessInstance;
import com.plexiti.activiti.test.fluent.engine.FluentBpmnProcessInstanceImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;

import java.util.List;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class TestProcessInstanceAssert extends AbstractAssert<TestProcessInstanceAssert, FluentBpmnProcessInstance> {

    protected TestProcessInstanceAssert(FluentBpmnProcessInstance actual) {
        super(actual, FluentBpmnProcessInstanceImpl.class);
    }

    public static TestProcessInstanceAssert assertThat(FluentBpmnProcessInstance actual) {
        return new TestProcessInstanceAssert(actual);
    }

}