package com.plexiti.activiti.test.fluent.assertions;


import com.plexiti.activiti.test.fluent.ActivitiFluentTestHelper;
import org.activiti.engine.repository.ProcessDefinition;
import org.fest.assertions.api.AbstractAssert;

/**
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 * @author Martin Schimak <martin.schimak@plexiti.com>
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
