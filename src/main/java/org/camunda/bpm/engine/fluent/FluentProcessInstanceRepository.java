package org.camunda.bpm.engine.fluent;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public interface FluentProcessInstanceRepository {
    
    FluentProcessInstance newProcessInstance(String processDefinitionKey);

}
