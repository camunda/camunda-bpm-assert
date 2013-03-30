package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.cdi.impl.util.ProgrammaticBeanLookup;
import org.camunda.bpm.engine.test.fluent.support.Classes;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.engine.impl.test.TestHelper;
import org.camunda.bpm.engine.runtime.ExecutionQuery;
import org.camunda.bpm.engine.runtime.JobQuery;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentLookups {

    private static ThreadLocal testObject = new ThreadLocal();

    public static void before(Object testObject) {
        FluentLookups.testObject.set(testObject);
        FluentProcessInstanceLookup.before(testObject);
    }

    public static void after(Object testObject) {
        FluentLookups.testObject.set(null);
        FluentProcessInstanceLookup.after(testObject);
    }

    public static ProcessEngine getProcessEngine() {
      /*
       * If we are in a CDI environment, let's try to get the ProcessEngine via de BeanManager
       */
      try {
        ProcessEngine processEngine = ProgrammaticBeanLookup.lookup(ProcessEngine.class);
        return processEngine;
      } catch (ProcessEngineException e) {
        //FIXME: for the time being we ignore this error and interpret it as using the lookup from a standalone test
      }
      /*
       * Otherwise we just "take it" as being in a standalone environment
       */
      if (testObject.get() instanceof ProcessEngineTestCase)
          return TestHelper.getProcessEngine(((ProcessEngineTestCase) testObject.get()).getConfigurationResource());
      else {
          try {
              return ((ProcessEngineRule) Classes.getFieldByType(testObject.get().getClass(), ProcessEngineRule.class).get(testObject.get())).getProcessEngine();
          } catch (IllegalAccessException e) {
              throw new RuntimeException(e);
          }
      }
    }

    public static RepositoryService getRepositoryService() {
        return getProcessEngine().getRepositoryService();
    }

    public static RuntimeService getRuntimeService() {
        return getProcessEngine().getRuntimeService();
    }

    public static TaskService getTaskService() {
        return getProcessEngine().getTaskService();
    }

    public static HistoryService getHistoryService() {
        return getProcessEngine().getHistoryService();
    }

    public static IdentityService getIdentityService() {
        return getProcessEngine().getIdentityService();
    }

    public static ManagementService getManagementService() {
        return getProcessEngine().getManagementService();
    }

    public static FormService getFormService() {
        return getProcessEngine().getFormService();
    }

    public static ProcessInstanceQuery createProcessInstanceQuery() {
        return getRuntimeService().createProcessInstanceQuery();
    }

    public static ExecutionQuery createExecutionQuery() {
        return getRuntimeService().createExecutionQuery();
    }

    public static TaskQuery createTaskQuery() {
        return getTaskService().createTaskQuery();
    }

    public static UserQuery createUserQuery() {
        return getIdentityService().createUserQuery();
    }

    public static JobQuery createJobQuery() {
        return getManagementService().createJobQuery();
    }

}
