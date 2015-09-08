package org.camunda.bpm.engine.test.assertions.cmmn;

import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.history.HistoricCaseActivityInstanceQuery;
import org.camunda.bpm.engine.impl.cmmn.entity.runtime.CaseExecutionEntity;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricCaseActivityInstanceEntity;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.test.assertions.AbstractProcessAssert;

/**
 * Created by Malte on 28.08.2015.
 */
public abstract class AbstractCaseAssert<S extends AbstractCaseAssert<S, A>, A> extends AbstractProcessAssert<S, A>
{
  protected AbstractCaseAssert(ProcessEngine engine, A actual, Class<?> selfType) {
    super(engine, actual, selfType);
  }

  @Override
  public A getActual() {
    return super.getActual();
  }

  public AbstractCaseActivityAssert activity(String activityId) {
      return activity(activityId, "Activity");
  }

  public AbstractCaseActivityAssert activity(String activityId,
          String caseExecutionType) {

    CaseExecution caseExecution = caseExecutionQuery().activityId(activityId).singleResult();
    if (caseExecution != null) {
      System.out.println("execution for activityId " + activityId + "found in state " + ((CaseExecutionEntity) caseExecution).getCurrentState());
      System.out.println(((CaseExecutionEntity) caseExecution).getActivity());

      Assertions
        .assertThat(caseExecution)
        .overridingErrorMessage(
          caseExecutionType + " '" + activityId + "' not found!")
        .isNotNull();
      Assertions.assertThat(caseExecution.getActivityType())
        .isEqualTo(caseExecutionType);

      return new CaseExecutionAssert(engine, caseExecution);
    } else {
      System.out.println("execution for activityId " + activityId + " not found in state - searching in historicActivites");
      return historicActivity(activityId, caseExecutionType);
    }
  }

  public AbstractCaseActivityAssert historicActivity(String activityId,
                                             String caseExecutionType) {
    HistoricCaseActivityInstance activityInstance =
    historyService().createHistoricCaseActivityInstanceQuery().caseActivityId(activityId).singleResult();
    //historicCaseActivityInstanceQuery()
              //.caseActivityId(activityId).singleResult();
    if(activityInstance != null) {
      System.out.println("historic for activityId " + activityId +  "found in state " + CaseExecutionState.CASE_EXECUTION_STATES.get(((HistoricCaseActivityInstanceEntity)activityInstance).getCaseActivityInstanceState()));
    }
      Assertions
              .assertThat(activityInstance)
              .overridingErrorMessage(
                caseExecutionType + " '" + activityId + "' not found!")
              .isNotNull();
      Assertions.assertThat(activityInstance.getCaseActivityType())
              .isEqualTo(caseExecutionType);

      return new HistoricCaseActivityAssert(engine, activityInstance);
  }

  protected abstract HistoricCaseActivityInstanceQuery historicCaseActivityInstanceQuery();

  public AbstractCaseActivityAssert stage(String activityId) {
      return activity(activityId, CaseActivityType.STAGE);
  }

  public AbstractCaseActivityAssert task(String activityId) {
      return activity(activityId, CaseActivityType.TASK);
  }
}
