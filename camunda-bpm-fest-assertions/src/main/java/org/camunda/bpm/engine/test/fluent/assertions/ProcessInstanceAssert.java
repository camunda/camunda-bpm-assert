package org.camunda.bpm.engine.test.fluent.assertions;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.fluent.FluentProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.fest.assertions.api.Assertions;

import java.util.List;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class ProcessInstanceAssert extends AbstractProcessAssert<ProcessInstanceAssert, ProcessInstance> {

  protected ProcessInstanceAssert(final ProcessEngine engine, final ProcessInstance actual) {
    super(engine, actual, ProcessInstanceAssert.class);
  }

  public static ProcessInstanceAssert assertThat(final ProcessEngine engine, final ProcessInstance actual) {
    return new ProcessInstanceAssert(engine, actual);
  }

  public ProcessInstanceAssert isWaitingAt(final String activityId) {
    isNotNull();

    final List<String> activeActivityIds = engine.getRuntimeService().getActiveActivityIds(actual.getId());
    Assertions
        .assertThat(activeActivityIds)
        .overridingErrorMessage("Expected processInstance with id '%s' to be waiting at activity with id '%s' but it actually waiting at: %s", actual.getId(),
            activityId, activeActivityIds).contains(activityId);

    checkForMoveToActivityIdException(activityId);

    return this;
  }

  /**
   * Delegate to {@link #isFinished()}. This method is useful to match the
   * original api method {@link ProcessInstance#isEnded()}.
   * 
   * @return this
   */
  public ProcessInstanceAssert isEnded() {
    return isFinished();
  }

  public ProcessInstanceAssert isFinished() {
    /*
     * TODO: we need to review this If the incomming Execution instance is null
     * we consider the processExecution finished
     */
    if (actual == null) {
      return this;
    }

    /*
     * If it is not null we make sure that it is actually finished.
     */
    Assertions.assertThat(actual.isEnded()).overridingErrorMessage("Expected processExecution %s to be finished but it is not!", actual.getId()).isTrue();

    return this;
  }

  /**
   * The actual instance must neither be ended nor suspended, see
   * {@link FluentProcessInstance#isActive()}.
   * 
   * @return this
   */
  public ProcessInstanceAssert isActive() {
    isStarted();

    Assertions.assertThat(actual.isSuspended()).overridingErrorMessage("Expected processExecution %s to be not suspended but it is!", actual.getId()).isFalse();

    return this;
  }

  public ProcessInstanceAssert isStarted() {
    isNotNull();

    Assertions.assertThat(actual.isEnded()).overridingErrorMessage("Expected processExecution %s to be started but it is not!", actual.getId()).isFalse();

    return this;
  }

  private static ThreadLocal<String> moveToActivityId = new ThreadLocal<String>();

  public static void setMoveToActivityId(final String id) {
    moveToActivityId.set(id);
  }

  private static void checkForMoveToActivityIdException(final String activityId) {
    if (activityId.equals(moveToActivityId.get())) {
      setMoveToActivityId(null);
      throw new MoveToActivityIdException();
    }
  }

  public static class MoveToActivityIdException extends RuntimeException {
    private static final long serialVersionUID = 2282185191899085294L;
  }

}