package com.plexiti.activiti.test.fluent.engine;

import com.plexiti.activiti.test.fluent.support.Classes;
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
public class FluentBpmnLookups {

    private static ThreadLocal testObject = new ThreadLocal();

    public static void init(Object testObject) {
        FluentBpmnLookups.testObject.set(testObject);
        FluentBpmnProcessInstanceLookup.init(testObject);
    }

    public static ProcessEngine getProcessEngine() {
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
