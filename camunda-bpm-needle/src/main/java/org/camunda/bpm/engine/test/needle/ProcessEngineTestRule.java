package org.camunda.bpm.engine.test.needle;

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
import org.junit.rules.TestRule;

/**
 * Combined interface of {@link ProcessEngine} and {@link TestRule}.
 * @author Jan Galinski, Holisticon AG (jan.galinski@holisticon.de)
 */
interface ProcessEngineTestRule extends TestRule, ProcessEngine {

    /**
     * Sets current toime of in memory engine. Use to test timers etc.
     * @param currentTime time to set
     */
    void setCurrentTime(Date currentTime);

    /**
     * Provide deployment id after deploying with @Deployment-annotation.
     * @return current deployment id
     */
    String getDeploymentId();

    /**
     * Get the process engine.
     * @return the process engine
     */
    ProcessEngine getProcessEngine();

    @Override
    TaskService getTaskService();

    @Override
    HistoryService getHistoryService();

    @Override
    IdentityService getIdentityService();

    @Override
    ManagementService getManagementService();

    @Override
    String getName();

    @Override
    RepositoryService getRepositoryService();

    @Override
    RuntimeService getRuntimeService();

    @Override
    FormService getFormService();

    @Override
    AuthorizationService getAuthorizationService();

    /**
     * @deprecated The rule does not allow closing the engine directly.
     * @see org.camunda.bpm.engine.ProcessEngine#close()
     */
    @Override
    @Deprecated
    void close();
}
