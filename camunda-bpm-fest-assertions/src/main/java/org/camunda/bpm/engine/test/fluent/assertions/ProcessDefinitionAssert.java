package org.camunda.bpm.engine.test.fluent.assertions;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.ProcessDefinition;

/**
 * Asserts for a {@link ProcessDefinition}
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael@cordones.me>
 */
public class ProcessDefinitionAssert extends AbstractProcessAssert<ProcessDefinitionAssert, ProcessDefinition> {

  protected ProcessDefinitionAssert(ProcessEngine engine, ProcessDefinition actual) {
    super(engine, actual, ProcessDefinitionAssert.class);
  }

  public static ProcessDefinitionAssert assertThat(ProcessEngine engine, ProcessDefinition actual) {
    return new ProcessDefinitionAssert(engine, actual);
  }

}
