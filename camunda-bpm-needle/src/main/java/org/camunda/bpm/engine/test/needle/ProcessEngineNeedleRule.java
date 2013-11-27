package org.camunda.bpm.engine.test.needle;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static org.camunda.bpm.engine.test.mock.RegisterMockTestRule.registerMockTestRule;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.fluent.FluentProcessEngine;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.impl.fluent.FluentProcessEngineImpl;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.ProcessEngineTestRule;
import org.camunda.bpm.engine.test.ProcessEngineTestWatcher;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTestRule;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests;
import org.camunda.bpm.engine.test.function.GetProcessEngineConfiguration;
import org.camunda.bpm.engine.test.needle.supplier.CamundaInstancesSupplier;
import org.junit.rules.ExternalResource;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

import de.akquinet.jbosscc.needle.injection.InjectionProvider;
import de.akquinet.jbosscc.needle.junit.testrule.NeedleTestRule;
import de.holisticon.toolbox.needle.NeedleTestRuleBuilder;

/**
 * Combines NeedleRule and ActivitiRule via {@link RuleChain}. Camunda Services
 * can be injected in test instances and @Deployment-annotated test methods are
 * interpreted
 * 
 * @author Jan Galinski, Holisticon AG (jan.galinski@holisticon.de)
 */
public class ProcessEngineNeedleRule extends ExternalResource implements ProcessEngineTestRule {

  public static ProcessEngineNeedleRuleBuilder fluentNeedleRule(final Object testInstance) {
    return new ProcessEngineNeedleRuleBuilder(testInstance);
  }

  /**
   * The Logger for this class.
   */
  private final Logger logger = LoggerFactory.getLogger(ProcessEngineNeedleRule.class);

  /**
   * Internal rule, chains Activiti and Needle Rule.
   */
  private final TestRule ruleChain;

  /**
   * Stores the ActivitiRule for service access via getter methods.
   */
  private final ProcessEngineTestWatcher processEngineTestWatcher;

  private final FluentProcessEngine fluentProcessEngine;

  /**
   * Contains the instance started in test.
   */
  private ProcessInstance processInstance;

  ProcessEngineNeedleRule(final Object testInstance, final ProcessEngineConfiguration configuration, final Set<InjectionProvider<?>> injectionProviders) {

    // initialize processEngine
    final CamundaInstancesSupplier camundaInstancesSupplier = CamundaInstancesSupplier.camundaInstancesSupplier(configuration);

    // activate activiti rule
    this.processEngineTestWatcher = new ProcessEngineTestWatcher(camundaInstancesSupplier.getProcessEngine());
    this.fluentProcessEngine = new FluentProcessEngineImpl(camundaInstancesSupplier.getProcessEngine());

    final FluentProcessEngineTestRule fluentProcessEngineTestRule = new FluentProcessEngineTestRule(testInstance, this.fluentProcessEngine);

    // create needle rule with camunda supplier and additional providers
    final NeedleTestRule needleTestRule = NeedleTestRuleBuilder.needleTestRule(testInstance).addSupplier(camundaInstancesSupplier) //
        .addProvider(injectionProviders.toArray(new InjectionProvider<?>[injectionProviders.size()])) //
        .build();

    final RuleChain needleWithRegisterMocks = RuleChain.outerRule(needleTestRule).around(registerMockTestRule(testInstance));

    // combine activiti and needle rules
    this.ruleChain = RuleChain.outerRule(fluentProcessEngineTestRule).around(processEngineTestWatcher).around(needleWithRegisterMocks);
  }

  @Override
  public void setCurrentTime(final Date currentTime) {
    processEngineTestWatcher.setCurrentTime(currentTime);
  }

  @Override
  public Statement apply(final Statement base, final Description description) {
    return ruleChain.apply(base, description);
  }

  @Override
  public void before() {
    FluentProcessEngineTests.before(getEngine());
    // does not use FluentMocks, use needle
  }

  @Override
  public void after() {
    // no need to rest Mocks, this is done in RegisterMockTestRule
    FluentProcessEngineTests.after();
  }

  protected FluentProcessEngine getEngine() {
    return fluentProcessEngine;
  }

  @Override
  public String getDeploymentId() {
    return processEngineTestWatcher.getDeploymentId();
  }

  @Override
  public ProcessEngine getProcessEngine() {
    return processEngineTestWatcher.getProcessEngine();
  }

  @Override
  public RepositoryService getRepositoryService() {
    return processEngineTestWatcher.getRepositoryService();
  }

  @Override
  public RuntimeService getRuntimeService() {
    return processEngineTestWatcher.getRuntimeService();
  }

  @Override
  public FormService getFormService() {
    return processEngineTestWatcher.getFormService();
  }

  @Override
  public TaskService getTaskService() {
    return processEngineTestWatcher.getTaskService();
  }

  @Override
  public HistoryService getHistoryService() {
    return processEngineTestWatcher.getHistoryService();
  }

  @Override
  public IdentityService getIdentityService() {
    return processEngineTestWatcher.getIdentityService();
  }

  @Override
  public ManagementService getManagementService() {
    return processEngineTestWatcher.getManagementService();
  }

  public ProcessEngineConfiguration getProcessEngineConfiguration() {
    return GetProcessEngineConfiguration.INSTANCE.apply(getProcessEngine());
  }

  public DataSource getProcessEngineDataSource() {
    return getProcessEngineConfiguration().getDataSource();
  }

  /**
   * Performs a search for active ProcessInstances and fails if any are found.
   */
  public void assertNoMoreRunningInstances() {
    assertThat(getRuntimeService().createProcessInstanceQuery().active().count(), is(0L));
  }

  /**
   * Delegates to {@link RuntimeService#startProcessInstanceByKey(String, Map)}.
   * 
   * @param processDefinitionKey
   *          process name as configured in BPMN file
   * @param variables
   *          map for process start
   * @return started process instance
   */
  public ProcessInstance startProcessInstanceByKey(final String processDefinitionKey, final Map<String, Object> variables) {
    checkArgument(processDefinitionKey != null, "processDefinitionKey must not be null!");

    if (variables == null) {
      setProcessInstance(getRuntimeService().startProcessInstanceByKey(processDefinitionKey));
    } else {
      setProcessInstance(getRuntimeService().startProcessInstanceByKey(processDefinitionKey, variables));
    }

    return getProcessInstance();
  }

  /**
   * Delegates to {@link RuntimeService#startProcessInstanceByKey(String)}.
   * 
   * @param processDefinitionKey
   *          process name as configured in BPMN file
   * @return started process instance
   */
  public ProcessInstance startProcessInstanceByKey(final String processDefinitionKey) {
    return startProcessInstanceByKey(processDefinitionKey, null);
  }

  /**
   * Deletes all history entries.
   */
  public void deleteHistoricInstances() {
    try {
      if (processInstance != null) {
        getHistoryService().deleteHistoricProcessInstance(processInstance.getId());
      }
    } catch (final Exception e) {
      logger.warn("could not delete Historic Process Instance: " + e.getMessage());
    }
  }

  public ProcessInstance getProcessInstance() {
    return processInstance;
  }

  public void setProcessInstance(final ProcessInstance processInstance) {
    this.processInstance = processInstance;
  }

  /**
   * Logs executionId and startTime of historic instance to debug.
   * 
   * @param history
   *          List of historic activity instances
   */
  private static void debugHistory(final List<HistoricActivityInstance> history) {
    final SimpleDateFormat format = new SimpleDateFormat("ss.SSS");
    for (final HistoricActivityInstance activityInstance : history) {
      LoggerFactory.getLogger(ProcessEngineNeedleRule.class).debug(
          format("[%03d] (%s) %s", Integer.parseInt(activityInstance.getExecutionId()), format.format(activityInstance.getStartTime().getTime()),
              activityInstance.getActivityId()));
    }
  }

  /**
   * Checks if all activities belonging to {@link #processInstance} were
   * executed in the expected order.
   * 
   * @see #assertActivitiesInOrder(List)
   * @param activityIds
   *          vararg array of activity ids in expected order
   */
  public void assertActivitiesInOrder(final String... activityIds) {
    checkState(processInstance != null);
    assertActivitiesInOrder(newArrayList(activityIds));
  }

  /**
   * Checks if all activities belonging to {@link #processInstance} were
   * executed in the expected order.
   * 
   * @param activityIds
   *          List of activity ids in expected order
   */
  public void assertActivitiesInOrder(final List<String> activityIds) {
    checkState(processInstance != null);

    // suche alle Einträge für die aktuelle Prozessinstanz
    final List<HistoricActivityInstance> history = newArrayList(getHistoryService().createHistoricActivityInstanceQuery()
        .processInstanceId(processInstance.getId()).orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceId().asc()
        .orderByExecutionId().asc().finished().list().iterator());

    debugHistory(history);

    final ListIterator<HistoricActivityInstance> iterator = history.listIterator();
    // prüfe ob iterator und übergebene Liste übereinstimmen
    for (final String activitiId : activityIds) {
      if (!iterator.hasNext()) {
        fail("no historic activities found. expected: '" + activitiId + "'");
      }

      assertThat(iterator.next().getActivityId(), is(activitiId));
    }
  }

  /**
   * Gets all Candidate groups from given Task.
   * 
   * @param task
   *          the task instance to be examinated.
   * @return Set of candidate groups.
   */
  public Set<String> getCandidateGroups(final Task task) {
    checkArgument(task != null, "task must not be null!");
    final List<IdentityLink> identityLinksForTask = getTaskService().getIdentityLinksForTask(task.getId());
    final Set<String> groups = Sets.newHashSet();
    for (final IdentityLink identityLink : identityLinksForTask) {
      final String group = identityLink.getGroupId();
      if (group != null) {
        groups.add(group);
      }
    }
    return groups;
  }

  /**
   * Get executionId waiting in activity with given id. Attention: this does not
   * work when multiple executions exist (in loops, parallel processes, ...).
   * 
   * @param activityId
   *          - name of the activity
   * @return execution id waiting in activity
   */
  public String getExecutionId(final String activityId) {
    return getRuntimeService().createExecutionQuery().processInstanceId(processInstance.getId()).activityId(activityId).singleResult().getId();
  }

  /**
   * Checks given user task for various expected values.
   * 
   * @param task
   *          the current user task
   * @param name
   *          expected task name
   * @param description
   *          expected task description
   * @param priority
   *          expected task priortiy
   * @param expectedCandidateGroups
   *          vararg array of expected candidate groups (must all match)
   */
  public void assertUserTask(final Task task, final String name, final String description, final int priority, final String... expectedCandidateGroups) {
    assertThat(task.getName(), equalTo(name));
    assertThat(task.getDescription(), is(description));
    assertThat(task.getPriority(), is(priority));
    final Set<String> candidateGroups = getCandidateGroups(task);
    assertThat(candidateGroups, hasSize(expectedCandidateGroups.length));
    // assertThat(candidateGroups, hasItems(expectedCandidateGroups));
  }

  /**
   * Get single current task. Will fail when none or more than one task is
   * active.
   * 
   * @return current task
   */
  public Task getTask() {
    final Task singleResult = getTaskService().createTaskQuery().singleResult();
    checkState(singleResult != null, "getTask() expects exactly 1 active task, found none!");

    return singleResult;
  }

  /**
   * Completes the current task.
   * 
   * @see #getTask()
   */
  public void completeTask() {
    getTaskService().complete(getTask().getId());
  }

  /**
   * Completes the current task with variables.
   * 
   * @param variables
   *          stored in process payload
   */
  public void completeTask(final Map<String, Object> variables) {
    getTaskService().complete(getTask().getId(), variables);
  }

  /**
   * Get Variables for given execution.
   * 
   * @param execution
   *          the current execution
   * @return Immutable Variable Map for this execution
   */
  public Map<String, Object> getVariables(final Execution execution) {
    checkArgument(execution != null, "execution must not be null!");
    return ImmutableMap.copyOf(getRuntimeService().getVariables(execution.getId()));
  }

  /**
   * Executes async job. Does not work if more then one job is active!
   */
  public void executeAsyncJob() {
    final Job job = getManagementService().createJobQuery().singleResult();
    assertNotNull("no active job found", job);
    executeAsyncJob(job.getId());
  }

  /**
   * Execute given job.
   * 
   * @param jobId
   *          the job id (must not be blank)
   */
  public void executeAsyncJob(final String jobId) {
    assertFalse(isNullOrEmpty(jobId));
    getManagementService().setJobRetries(jobId, 0);
    getManagementService().executeJob(jobId);
  }

  @Override
  public String getName() {
    return getProcessEngine().getName();
  }

  @Override
  public AuthorizationService getAuthorizationService() {
    return processEngineTestWatcher.getAuthorizationService();
  }

  @Override
  public void close() {
    processEngineTestWatcher.close();
  }
}
