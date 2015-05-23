package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.CaseDefinition;

public class CaseDefinitionAssert extends AbstractProcessAssert<CaseDefinitionAssert, CaseDefinition> {

  protected CaseDefinitionAssert(ProcessEngine engine, CaseDefinition actual) {
    super(engine, actual, CaseDefinitionAssert.class);
  }

  @Override
  protected CaseDefinition getCurrent() {
    return caseDefinitionQuery().singleResult();
  }


  public static CaseDefinitionAssert assertThat(ProcessEngine engine, CaseDefinition actual) {
    return new CaseDefinitionAssert(engine, actual);
  }

  @Override
  protected String toString(CaseDefinition caseDefinition) {
    return caseDefinition != null ?
        String.format("actual %s {" +
          "id='%s', " +
          "name='%s', " +
          "resourcename='%s', " +
          "deploymentId='%s'" +
          "}",
          CaseDefinition.class.getSimpleName(),
          caseDefinition.getId(),
          caseDefinition.getName(),
          caseDefinition.getResourceName(),
          caseDefinition.getDeploymentId())
        : null;
  }

}
