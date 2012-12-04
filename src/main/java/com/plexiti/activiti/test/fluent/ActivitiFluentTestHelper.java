package com.plexiti.activiti.test.fluent;

import org.activiti.engine.*;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.test.ActivitiRule;

/**
 * TODO: this could could probably be refactored into org.activiti.engine.impl.test.TestHelper
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class ActivitiFluentTestHelper {

    private static ThreadLocal<ProcessEngine> processEngine = new ThreadLocal<ProcessEngine>();
    private static ThreadLocal<RepositoryService> repositoryService = new ThreadLocal<RepositoryService>();
    private static ThreadLocal<RuntimeService> runtimeService = new ThreadLocal<RuntimeService>();
    private static ThreadLocal<TaskService> taskService = new ThreadLocal<TaskService>();
    private static ThreadLocal<HistoryService> historyService = new ThreadLocal<HistoryService>();
    private static ThreadLocal<IdentityService> identityService = new ThreadLocal<IdentityService>();
    private static ThreadLocal<ManagementService> managementService = new ThreadLocal<ManagementService>();
    private static ThreadLocal<FormService> formService = new ThreadLocal<FormService>();
    private static ThreadLocal<String> moveToActivityId = new ThreadLocal<String>();

    public ProcessEngine getProcessEngine() {
        return processEngine.get();
    }

    public static RepositoryService getRepositoryService() {
        return repositoryService.get();
    }

    public static RuntimeService getRuntimeService() {
        return runtimeService.get();
    }

    public static TaskService getTaskService() {
        return taskService.get();
    }

    public static HistoryService getHistoryService() {
        return historyService.get();
    }

    public static IdentityService getIdentityService() {
        return identityService.get();
    }

    public static ManagementService getManagementService() {
        return managementService.get();
    }

    public static FormService getFormService() {
        return formService.get();
    }

    public static String getMoveToActivityId() {
        return moveToActivityId.get();
    }

    public static void setMoveToActivityId(String moveToActivityId) {
        ActivitiFluentTestHelper.moveToActivityId.set(moveToActivityId);
    }

    public static void setUpEngineAndServices(ProcessEngine processEngine, RepositoryService repositoryService,
                                              RuntimeService runtimeService, TaskService taskService,
                                              HistoryService historicDataService, IdentityService identityService,
                                              ManagementService managementService, FormService formService) {
        ActivitiFluentTestHelper.processEngine.set(processEngine);
        ActivitiFluentTestHelper.repositoryService.set(repositoryService);
        ActivitiFluentTestHelper.runtimeService.set(runtimeService);
        ActivitiFluentTestHelper.taskService.set(taskService);
        ActivitiFluentTestHelper.historyService.set(historicDataService);
        ActivitiFluentTestHelper.identityService.set(identityService);
        ActivitiFluentTestHelper.managementService.set(managementService);
        ActivitiFluentTestHelper.formService.set(formService);
    }

}
