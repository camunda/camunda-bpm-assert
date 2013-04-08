package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTestRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.assertThat;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class FluentProcessInstanceTest {

    @Rule
    public ProcessEngineRule processEngineRule = new ProcessEngineRule();
    @Rule
    public FluentProcessEngineTestRule bpmnFluentTestRule = new FluentProcessEngineTestRule(this);

    private final String processDefinitionResource1 = "org/camunda/bpm/engine/fluent/FluentProcessInstanceTest.1.bpmn";
    private final String processDefinitionKey1 = "FluentProcessInstanceTest.1";
    private final String processDefinitionResource2 = "org/camunda/bpm/engine/fluent/FluentProcessInstanceTest.2.bpmn";
    private final String processDefinitionKey2 = "FluentProcessInstanceTest.2";
    private final String processDefinitionResource3 = "org/camunda/bpm/engine/fluent/FluentProcessInstanceTest.3.bpmn";
    private final String processDefinitionKey3 = "FluentProcessInstanceTest.3";
    private final String processDefinitionResource4 = "org/camunda/bpm/engine/fluent/FluentProcessInstanceTest.4.bpmn";
    private final String processDefinitionKey4 = "FluentProcessInstanceTest.4";

    @Test
    @Deployment(resources = {processDefinitionResource1})
    public void testStart_Ok() {

        FluentProcessInstance processInstance =
                newProcessInstance(processDefinitionKey1)
                .start();

        assertThat(processInstance)
                .isNotNull()
                .isStarted()
                .isWaitingAt("theTask");

    }

    @Test
    @Deployment(resources = {processDefinitionResource1})
    public void testStartAndMoveTo_ActivitiIdDoesExist() {

        FluentProcessInstance processInstance =
                newProcessInstance(processDefinitionKey1, new Move() {
                    public void along() {
                        testStart_Ok();
                    }
                })
                .startAndMoveTo("theTask");

        assertThat(processInstance)
                .isNotNull()
                .isStarted()
                .isWaitingAt("theTask");

    }

    @Test
    @Deployment(resources = {processDefinitionResource1})
    public void testStartAndMoveTo_ActivitiIdDoesNotExist() {

        FluentProcessInstance processInstance = null;
        IllegalArgumentException exception = null;
        try {
            processInstance =
                    newProcessInstance(processDefinitionKey1, new Move() {
                        public void along() {
                            testStart_Ok();
                        }
                    })
                    .startAndMoveTo("theNonExistingTask");
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        assertThat(exception)
                .overridingErrorMessage("Expected IllegalArgumentException to be thrown, but expected event did not occur.")
                .isNotNull();
        assertThat(processInstance).isNull();

    }

    @Test
    @Deployment(resources = {processDefinitionResource1})
    public void testVariable_StartAfterSetting() {

        FluentProcessInstance processInstance =
                newProcessInstance(processDefinitionKey1)
                    .setVariable("theKey", "theValue");

        assertThat(processInstance.getVariable("theKey"))
                .isNotNull()
                .isDefined()
                .asString()
                .isEqualTo("theValue");

        processInstance.start();

        assertThat(processInstance)
                .isStarted();

        assertThat(processInstance.getVariable("theKey"))
                .isNotNull()
                .isDefined()
                .asString()
                .isEqualTo("theValue");
    }

    @Test
    @Deployment(resources = {processDefinitionResource1})
    public void testVariable_StartBeforeSetting() {

        FluentProcessInstance processInstance =
                newProcessInstance(processDefinitionKey1)
                        .start();

        processInstance.setVariable("theKey", "theValue");

        assertThat(processInstance)
                .isNotNull()
                .isStarted();

        assertThat(processInstance.getVariable("theKey"))
                .isNotNull()
                .isDefined()
                .asString()
                .isEqualTo("theValue");

    }

    @Test
    @Deployment(resources = {processDefinitionResource1})
    public void testVariable_KeyDoesExist() {

        FluentProcessInstance processInstance =
                newProcessInstance(processDefinitionKey1)
                        .start();

        processInstance.setVariable("theKey", "theValue");

        assertThat(processInstance.getVariable("theKey"))
                .isNotNull()
                .isDefined()
                .asString()
                .isEqualTo("theValue");

    }

    @Test
    @Deployment(resources = {processDefinitionResource1})
    public void testVariable_KeyDoesNotExist() {

        FluentProcessInstance processInstance =
                newProcessInstance(processDefinitionKey1)
                        .start();

        processInstance.setVariable("theKey", "theValue");

        IllegalArgumentException exception = null;
        try {
            processInstance.getVariable("theNonExistingKey");
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        assertThat(exception)
                .overridingErrorMessage("Expected IllegalArgumentException to be thrown, but expected event did not occur.")
                .isNotNull();

    }

    @Test
    @Deployment(resources = {processDefinitionResource1})
    public void testTask_noTaskWaiting() {

        newProcessInstance(processDefinitionKey1)
                .start();
        processInstance().task().complete();
        assertThat(processInstance().task()).isNull();
        
    }

    @Test
    @Deployment(resources = {processDefinitionResource1})
    public void testTask_singleTaskWaiting() {

        FluentProcessInstance processInstance1 = newProcessInstance(processDefinitionKey1)
                .start();
        FluentProcessInstance processInstance2 = newProcessInstance(processDefinitionKey1)
                .start();
        assertThat(processInstance1.task()).isNotNull();
        assertThat(processInstance2.task()).isNotNull();

    }

    @Test
    @Deployment(resources = {processDefinitionResource2})
    public void testTask_multipleTasksWaiting() {

        IllegalStateException exception = null;
        try {
            newProcessInstance(processDefinitionKey2)
                    .start();
            processInstance()
                    .task();
        } catch (IllegalStateException e) {
            exception = e;
        }
        assertThat(exception).overridingErrorMessage("Expected IllegalStateException to be thrown, but expected event did not occur.").isNotNull();

    }

    @Test
    @Deployment(resources = {processDefinitionResource1, processDefinitionResource3})
    public void testJob_noJobWaitingInANotYetStartedInstance() {

        FluentProcessInstance instance2 = newProcessInstance(processDefinitionKey3)
            .setVariable("dueDate", new Date());

        assertThat(instance2.job()).isNull();
        
    }

    @Test
    @Deployment(resources = {processDefinitionResource1, processDefinitionResource3})
    public void testJob_noJobWaitingInAStartedInstance() {

        newProcessInstance(processDefinitionKey1)
                .start();

        assertThat(processInstance().job()).isNull();

    }

    @Test
    @Deployment(resources = {processDefinitionResource3})
    public void testJob_singleJobWaiting() {

        newProcessInstance(processDefinitionKey3)
            .setVariable("dueDate", new Date())
            .start()
        ;

        assertThat(processInstance().job()).isNotNull();

    }

    @Test
    @Deployment(resources = {processDefinitionResource4})
    public void testJob_multipleJobsWaiting() {

        IllegalStateException exception = null;
        try {
            newProcessInstance(processDefinitionKey4)
                .setVariable("dueDate", new Date())
                .start();
            processInstance()
                    .job();
        } catch (IllegalStateException e) {
            exception = e;
        }
        assertThat(exception).overridingErrorMessage("Expected IllegalStateException to be thrown, but expected event did not occur.").isNotNull();

    }

}
