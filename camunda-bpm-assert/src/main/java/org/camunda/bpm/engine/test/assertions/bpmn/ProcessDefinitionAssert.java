package org.camunda.bpm.engine.test.assertions.bpmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.*;

/**
 * Assertions for a {@link ProcessDefinition}
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael@cordones.me>
 */
public class ProcessDefinitionAssert extends AbstractProcessAssert<ProcessDefinitionAssert, ProcessDefinition> {

  protected ProcessDefinitionAssert(ProcessEngine engine, ProcessDefinition actual) {
    super(engine, actual, ProcessDefinitionAssert.class);
  }

  protected static ProcessDefinitionAssert assertThat(ProcessEngine engine, ProcessDefinition actual) {
    return new ProcessDefinitionAssert(engine, actual);
  }

  @Override
  protected ProcessDefinition getCurrent() {
    return processDefinitionQuery().singleResult();
  }

  @Override
  protected String toString(ProcessDefinition processDefinition) {
    return processDefinition != null ?
      String.format("%s {" +
        "id='%s', " +
        "name='%s', " +
        "description='%s', " +
        "deploymentId='%s'}",
        ProcessDefinition.class.getSimpleName(),
        processDefinition.getId(),
        processDefinition.getName(),
        processDefinition.getDescription(),
        processDefinition.getDeploymentId())
      : null;
  }

  /**
   * Verifies the expectation that the {@link ProcessDefinition} currently has the 
   * specified number of active instances, iow neither suspended nor ended instances.
   *
   * @param   number the number of expected active instances 
   * @return  this {@link ProcessDefinitionAssert}
   */
  public ProcessDefinitionAssert hasActiveInstances(final long number) {
    long instances = processInstanceQuery().active().count();
    Assertions      
      .assertThat(instances)
      .overridingErrorMessage("Expecting %s to have %s active instances, but found it to have %s.",
        getCurrent(), number, instances 
      )
      .isEqualTo(number);
    return this;
  }
  
  /* ProcessInstanceQuery, automatically narrowed to actual {@link ProcessDefinition} */
  @Override
  protected ProcessInstanceQuery processInstanceQuery() {
    return super.processInstanceQuery().processDefinitionId(actual.getId());
  }

  /* ProcessDefinitionQuery, automatically narrowed to actual {@link ProcessDefinition} */
  @Override
  protected ProcessDefinitionQuery processDefinitionQuery() {
    return super.processDefinitionQuery().processDefinitionId(actual.getId());
  }
  
}
