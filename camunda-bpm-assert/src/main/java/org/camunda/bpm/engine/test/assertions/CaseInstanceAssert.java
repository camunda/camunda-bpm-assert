package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.CaseInstance;

public class CaseInstanceAssert extends AbstractProcessAssert<CaseInstanceAssert, CaseInstance> {

  public static CaseInstanceAssert assertThat(final ProcessEngine engine, final CaseInstance actual) {
    return new CaseInstanceAssert(engine, actual);
  }

  protected CaseInstanceAssert(final ProcessEngine engine, final CaseInstance actual) {
    super(engine, actual, CaseInstanceAssert.class);
  }

  @Override
  protected CaseInstance getCurrent() {
    return caseInstanceQuery().singleResult();
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
