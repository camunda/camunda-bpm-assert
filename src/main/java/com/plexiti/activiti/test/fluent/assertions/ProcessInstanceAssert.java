package com.plexiti.activiti.test.fluent.assertions;

import com.plexiti.activiti.test.fluent.engine.FluentLookups;
import com.plexiti.activiti.test.fluent.engine.FluentProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;

import java.util.List;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class ProcessInstanceAssert extends AbstractAssert<ProcessInstanceAssert, ProcessInstance> {

    protected ProcessInstanceAssert(FluentProcessInstance actual) {
        super(actual, ProcessInstanceAssert.class);
    }

    public static ProcessInstanceAssert assertThat(FluentProcessInstance actual) {
        return new ProcessInstanceAssert(actual);
    }

    public ProcessInstanceAssert isWaitingAt(String activityId) {
        isNotNull();

        List<String> activeActivityIds = FluentLookups.getRuntimeService()
                .getActiveActivityIds(actual.getId());
        Assertions.assertThat(activeActivityIds)
                .overridingErrorMessage("Expected processInstance with id '%s' to be waiting at activity with id '%s' but it actually waiting at: %s",
                        actual.getId(), activityId, activeActivityIds)
                .contains(activityId);

        checkForMoveToActivityIdException(activityId);

        return this;
    }

    public ProcessInstanceAssert isFinished() {
        /*
         * TODO: we need to review this
         * If the incomming Execution instance is null we consider the processExecution finished
         */
        if (actual == null) {
            return this;
        }

        /*
         * If it is not null we make sure that it is actually finished.
         */
        Assertions.assertThat(actual.isEnded()).
                overridingErrorMessage("Expected processExecution %s to be finished but it is not!", actual.getId()).
                isTrue();

        return this;
    }

    public ProcessInstanceAssert isStarted() {
        isNotNull();

        Assertions.assertThat(actual.isEnded()).
                overridingErrorMessage("Expected processExecution %s to be started but it is not!", actual.getId()).
                isFalse();

        return this;
    }

    private static ThreadLocal<String> moveToActivityId = new ThreadLocal<String>();

    public static void setMoveToActivityId(String id) {
        moveToActivityId.set(id);
    }

    private static void checkForMoveToActivityIdException(String activityId) {
        if (activityId.equals(moveToActivityId.get())) {
            setMoveToActivityId(null);
            throw new MoveToActivityIdException();
        }
    }

    public static class MoveToActivityIdException extends RuntimeException {
        private static final long serialVersionUID = 2282185191899085294L;
    }

}