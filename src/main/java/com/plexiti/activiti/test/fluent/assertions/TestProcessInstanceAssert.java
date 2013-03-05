package com.plexiti.activiti.test.fluent.assertions;

import com.plexiti.activiti.test.fluent.engine.TestLookups;
import com.plexiti.activiti.test.fluent.engine.TestProcessInstance;
import com.plexiti.activiti.test.fluent.engine.TestProcessInstanceImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;

import java.util.List;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class TestProcessInstanceAssert extends AbstractAssert<TestProcessInstanceAssert, TestProcessInstance> {

    protected TestProcessInstanceAssert(TestProcessInstance actual) {
        super(actual, TestProcessInstanceImpl.class);
    }

    public static TestProcessInstanceAssert assertThat(TestProcessInstance actual) {
        return new TestProcessInstanceAssert(actual);
    }

    public Execution execution() {
        List<Execution> executions = executions();
        if (executions.size() == 0) return null;
        Assertions.assertThat(executions)
                .as("By calling execution() you implicitly assumed that at most one execution exists but there are " + executions.size())
                .hasSize(1);
        return executions.get(0);
    }

    public List<Execution> executions() {
        ExecutionQuery executionQuery = TestLookups.getRuntimeService().createExecutionQuery();
        return executionQuery.processInstanceId(actual.getActualProcessInstance().getId()).list();
    }}
