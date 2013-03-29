package org.camunda.bpm.engine.test.fluent.assertions;


import org.camunda.bpm.engine.fluent.FluentProcessEngine;
import org.camunda.bpm.engine.repository.ProcessDefinition;

/**
 * Fluent assertions for {@link org.camunda.bpm.engine.repository.ProcessDefinition}
 *
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessDefinitionAssert extends AbstractProcessAssert<ProcessDefinitionAssert, ProcessDefinition>  {

    protected ProcessDefinitionAssert(FluentProcessEngine engine, ProcessDefinition actual) {
        super(engine, actual, ProcessDefinitionAssert.class);
    }

    public static ProcessDefinitionAssert assertThat(FluentProcessEngine engine, ProcessDefinition actual) {
        return new ProcessDefinitionAssert(engine, actual);
    }

    public ProcessDefinitionAssert isDeployed() {
        return assertThat(engine, actual).isNotNull();
    }
}
