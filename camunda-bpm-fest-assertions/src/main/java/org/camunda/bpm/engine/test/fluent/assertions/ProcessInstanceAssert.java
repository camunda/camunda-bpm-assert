package org.camunda.bpm.engine.test.fluent.assertions;

import java.util.List;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricActivityInstanceQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.TaskQuery;
import org.fest.assertions.api.Assertions;

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

  private HistoricActivityInstanceQuery createHistoricActivityInstanceQuery() {
    return engine.getHistoryService().createHistoricActivityInstanceQuery();
  }

  /**
   * Expects that the given processInstance passed a given activity at least
   * once. Checks historyService.
   * 
   * @param expectedActivityId
   *          the activity to verify
   * @return this
   */
  public ProcessInstanceAssert isFinishedAndPassedActivity(final String expectedActivityId) {
    isFinished();
    final List<HistoricActivityInstance> passed = createHistoricActivityInstanceQuery().activityId(expectedActivityId).finished()
        .processInstanceId(actual.getId()).list();

    final String message = "Expected processInstance with id '%s' to pass activity '%s' at least once, but didn't";
    Assertions.assertThat(passed).overridingErrorMessage(message, actual.getId(), expectedActivityId).isNotEmpty();
    Assertions.assertThat(passed).overridingErrorMessage(message, actual.getId(), expectedActivityId).isNotNull();
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
    Assertions.assertThat(engine.getRuntimeService().createProcessInstanceQuery().processInstanceId(actual.getId()).singleResult()).overridingErrorMessage("Expected processExecution %s to be finished but it is not!", actual.getId()).isNull();

    return this;
  }

  /**
   * The actual instance must neither be ended nor suspended
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

  /**
   * Enter into a chained task assert inspecting the one and mostly 
   * one task currently available in the context of the process instance
   * under test of this ProcessInstanceAssert.
   * @return TaskAssert inspecting the only task available. Inspecting a 
   * 'null' Task in case no such Task is available.
   * @throws RuntimeException in case more than one task is available TODO check which one
   */
  public TaskAssert task() {
    return task(engine.getTaskService().createTaskQuery());
  }

  /**
   * Enter into a chained task assert inspecting only tasks currently 
   * available in the context of the process instance under test of this 
   * ProcessInstanceAssert.
   * @param query TaskQuery further narrowing down the search for tasks
   * @return TaskAssert inspecting the only task resulting from the given 
   * search. Inspecting a 'null' Task in case no such Task is available.
   * @throws RuntimeException in case more than one task is delivered by 
   * the query TODO check which one
   */
  public TaskAssert task(TaskQuery query) {
    if (query == null)
      throw new IllegalArgumentException("Illegal call of task(query = 'null') - but must not be null!");
    isNotNull();
    TaskQuery narrowed = query.processInstanceId(actual.getId());
    return TaskAssert.assertThat(engine, narrowed.singleResult());
  }

}