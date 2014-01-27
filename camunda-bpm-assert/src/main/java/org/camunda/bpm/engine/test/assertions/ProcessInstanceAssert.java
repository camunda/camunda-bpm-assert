package org.camunda.bpm.engine.test.assertions;

import java.util.List;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricActivityInstanceQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.TaskQuery;
import org.assertj.core.api.Assertions;

/**
 * Asserts for a {@link ProcessInstance}
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael@cordones.me>
 */
public class ProcessInstanceAssert extends AbstractProcessAssert<ProcessInstanceAssert, ProcessInstance> {

  protected ProcessInstanceAssert(final ProcessEngine engine, final ProcessInstance actual) {
    super(engine, actual, ProcessInstanceAssert.class);
  }

  protected static ProcessInstanceAssert assertThat(final ProcessEngine engine, final ProcessInstance actual) {
    return new ProcessInstanceAssert(engine, actual);
  }

  /**
   * Assert that the {@link ProcessInstance} is currently waiting 
   * at a specified activity.
   * @param activityId the id of the expected activity     
   * @return this {@link ProcessInstanceAssert}
   */
  public ProcessInstanceAssert isWaitingAt(final String activityId) {
    isNotNull();

    final List<String> activeActivityIds = engine.getRuntimeService().getActiveActivityIds(actual.getId());
    Assertions
        .assertThat(activeActivityIds)
        .overridingErrorMessage("Expected processInstance with id '%s' to be waiting at '%s' but it is actually waiting at %s", actual.getId(),
            activityId, activeActivityIds).contains(activityId);

    return this;
  }

  // TODO refactor so that services, queries and automatically 'narrowed' queries (e.g. to the asserted processinstance) are accessible for all asserts
  private HistoricActivityInstanceQuery createHistoricActivityInstanceQuery() {
    return engine.getHistoryService().createHistoricActivityInstanceQuery();
  }

  /**
   * Assert that the {@link ProcessInstance} has passed a specified activity
   * @param expectedActivityId the id of the activity expected to have been passed    
   * @return this {@link ProcessInstanceAssert}
   */
  // TODO make sure this works for running as well as historic instances
  // TODO separate isFinished from hasPassed and make sure the latter works for running as well as historic instances
  // TODO check that the history service is enabled in case a finished process instance is checked against
  // TODO change parameter name expectedActivityId to activitiId - for consistency reasons
  // TODO add resulting assertions to user guide
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
   * Assert that the {@link ProcessInstance} is ended
   * @return this {@link ProcessInstanceAssert}
   */
  // TODO do not accept actual == null instead just check runtimeservice for the actual process instance id
  public ProcessInstanceAssert isEnded() {
    return isFinished();
  }

  // TODO move code to isEnded() and remove
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
   * Assert that the {@link ProcessInstance} is currently 'active', 
   * so neither suspended nor finished.
   * @return this {@link ProcessInstanceAssert}
   */
  // TODO do not directly rely on isSuspended(), instead check runtimeservice for the actual process instance id
  public ProcessInstanceAssert isActive() {
    isStarted();

    Assertions.assertThat(actual.isSuspended()).overridingErrorMessage("Expected processExecution %s to be not suspended but it is!", actual.getId()).isFalse();

    return this;
  }

  /**
   * Assert that the {@link ProcessInstance} is started
   * @return this {@link ProcessInstanceAssert}
   */
  // TODO do not rely on isEnded(), instead check runtimeservice for the actual process instance id
  public ProcessInstanceAssert isStarted() {
    isNotNull();

    Assertions.assertThat(actual.isEnded()).overridingErrorMessage("Expected processExecution %s to be started but it is not!", actual.getId()).isFalse();

    return this;
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