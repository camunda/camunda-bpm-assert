package com.plexiti.activiti.test.fluent.engine;

import com.plexiti.activiti.test.fluent.support.Classes;
import org.activiti.engine.*;
import org.activiti.engine.impl.test.TestHelper;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.ActivitiTestCase;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class TestLookups {

    private static ThreadLocal testObject = new ThreadLocal();

    public static void init(Object testObject) {
        TestLookups.testObject.set(testObject);
        TestProcessInstanceLookup.init(testObject);
    }

    public static ProcessEngine getProcessEngine() {
        if (testObject.get() instanceof ActivitiTestCase)
            return TestHelper.getProcessEngine(((ActivitiTestCase) testObject.get()).getConfigurationResource());
        else {
            try {
                return ((ActivitiRule) Classes.getFieldByType(testObject.get().getClass(), ActivitiRule.class).get(testObject.get())).getProcessEngine();
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

}
