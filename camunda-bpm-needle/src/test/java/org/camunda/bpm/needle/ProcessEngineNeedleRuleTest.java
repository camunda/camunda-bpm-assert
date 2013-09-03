package org.camunda.bpm.needle;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.guard.GuardSupport;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.test.needle.ProcessEngineNeedleRule;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.akquinet.jbosscc.needle.annotation.Mock;
import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;

public class ProcessEngineNeedleRuleTest {

    public static class A {

    }

    private static final String PROCESS_KEY = "test-process";
    private static final String PROCESS_FILE = PROCESS_KEY + ".bpmn";

    private final Logger logger = LoggerFactory.getLogger(ProcessEngineNeedleRule.class);

    @Rule
    public final ProcessEngineNeedleRule processEngineNeedleRule = ProcessEngineNeedleRule.fluentNeedleRule(this).build();

    @Inject
    private RepositoryService repositoryService;

    @Inject
    private RuntimeService runtimeService;

    @Inject
    private TaskService taskService;

    @Mock
    private JavaDelegate javaDelegate;

    @ObjectUnderTest
    private A a;

    @Test
    public void shouldInitMocksAndServices() throws Exception {
        assertNotNull(a);
        assertNotNull(runtimeService);
        assertNotNull(taskService);
        assertNotNull(javaDelegate);
        assertNotNull(repositoryService);
    }

    @Test
    @Deployment(resources = PROCESS_FILE)
    public void shouldDeployAndRunTestProcess() {
        Mocks.register("serviceTask", javaDelegate);
        final String deploymentId = repositoryService.createDeploymentQuery().singleResult().getId();
        final List<String> deploymentResources = repositoryService.getDeploymentResourceNames(deploymentId);

        logger.info("deployed resources: " + deploymentResources);

        // starts the process, activates task "wait"
        logger.debug("start process:" + PROCESS_KEY);
        runtimeService.startProcessInstanceByKey(PROCESS_KEY, GuardSupport.MAP_SKIP_GUARDS);

        // find and complete task "wait", process is finished
        final Task task = taskService.createTaskQuery().singleResult();
        logger.debug("completing task: " + task.getName());
        taskService.complete(task.getId());

        // verify no more instances running
        assertThat(runtimeService.createProcessInstanceQuery().list(), is(new IsEmptyCollection<ProcessInstance>()));
    }
}
