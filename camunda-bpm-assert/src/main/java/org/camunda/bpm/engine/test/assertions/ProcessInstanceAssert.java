package org.camunda.bpm.engine.test.assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.ListAssert;
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
   * at one or more specified activities.
   * @param activityIds the id's of the activities the process instance is expected to 
   *                    be waiting at
   * @return this {@link ProcessInstanceAssert}
   */
  public ProcessInstanceAssert isWaitingAt(final String... activityIds) {
    return isWaitingAt(activityIds, false);
  }

  /**
   * Assert that the {@link ProcessInstance} is currently waiting 
   * at exactly one or more specified activities.
   * @param activityIds the id's of the activities the process instance is expected to 
   *                    be waiting at
   * @return this {@link ProcessInstanceAssert}
   */
  public ProcessInstanceAssert isWaitingAtExactly(final String... activityIds) {
    return isWaitingAt(activityIds, true);
  }

  private ProcessInstanceAssert isWaitingAt(final String[] activityIds, boolean exactly) {
    isNotNull();
    Assertions.assertThat(activityIds)
      .overridingErrorMessage("expected list of activityIds not to be null, not to be empty and not to contain null values: %s."
        , Lists.newArrayList(activityIds))
      .isNotNull().isNotEmpty().doesNotContainNull();
    final List<String> activeActivityIds = runtimeService().getActiveActivityIds(actual.getId());
    ListAssert<String> assertion = Assertions.assertThat(activeActivityIds)
      .overridingErrorMessage("Expected processInstance with id '%s' to be waiting at '%s' but it is actually waiting at %s", actual.getId(),
        Lists.newArrayList(activityIds), activeActivityIds);
    if (exactly) {
      String[] sorted = activityIds.clone();
      Arrays.sort(sorted);
      assertion.containsExactly(sorted);
    } else {
      assertion.contains(activityIds);
    }
    return this;
  }

  /**
   * Assert that the {@link ProcessInstance} has passed exactly one or more 
   * specified activities.
   * @param activityIds the id's of the activities expected to have been passed    
   * @return this {@link ProcessInstanceAssert}
   */
  public ProcessInstanceAssert hasPassedExactly(final String... activityIds) {
     return hasPassed(activityIds, true);
  }

  /**
   * Assert that the {@link ProcessInstance} has passed one or more specified activities
   * @param activityIds the id's of the activities expected to have been passed    
   * @return this {@link ProcessInstanceAssert}
   */
  public ProcessInstanceAssert hasPassed(final String... activityIds) {
    return hasPassed(activityIds, false);
  }

  private ProcessInstanceAssert hasPassed(final String[] activityIds, boolean exactly) {
    isNotNull();
    Assertions.assertThat(activityIds)
      .overridingErrorMessage("expected list of activityIds not to be null, not to be empty and not to contain null values: %s." 
        , Lists.newArrayList(activityIds))
      .isNotNull().isNotEmpty().doesNotContainNull();
    List<HistoricActivityInstance> finishedInstances = historicActivityInstanceQuery().finished().orderByActivityId().asc().list();
    List<String> finished = new ArrayList<String>(finishedInstances.size());
    for (HistoricActivityInstance instance: finishedInstances) {
      finished.add(instance.getActivityId());
    }
    final String message = "Expected ProcessInstance { id = '%s' } to have passed activities %s at least once, but actually " +
      "we instead we found that it passed %s. (Please make sure you have set the history service of the engine to at least " +
      "'activity' or a higher level before making use of this assertion!)";
    ListAssert<String> assertion = Assertions.assertThat(finished)
      .overridingErrorMessage(message, actual.getId(), Lists.newArrayList(activityIds), Lists.newArrayList(finished));
    if (exactly) {
      String[] sorted = activityIds.clone();
      Arrays.sort(sorted);
      assertion.containsExactly(sorted);
    } else {
      assertion.contains(activityIds);
    }
    return this;
  }

  /**
   * Assert that the {@link ProcessInstance} is ended
   * @return this {@link ProcessInstanceAssert}
   */
  public ProcessInstanceAssert isEnded() {
    isNotNull();
    Assertions.assertThat(processInstanceQuery().singleResult())
      .overridingErrorMessage("Expected ProcessInstance { id = '%s' } to be ended, but it is not!", actual.getId())
      .isNull();
    return this;
  }

  /**
   * Assert that the {@link ProcessInstance} is currently suspended
   * @return this {@link ProcessInstanceAssert}
   */
  public ProcessInstanceAssert isSuspended() {
    isNotNull();
    Assertions.assertThat(processInstanceQuery().singleResult()).isNotNull();
    Assertions.assertThat(processInstanceQuery().singleResult().isSuspended())
      .overridingErrorMessage("Expected ProcessInstance { id = '%s' } to be suspended, but it is not!", actual.getId())
      .isTrue();
    return this;
  }

  /**
   * Assert that the {@link ProcessInstance} is not ended
   * @return this {@link ProcessInstanceAssert}
   */
  public ProcessInstanceAssert isNotEnded() {
    isNotNull();
    Assertions.assertThat(processInstanceQuery().singleResult())
      .overridingErrorMessage("Expected ProcessInstance { id = '%s' } not to be ended, but it is!", actual.getId())
      .isNotNull();
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
   * Assert that the {@link ProcessInstance} is started. This is also true, in case the 
   * process instance already ended.
   * @return this {@link ProcessInstanceAssert}
   */
  public ProcessInstanceAssert isStarted() {
    isNotNull();
    Object pi = processInstanceQuery().singleResult();
    if (pi == null) 
      pi = historicProcessInstanceQuery().singleResult();
    Assertions.assertThat(pi)
      .overridingErrorMessage("Expected ProcessInstance { id = '%s' } to be started, but it is not!", actual.getId())
      .isNotNull();
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