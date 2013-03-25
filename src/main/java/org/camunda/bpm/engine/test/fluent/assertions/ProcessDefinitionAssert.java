package org.camunda.bpm.engine.test.fluent.assertions;


import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.fest.assertions.api.AbstractAssert;

/**
 * Fluent assertions for {@link org.camunda.bpm.engine.repository.ProcessDefinition}
 *
 * @author Rafael Cordones <rafael.cordones@camunda.com>
 * @author Martin Schimak <martin.schimak@camunda.com>
 */
public class ProcessDefinitionAssert extends AbstractAssert<ProcessDefinitionAssert, ProcessDefinition> {

    protected ProcessDefinitionAssert(ProcessDefinition actual) {
        super(actual, ProcessDefinitionAssert.class);
    }

    public static ProcessDefinitionAssert assertThat(ProcessDefinition actual) {
        return new ProcessDefinitionAssert(actual);
    }

    public ProcessDefinitionAssert isDeployed() {
        return assertThat(actual).isNotNull();
    }
}
