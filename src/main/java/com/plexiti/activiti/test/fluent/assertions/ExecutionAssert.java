package com.plexiti.activiti.test.fluent.assertions;

import com.plexiti.activiti.test.fluent.ActivitiFluentTestHelper;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;

import java.util.List;

/**
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public class ExecutionAssert extends AbstractAssert<ExecutionAssert, Execution> {

    protected ExecutionAssert(Execution actual) {
        super(actual, ExecutionAssert.class);
    }

    public static ExecutionAssert assertThat(Execution actual) {
        return new ExecutionAssert(actual);
    }

    public ExecutionAssert isFinished() {
        isNotNull();

        Assertions.assertThat(actual.isEnded()).
                overridingErrorMessage("Expected execution %s to be finished but it is not!", actual.getId()).
                isTrue();

        return this;
    }

    public ExecutionAssert isStarted() {
        isNotNull();

        Assertions.assertThat(actual.isEnded()).
                overridingErrorMessage("Expected execution %s to be started but it is not!", actual.getId()).
                isFalse();

        return this;
    }

    public ExecutionAssert isAtActivity(String activityId) {
        isNotNull();

        List<String> activeActivityIds = ActivitiFluentTestHelper.getRuntimeService()
                                                                 .getActiveActivityIds(actual.getId());
        Assertions.assertThat(activeActivityIds)
                .overridingErrorMessage("Expected execution with id '%s' to be waiting at activity with id '%s' but it actually waiting at: %s",
                                        actual.getId(), activityId, activeActivityIds)
                .contains(activityId);

        return this;
    }

}
