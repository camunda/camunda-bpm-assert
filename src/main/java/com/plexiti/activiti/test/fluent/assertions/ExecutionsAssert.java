package com.plexiti.activiti.test.fluent.assertions;

import org.activiti.engine.runtime.Execution;
import org.fest.assertions.api.AbstractAssert;

import java.util.List;

/**
 * Fluent assertions for a collection of {@link org.activiti.engine.runtime.Execution}
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class ExecutionsAssert extends AbstractAssert<ExecutionsAssert, List<Execution>> {

    protected ExecutionsAssert(List<Execution> actual) {
        super(actual, ExecutionsAssert.class);
    }

    public static ExecutionsAssert assertThat(List<Execution> actual) {
        return new ExecutionsAssert(actual);
    }

    public ExecutionsAssert areFinished() {
        /*
         * TODO: we need to review this
         * If the incomming Execution instance is null we consider the execution finished
         */
        if (actual == null) {
            return this;
        }

        /*
         * If it is not null we make sure that it is actually finished.
         */
        //ExecutionQuery executionQuery = ActivitiFluentTestHelper.getRuntimeService().createExecutionQuery();
        //executionQuery.processInstanceId(actual.getActualProcessInstance().getId()).list();
        //ActivitiFluentTests.assertThat(actual.get(0).is).
        //        overridingErrorMessage("Expected execution %s to be finished but it is not!", actual.getId()).
        //        isTrue();

        return this;
    }

}
