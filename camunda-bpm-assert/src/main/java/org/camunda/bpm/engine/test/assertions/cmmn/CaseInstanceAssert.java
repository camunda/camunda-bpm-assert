package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.impl.cmmn.execution.CmmnActivityExecution;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.assertions.AbstractProcessAssert;

public class CaseInstanceAssert extends AbstractProcessAssert<CaseInstanceAssert, CaseInstance> {

  public static CaseInstanceAssert assertThat(final ProcessEngine engine, final CaseInstance actual) {
    return new CaseInstanceAssert(engine, actual);
  }

  protected CaseInstanceAssert(final ProcessEngine engine, final CaseInstance actual) {
    super(engine, actual, CaseInstanceAssert.class);
  }

  public CaseInstanceAssert isActive() {
    CmmnActivityExecution caseExecution = (CmmnActivityExecution) caseService()
      .createCaseExecutionQuery()
      .caseExecutionId(getCurrent().getId())
      .singleResult();

    CaseExecutionState currentState = caseExecution.getCurrentState();

    Assertions.assertThat(currentState)
      .overridingErrorMessage("Expected case instance '"+toString(getActual()) + "' to be active, but it is '"+currentState+"'")
      .isEqualTo(CaseExecutionState.ACTIVE);
    return this;
  }

  public CaseTaskAssert task(String activityId) {
    CaseExecution caseExecution = caseService().createCaseExecutionQuery().caseInstanceId(getActual().getCaseInstanceId()).activityId(activityId).singleResult();
    Assertions.assertThat(caseExecution).overridingErrorMessage("Task '"+activityId+"' not found!").isNotNull();
    return new CaseTaskAssert(engine, caseExecution);
  }

  @Override
  protected CaseInstance getCurrent() {
    return caseInstanceQuery().singleResult();
  }


  @Override
  protected String toString(CaseInstance caseInstance) {
    return caseInstance != null ?
      String.format("%s {" +
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
