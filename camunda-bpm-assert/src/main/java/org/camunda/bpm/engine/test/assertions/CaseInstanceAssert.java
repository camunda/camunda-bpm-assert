package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.CaseInstance;

public class CaseInstanceAssert extends AbstractProcessAssert<CaseInstanceAssert, CaseInstance> {

  public static CaseInstanceAssert assertThat(final ProcessEngine engine, final CaseInstance actual) {
    return new CaseInstanceAssert(engine, actual);
  }

  protected CaseInstanceAssert(final ProcessEngine engine, final CaseInstance actual) {
    super(engine, actual, CaseInstance.class);
  }

  @Override
  protected CaseInstance getCurrent() {
    return caseInstanceQuery().singleResult();
  }

  @Override
  protected String toString(final CaseInstance caseInstance) {
    return caseInstance.toString();
  }
}
