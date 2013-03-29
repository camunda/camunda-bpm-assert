package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.repository.ProcessDefinition;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class FluentProcessDefinitionRepository {

    /**
     * @see org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests#processDefinition(String)
     */
    public static FluentProcessDefinition processDefinition(String processDefinitionKey) {
        ProcessDefinition definition =
                FluentLookups.getRepositoryService().createProcessDefinitionQuery()
                        .processDefinitionKey(processDefinitionKey)
                        .latestVersion()
                        .singleResult();
        if (definition == null)
            throw new IllegalArgumentException("No such process definition (with the given processDefinitionKey '" + processDefinitionKey + "') is deployed.");
        return new FluentProcessDefinitionImpl(definition);
    }

}
