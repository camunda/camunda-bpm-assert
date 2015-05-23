package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.CaseInstance;

public class CaseInstanceAssert extends AbstractProcessAssert<CaseInstanceAssert, CaseInstance> {

  protected CaseInstanceAssert(ProcessEngine engine, CaseInstance actual) {
    super(engine, actual, CaseInstanceAssert.class);
  }

  @Override
  protected CaseInstance getCurrent() {
    return caseInstanceQuery().singleResult();
  }

  public static CaseInstanceAssert assertThat(ProcessEngine engine, CaseInstance actual) {
    return new CaseInstanceAssert(engine, actual);
  }

  @Override
  protected String toString(CaseInstance caseInstance) {
    return caseInstance != null ?
      String.format("actual %s {" +
          "id='%s', " +
          "caseDefinitionId='%s', " +
          "businessKey='%s'" +
          "}",
          CaseInstance.class.getSimpleName(),
          caseInstance.getId(),
          caseInstance.getCaseDefinitionId(),
          caseInstance.getBusinessKey())
        : null;
  }

}
