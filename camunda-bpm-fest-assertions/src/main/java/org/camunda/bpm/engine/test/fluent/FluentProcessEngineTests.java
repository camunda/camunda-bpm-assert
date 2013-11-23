package org.camunda.bpm.engine.test.fluent;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.fluent.FluentJob;
import org.camunda.bpm.engine.fluent.FluentProcessDefinition;
import org.camunda.bpm.engine.fluent.FluentProcessEngine;
import org.camunda.bpm.engine.fluent.FluentProcessInstance;
import org.camunda.bpm.engine.fluent.FluentProcessVariable;
import org.camunda.bpm.engine.fluent.FluentTask;
import org.camunda.bpm.engine.impl.fluent.FluentDeploymentImpl;
import org.camunda.bpm.engine.impl.fluent.FluentProcessEngineImpl;
import org.camunda.bpm.engine.impl.fluent.FluentProcessInstanceImpl;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ExecutionQuery;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.JobQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.bpm.engine.test.fluent.assertions.JobAssert;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessDefinitionAssert;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessInstanceAssert;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessVariableAssert;
import org.camunda.bpm.engine.test.fluent.assertions.TaskAssert;
import org.camunda.bpm.engine.test.mock.Mocks;

/**
 * Convenience class to access all fluent Activiti assertions. In your code use
 * import static FluentProcessEngineTests.*;
 * 
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentProcessEngineTests extends org.fest.assertions.api.Assertions {

  private static ThreadLocal<FluentProcessEngine> fluentProcessEngineLocal = new ThreadLocal<FluentProcessEngine>();
  private static ThreadLocal<Map<String, FluentProcessInstance>> testProcessInstancesLocal = new ThreadLocal<Map<String, FluentProcessInstance>>();
  private static ThreadLocal<Set<String>> deploymentIdsLocal = new ThreadLocal<Set<String>>();

  public static void before(final FluentProcessEngine fluentProcessEngine) {
    fluentProcessEngineLocal.set(fluentProcessEngine);
    testProcessInstancesLocal.set(new HashMap<String, FluentProcessInstance>());
    deploymentIdsLocal.set(new HashSet<String>());
  }

  public static void before(final ProcessEngine processEngine) {
    before(new FluentProcessEngineImpl(processEngine));
  }

  /**
   * @see #before(FluentProcessEngine)
   * @param fluentProcessEngineTestRule
   */
  @Deprecated
  public static void before(final FluentProcessEngineTestRule fluentProcessEngineTestRule) {
    before(fluentProcessEngineTestRule.getEngine());
  }

  /**
   * @deprated see {@link #after()}
   * @param unused
   */
  @Deprecated
  public static void after(final FluentProcessEngineTestRule unused) {
    after();
  }

  /**
   * Reset all threadlocal variables to <code>null</code>, undeploy processes
   * and reset clock and mocks.
   */
  public static void after() {
    undeploy();

    fluentProcessEngineLocal.set(null);
    testProcessInstancesLocal.set(null);

    deploymentIdsLocal.set(null);

    resetMocks();
    resetClock();
  }

  /**
   * Undeploy all processes before closing the engine.
   */
  public static void undeploy() {
    for (final String deploymentId : deploymentIdsLocal.get()) {
      processEngine().getRepositoryService().deleteDeployment(deploymentId, true);
    }
  }

  /**
   * Sets time.
   * 
   * @param currentTime
   *          sets current time in the engine
   */
  public static void setCurrentTime(final Date currentTime) {
    checkArgument(currentTime != null, "currentTime must not be null!");
    ClockUtil.setCurrentTime(currentTime);
  }

  /**
   * Resets process engine clock.
   */
  public static void resetClock() {
    ClockUtil.reset();
  }

  public static void resetMocks() {
    Mocks.reset();
  }

  protected static Map<String, FluentProcessInstance> getTestProcessInstances() {
    return testProcessInstancesLocal.get();
  }

  protected static FluentProcessEngine processEngine() {
    return fluentProcessEngineLocal.get();
  }

  public static interface Move extends FluentProcessInstance.Move {
  }

  public static FluentProcessInstance newProcessInstance(final String processDefinitionKey, final Move move, final String targetActivityId) {
    final FluentProcessInstanceImpl fluentBpmnProcessInstance = (FluentProcessInstanceImpl) newProcessInstance(processDefinitionKey);
    ProcessInstanceAssert.setMoveToActivityId(targetActivityId);
    fluentBpmnProcessInstance.moveAlong(move);
    return fluentBpmnProcessInstance;
  }

  /**
   * Creates a new {@link FluentProcessInstance} which provides fluent methods
   * to declare process variables before actually starting a process instance.
   * 
   * @param processDefinitionKey
   *          the value of the "id" attribute in the process definition BPMN 2.0
   *          XML file
   * @return a {@link FluentProcessInstance} that can be further configured
   *         before starting the process instance
   * @throws IllegalArgumentException
   *           in case there is no such process definition deployed with the
   *           given key
   * @see org.camunda.bpm.engine.fluent.FluentProcessInstance#setVariable(String,
   *      Object)
   * @see org.camunda.bpm.engine.fluent.FluentProcessInstance#start()
   */
  public static FluentProcessInstance newProcessInstance(final String processDefinitionKey) {
    checkArgument(isNotBlank(processDefinitionKey), "processDefinitionKey must not be blank!");
    final Map<String, FluentProcessInstance> instances = getTestProcessInstances();

    // return cached instance if exists, otherwise create and cache a new
    // instance
    return firstNonNull(instances.get(processDefinitionKey), cacheProcessInstance(processDefinitionKey, createProcessInstance(processDefinitionKey)));
  }

  /**
   * Creates a new process instance.
   * 
   * @param processDefinitionKey
   *          the process definition id for the new instance
   * @return new ProcessInstance instance
   */
  private static FluentProcessInstance createProcessInstance(final String processDefinitionKey) {
    return processEngine().getProcessInstanceRepository().newProcessInstance(processDefinitionKey);
  }

  /**
   * Cache the given process instance under the given process definition key.
   * 
   * @param processDefinitionKey
   *          the process' definition key
   * @param fluentProcessInstance
   *          the instance to cache
   */
  private static FluentProcessInstance cacheProcessInstance(final String processDefinitionKey, final FluentProcessInstance fluentProcessInstance) {
    final Map<String, FluentProcessInstance> instances = getTestProcessInstances();

    // make sure no instance for this process is registered
    checkState(!instances.containsKey(processDefinitionKey), format("An instance for processDefinitionKey='%s' is already registered!", processDefinitionKey));

    instances.put(processDefinitionKey, fluentProcessInstance);
    return instances.get(processDefinitionKey);
  }

  /**
   * Creates a new {@link FluentProcessInstance} from existing, already running
   * process instance. Use this to enrich an instance that was started
   * individually with the fluent api and assertions methods. The fluent
   * instance is cached via {@link #getTestProcessInstances()}.
   * 
   * @param newProcessInstance
   *          the existing process instance, must not be <code>null</code>
   * @return a fluent process instance wrapping the given instance.
   */
  public static FluentProcessInstance newProcessInstance(final ProcessInstance newProcessInstance) {
    checkArgument(newProcessInstance != null, "newProcessInstance must not be null!");
    return newProcessInstance(new FluentProcessInstanceImpl(processEngine(), newProcessInstance));
  }

  public static FluentProcessInstance newProcessInstance(final FluentProcessInstance newProcessInstance) {
    checkArgument(newProcessInstance != null, "newProcessInstance must not be null!");

    return cacheProcessInstance(newProcessInstance.processDefinitionKey(), new FluentProcessInstanceImpl(processEngine(), newProcessInstance));
  }

  /**
   * Returns the one and only {@link FluentProcessInstance} started from within
   * and bound to the current thread running the test scenario - in case just
   * one such instance has been started.
   * 
   * @return the one and only {@link FluentProcessInstance} bound to the current
   *         thread
   * @throws IllegalStateException
   *           in case no process instance has been started yet in the context
   *           of the current thread
   * @throws IllegalStateException
   *           in case more than one process instance has been started in the
   *           context of the current thread running the test scenario.
   */
  public static FluentProcessInstance processInstance() {
    final Map<String, FluentProcessInstance> instances = getTestProcessInstances();
    if (instances.isEmpty())
      throw new IllegalStateException("No process instance has been started yet in the context of the current thread. Call newProcessinstance(String) first.");
    if (instances.size() > 1)
      throw new IllegalStateException("More than one process instance has been started in the context of the current thread.");
    return instances.values().iterator().next();
  }

  /**
   * Returns the first {@link FluentProcessInstance} started with the given
   * processDefinitionKey - hence based on this processDefinition - and started
   * from within and bound to the current thread running the test scenario. In
   * the seldom case more than one {@link FluentProcessInstance} per
   * processDefinitionKey need to be started from within the same test scenario,
   * it is the recommended approach to hold those {@link FluentProcessInstance}
   * objects within local test variables.
   * 
   * @param processDefinitionKey
   *          the value of the "id" attribute in the process definition BPMN 2.0
   *          XML file
   * @return the first {@link FluentProcessInstance} bound to the current thread
   * @throws IllegalStateException
   *           in case no such process instance (started with the given
   *           processDefinitionKey) has been started yet in the context of the
   *           current thread running the test scenario.
   */
  public static FluentProcessInstance processInstance(final String processDefinitionKey) {
    if (!getTestProcessInstances().containsKey(processDefinitionKey))
      throw new IllegalStateException("No such process instance (started with the given processDefinitionKey '" + processDefinitionKey
          + "') has been started yet in the context of the current thread. Call newProcessinstance(String) first.");
    return getTestProcessInstances().values().iterator().next();
  }

  /**
   * Returns the latest deployed version of the {@link FluentProcessDefinition}
   * with the given processDefinitionKey
   * 
   * @param processDefinitionKey
   *          the value of the "id" attribute in the process definition BPMN 2.0
   *          XML file
   * @return the latest deployed version of the {@link FluentProcessDefinition}
   * @throws IllegalArgumentException
   *           in case no such process definition was deployed yet.
   */
  public static FluentProcessDefinition processDefinition(final String processDefinitionKey) {
    return processEngine().getProcessDefinitionRepository().processDefinition(processDefinitionKey);
  }

  /**
   * Deploys given BPMN files in classpath.
   * 
   * @param classPathResources
   *          vararg list of bpmn file-paths.
   * @return the deployment id
   */
  public static String deploy(final String... classPathResources) {
    checkArgument(classPathResources != null && classPathResources.length > 0, "classPathResources must not be null or empty!");

    final String deploymentId = new FluentDeploymentImpl(processEngine()).addClasspathResource(classPathResources).deploy();
    deploymentIdsLocal.get().add(deploymentId);
    return deploymentId;
  }

  /**
   * @return all deployment Ids in current thread
   */
  public static Set<String> getDeploymentIds() {
    return deploymentIdsLocal.get();
  }

  /**
   * @see processInstance().task()
   * @return current task
   */
  public static FluentTask task() {
    return processInstance().task();
  }

  /**
   * Like {@link #task()}, but asserts that the task returned has the given
   * definition key.
   * 
   * @param definitionKey
   *          the expected task definition key
   * @return current task
   */
  public static FluentTask task(final String definitionKey) {
    final FluentTask task = processInstance().task();
    assertThat(task).hasDefinitionKey(definitionKey);
    return task;
  }

  /**
   * @see processInstance().job()
   * @return current job
   */
  public static FluentJob job() {
    return processInstance().job();
  }

  /**
   * Like {@link #job()}, but asserts that the job returned has the given jobId.
   * 
   * @param jobId
   *          the expected job id
   * @return the current job
   */
  public static FluentJob job(final String jobId) {
    final FluentJob job = processInstance().job();
    assertThat(job).hasId(jobId);
    return job;
  }

  public static TaskQuery taskQuery() {
    return processEngine().getTaskService().createTaskQuery();
  }

  public static JobQuery jobQuery() {
    return processEngine().getManagementService().createJobQuery();
  }

  public static ProcessInstanceQuery processInstanceQuery() {
    return processEngine().getRuntimeService().createProcessInstanceQuery();
  }

  public static ExecutionQuery executionQuery() {
    return processEngine().getRuntimeService().createExecutionQuery();
  }

  // Assertions

  public static TaskAssert assertThat(final Task actual) {
    return TaskAssert.assertThat(processEngine(), actual);
  }

  public static JobAssert assertThat(final Job actual) {
    return JobAssert.assertThat(processEngine(), actual);
  }

  public static ProcessDefinitionAssert assertThat(final ProcessDefinition actual) {
    return ProcessDefinitionAssert.assertThat(processEngine(), actual);
  }

  public static ProcessInstanceAssert assertThat(final ProcessInstance actual) {
    return ProcessInstanceAssert.assertThat(processEngine(), actual);
  }

  public static ProcessVariableAssert assertThat(final FluentProcessVariable actual) {
    return ProcessVariableAssert.assertThat(processEngine(), actual);
  }

}
