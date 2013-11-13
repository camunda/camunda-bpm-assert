package org.camunda.bpm.engine.test;

import java.util.Date;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.test.TestHelper;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Rewrite of the ActivitiRule since TestWatchman is deprecated for junit>4.9.
 * 
 * @author Jan Galinski, Holisticon AG (jan.galinski@holisticon.de)
 */
public class ProcessEngineTestWatcher extends TestWatcher implements ProcessEngineTestRule {

  /**
   * Default configuration file name when not used with needle.
   */
  private static final String ACTIVITI_CONFIG_RESOURCE = "activiti.cfg.xml";

  /**
   * Holds the deploymentId after execution of @Deployment. can be used to query
   * the {@link RepositoryService}.
   */
  protected String deploymentId;

  /**
   * The standalone process engine.
   */
  private final ProcessEngine processEngine;

  /**
   * Use default engine.
   * 
   * @see #ProcessEngineTestWatcher(String)
   */
  public ProcessEngineTestWatcher() {
    this(ACTIVITI_CONFIG_RESOURCE);
  }

  /**
   * Create new instance with given configuration resource.
   * 
   * @param configurationResource
   *          name of the configuration file (e.g. activiti.cfg.xml)
   * @see TestHelper#getProcessEngine(String)
   * @see #ProcessEngineTestWatcher(ProcessEngine)
   */
  public ProcessEngineTestWatcher(final String configurationResource) {
    this(TestHelper.getProcessEngine(configurationResource));
  }

  /**
   * Creates a new instance with given {@link ProcessEngine}.
   * 
   * @param processEngine
   *          the ProcessEngine instance to use
   */
  public ProcessEngineTestWatcher(final ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  @Override
  protected void starting(final Description description) {
    deploymentId = TestHelper.annotationDeploymentSetUp(processEngine, description.getTestClass(), description.getMethodName());
  }

  @Override
  protected void finished(final Description description) {
    TestHelper.annotationDeploymentTearDown(processEngine, deploymentId, description.getTestClass(), description.getMethodName());
    ClockUtil.reset();
  }

  @Override
  public void setCurrentTime(final Date currentTime) {
    ClockUtil.setCurrentTime(currentTime);
  }

  @Override
  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  @Override
  public String getDeploymentId() {
    return deploymentId;
  }

  @Override
  public RepositoryService getRepositoryService() {
    return processEngine.getRepositoryService();
  }

  @Override
  public RuntimeService getRuntimeService() {
    return processEngine.getRuntimeService();
  }

  @Override
  public FormService getFormService() {
    return processEngine.getFormService();
  }

  @Override
  public TaskService getTaskService() {
    return processEngine.getTaskService();
  }

  @Override
  public HistoryService getHistoryService() {
    return processEngine.getHistoryService();
  }

  @Override
  public IdentityService getIdentityService() {
    return processEngine.getIdentityService();
  }

  @Override
  public ManagementService getManagementService() {
    return processEngine.getManagementService();
  }

  @Override
  public String getName() {
    return processEngine.getName();
  }

  @Override
  public AuthorizationService getAuthorizationService() {
    return processEngine.getAuthorizationService();
  }

  @Override
  public void close() {
    processEngine.close();
  }

}
