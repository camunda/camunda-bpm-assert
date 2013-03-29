package org.camunda.bpm.engine.test.fluent;

import org.camunda.bpm.engine.fluent.FluentProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class FluentProcessEngineTestsTest {

    @Rule
    public ProcessEngineRule processEngineRule = new ProcessEngineRule();
    @Rule
    public FluentProcessEngineTestRule bpmnFluentTestRule = new FluentProcessEngineTestRule(this);
    
    private final String processDefinitionResource1 = "org/camunda/bpm/engine/test/fluent/FluentProcessEngineTestsTest.1.bpmn";
    private final String processDefinitionResource2 = "org/camunda/bpm/engine/test/fluent/FluentProcessEngineTestsTest.2.bpmn";
    private final String processDefinitionKey1 = "FluentProcessEngineTestsTest.1";
    private final String processDefinitionKey2 = "FluentProcessEngineTestsTest.2";

	@Test
	@Deployment(resources = {processDefinitionResource1})
	public void testNewProcessInstance_processDefinitionDeployed() {
		
        FluentProcessInstance processInstance = newProcessInstance(processDefinitionKey1);
        assertThat(processInstance).isNotNull();
        
	}

    @Test
    public void testNewProcessInstance_processDefinitionNotDeployed() {

        IllegalArgumentException exception = null;
        try {
            newProcessInstance(processDefinitionKey1);            
        } catch (IllegalArgumentException e) {
            exception = e;  
        }
        assertThat(exception).overridingErrorMessage("Expected IllegalArgumentException to be thrown, but expected event did not occur.").isNotNull();

    }

    @Test
    @Deployment(resources = {processDefinitionResource1, processDefinitionResource2})
    public void testProcessInstance_noInstanceBoundToThread() {

        IllegalStateException exception = null;
        try {
            processInstance();
        } catch (IllegalStateException e) {
            exception = e;
        }
        assertThat(exception).overridingErrorMessage("Expected IllegalStateException to be thrown, but expected event did not occur.").isNotNull();

    }

    @Test
    @Deployment(resources = {processDefinitionResource1, processDefinitionResource2})
    public void testProcessInstance_singleInstanceBoundToThread() {

        newProcessInstance(processDefinitionKey1);
        assertThat(processInstance()).isNotNull();

    }

    @Test
    @Deployment(resources = {processDefinitionResource1, processDefinitionResource2})
    public void testProcessInstance_multipleInstancesBoundToThread() {

        IllegalStateException exception = null;
        try {
            newProcessInstance(processDefinitionKey1);
            newProcessInstance(processDefinitionKey2);
            processInstance();
        } catch (IllegalStateException e) {
            exception = e;
        }
        assertThat(exception).overridingErrorMessage("Expected IllegalStateException to be thrown, but expected event did not occur.").isNotNull();

    }

    @Test
    @Deployment(resources = {processDefinitionResource1})
    public void testProcessDefinition_String_noInstanceBoundToThread() {

        IllegalStateException exception = null;
        try {
            processInstance(processDefinitionKey1);
        } catch (IllegalStateException e) {
            exception = e;
        }
        assertThat(exception).overridingErrorMessage("Expected IllegalStateException to be thrown, but expected event did not occur.").isNotNull();

    }

    @Test
    @Deployment(resources = {processDefinitionResource1})
    public void testProcessInstance__String_atLeastOneInstanceBoundToThread() {

        newProcessInstance(processDefinitionKey1);
        assertThat(processInstance(processDefinitionKey1)).isNotNull();

    }

}
