package org.camunda.bpm.engine.fluent;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public interface FluentProcessDefinitionRepository {

  FluentProcessDefinition processDefinition(String processDefinitionKey);

}
