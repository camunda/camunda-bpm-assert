package org.camunda.bpm.engine.test.assertions;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.util.Lists;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.runtime.*;
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

  /**
   * Assert that the {@link ProcessInstance} has passed a specified activity
   * @param activityIds the id's of the activities expected to have been passed    
   * @return this {@link ProcessInstanceAssert}
   */
  public ProcessInstanceAssert hasPassed(final String... activityIds) {
    Assertions.assertThat(activityIds)
      .overridingErrorMessage("expected list of activityIds not to be null, not to be empty and not to contain null values: %s." 
        , Lists.newArrayList(activityIds))
      .isNotNull().isNotEmpty().doesNotContainNull();
    String activityId = activityIds[0];
    List<HistoricActivityInstance> passed = historicActivityInstanceQuery().activityId(activityId).finished().list();
    final String message = "Expected ProcessInstance { id = '%s' } to have passed activity '%s' at least once, but actually " +
      "we didn't find that expectation to be true. (Please make sure you have set the history service of the engine to a proper " +
      "level before making use of this assertion!)";
    Assertions.assertThat(passed).overridingErrorMessage(message, actual.getId(), activityId).isNotNull();
    Assertions.assertThat(passed).overridingErrorMessage(message, actual.getId(), activityId).isNotEmpty();
    if (activityIds.length > 1)
      hasPassed(Arrays.copyOfRange(activityIds, 1, activityIds.length));
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

  @Override
  protected TaskQuery taskQuery() {
    return super.taskQuery().processInstanceId(actual.getId());
  }

  @Override
  protected JobQuery jobQuery() {
    return super.jobQuery().processInstanceId(actual.getId());
  }

  @Override
  protected ProcessInstanceQuery processInstanceQuery() {
    return super.processInstanceQuery().processInstanceId(actual.getId());
  }

  @Override
  protected ExecutionQuery executionQuery() {
    return super.executionQuery().processInstanceId(actual.getId());
  }

  @Override
  protected VariableInstanceQuery variableInstanceQuery() {
    return super.variableInstanceQuery().processInstanceIdIn(actual.getId());
  }

  @Override
  protected HistoricActivityInstanceQuery historicActivityInstanceQuery() {
    return super.historicActivityInstanceQuery().processInstanceId(actual.getId());
  }

  @Override
  protected HistoricDetailQuery historicDetailQuery() {
    return super.historicDetailQuery().processInstanceId(actual.getId());
  }

  @Override
  protected HistoricProcessInstanceQuery historicProcessInstanceQuery() {
    return super.historicProcessInstanceQuery().processInstanceId(actual.getId());
  }

  @Override
  protected HistoricTaskInstanceQuery historicTaskInstanceQuery() {
    return super.historicTaskInstanceQuery().processInstanceId(actual.getId());
  }

  @Override
  protected HistoricVariableInstanceQuery historicVariableInstanceQuery() {
    return super.historicVariableInstanceQuery().processInstanceId(actual.getId());
  }
  
}