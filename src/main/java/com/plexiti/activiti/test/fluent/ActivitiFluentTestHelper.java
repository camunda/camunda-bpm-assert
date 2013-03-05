package com.plexiti.activiti.test.fluent;

import org.activiti.engine.*;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.test.TestHelper;
import org.activiti.engine.test.ActivitiRule;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class ActivitiFluentTestHelper {

    private static ThreadLocal<String> moveToActivityId = new ThreadLocal<String>();

    public static ProcessEngine getProcessEngine() {
        return TestHelper.getProcessEngine("activiti.cfg.xml");
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

    public static String getMoveToActivityId() {
        return moveToActivityId.get();
    }

    public static void setMoveToActivityId(String moveToActivityId) {
        ActivitiFluentTestHelper.moveToActivityId.set(moveToActivityId);
    }

}
