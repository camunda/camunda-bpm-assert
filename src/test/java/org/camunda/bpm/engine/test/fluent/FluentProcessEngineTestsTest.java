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
    
    private final String processDefinitionResource = "org/camunda/bpm/engine/test/fluent/FluentProcessEngineTestsTest.testNewProcessInstance.bpmn";
    private final String processDefinitionId = "testNewProcessInstance";

	@Test
	@Deployment(resources = { processDefinitionResource })
	public void testNewProcessInstance_processDefinitionDeployed() {
		
        FluentProcessInstance processInstance = newProcessInstance(processDefinitionId);
        assertThat(processInstance).isNotNull();
        
	}

    @Test
    public void testNewProcessInstance_processDefinitionNotDeployed() {

        IllegalArgumentException exception = null;
        try {
            newProcessInstance(processDefinitionId);            
        } catch (IllegalArgumentException e) {
            exception = e;  
        }
        assertThat(exception).overridingErrorMessage("Expected IllegalArgumentException to be thrown, but expected event did not occur.").isNotNull();

    }

}
