package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.impl.cmmn.execution.CmmnExecution;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricCaseActivityInstanceEntity;
import org.camunda.bpm.engine.runtime.CaseExecution;

/**
 * Provides an abstraction of plan items.
 * Plan items are represented as either CaseExecutions or HistoricCaseActivityInstances (and often both), making it hard to write test
 * code that provides meaningful error messages.
 * <p/>
 * An example:
 * Given a stage that is expected to be enabled but is falsely completed.
 * When trying to start the stage, the CaseExecution is needed, but a completed stage doesn't have that anymore!
 * To provide a meaningful error message ("cannot start stage, because it is state xxx"), the HistoricCaseActivityInstance
 * is needed.
 * <p/>
 * This class holds both CaseExecution and HistoricCaseActivityInstance.
 *
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
public class AbstractPlanItemHolder {
  protected final CaseExecution actualCaseExecution;
  protected final HistoricCaseActivityInstance actualHistoricCaseActivityInstance;

  public AbstractPlanItemHolder(CaseExecution caseExecution, HistoricCaseActivityInstance historicCaseActivityInstance) {
    this.actualCaseExecution = caseExecution;
    this.actualHistoricCaseActivityInstance = historicCaseActivityInstance;
  }

  public CaseExecutionState actualState() {
    if (actualCaseExecution != null) {
      return ((CmmnExecution) actualCaseExecution).getCurrentState();
    } else if (actualHistoricCaseActivityInstance != null) {
      return CaseExecutionState.CASE_EXECUTION_STATES.get(
        ((HistoricCaseActivityInstanceEntity) actualHistoricCaseActivityInstance).getCaseActivityInstanceState()
      );
    } else {
      return null;
    }
  }

  public String actualType() {
    if (actualCaseExecution != null) {
      return actualCaseExecution.getActivityType();
    } else if (actualHistoricCaseActivityInstance != null) {
      return actualHistoricCaseActivityInstance.getCaseActivityType();
    } else {
      return null;
    }
  }

  public CaseExecution getActualCaseExecution() {
    return actualCaseExecution;
  }

  public HistoricCaseActivityInstance getActualHistoricCaseActivityInstance() {
    return actualHistoricCaseActivityInstance;
  }

  public String getCaseInstanceId() {
    if (actualCaseExecution != null) {
      return actualCaseExecution.getCaseInstanceId();
    } else if (actualHistoricCaseActivityInstance != null) {
      return actualHistoricCaseActivityInstance.getCaseInstanceId();
    } else {
      return null;
    }
  }

  public String getId() {
    if (actualCaseExecution != null) {
      return actualCaseExecution.getActivityId();
    } else if (actualHistoricCaseActivityInstance != null) {
      return actualHistoricCaseActivityInstance.getCaseActivityId();
    } else {
      return null;
    }
  }
}
