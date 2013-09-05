package org.camunda.bpm.engine.impl.fluent;

import org.camunda.bpm.engine.fluent.AbstractFluentProcessEngineAware;
import org.camunda.bpm.engine.fluent.FluentProcessDefinition;
import org.camunda.bpm.engine.fluent.FluentProcessDefinitionRepository;
import org.camunda.bpm.engine.fluent.FluentProcessEngine;
import org.camunda.bpm.engine.repository.ProcessDefinition;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class FluentProcessDefinitionRepositoryImpl extends AbstractFluentProcessEngineAware implements FluentProcessDefinitionRepository {

    protected FluentProcessDefinitionRepositoryImpl(FluentProcessEngine engine) {
        super(engine);
    }

    /**
     * @see org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests#processDefinition(String)
     */
    @Override
    public FluentProcessDefinition processDefinition(String processDefinitionKey) {
        ProcessDefinition definition =
                engine.getRepositoryService().createProcessDefinitionQuery()
                        .processDefinitionKey(processDefinitionKey)
                        .latestVersion()
                        .singleResult();
        if (definition == null)
            throw new IllegalArgumentException("No such process definition (with the given processDefinitionKey '" + processDefinitionKey + "') is deployed.");
        return new FluentProcessDefinitionImpl(engine, definition);
    }

}
