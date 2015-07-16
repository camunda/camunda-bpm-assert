package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.assertions.AbstractProcessAssert;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
public class CaseTaskAssert extends AbstractProcessAssert<CaseTaskAssert, CaseExecution> {

  protected CaseTaskAssert(final ProcessEngine engine, final CaseExecution actual) {
    super(engine, actual, CaseTaskAssert.class);
  }

  //TODO
  @Override
  protected CaseExecution getCurrent() {
    return caseExecutionQuery().singleResult();
  }


  //TODO
  @Override
  protected String toString(CaseExecution caseExecution) {
    return caseExecution != null ?
      String.format("actual %s {" +
          "id='%s', " +
          "caseDefinitionId='%s', " +
          "activityType='%s'" +
          "}",
        caseExecution.getClass().getSimpleName(),
        caseExecution.getId(),
        caseExecution.getCaseDefinitionId(),
        caseExecution.getActivityType())
      : null;
  }


  public CaseTaskAssert isActive() {
    assertThat(false);
    return this;
  }

  public CaseTaskAssert isCompleted() {
    assertThat(false);
    return this;
  }
}
