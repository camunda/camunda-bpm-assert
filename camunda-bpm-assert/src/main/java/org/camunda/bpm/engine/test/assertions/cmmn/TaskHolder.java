package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.impl.cmmn.execution.CmmnExecution;
import org.camunda.bpm.engine.runtime.CaseExecution;

/**
 * Provides an abstraction of human, case and process tasks.
 * See superclass for explanations.
 *
 * @see AbstractPlanItemHolder
 *
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
public class TaskHolder extends AbstractPlanItemHolder {

  public TaskHolder(CaseExecution caseExecution, HistoricCaseActivityInstance historicCaseActivityInstance) {
    super(caseExecution, historicCaseActivityInstance);
  }

  public boolean isActive() {
    if (actualCaseExecution != null) {
      return actualCaseExecution.isActive();
    } else if (actualHistoricCaseActivityInstance != null) {
      return actualHistoricCaseActivityInstance.isActive();
    } else {
      return false;
    }
  }

  public boolean isAvailable() {
    if (actualCaseExecution != null) {
      return actualCaseExecution.isAvailable();
    } else if (actualHistoricCaseActivityInstance != null) {
      return actualHistoricCaseActivityInstance.isAvailable();
    } else {
      return false;
    }
  }

  public boolean isCompleted() {
    if (actualCaseExecution != null) {
      return ((CmmnExecution) actualCaseExecution).isCompleted();
    } else if (actualHistoricCaseActivityInstance != null) {
      return actualHistoricCaseActivityInstance.isCompleted();
    } else {
      return false;
    }
  }

  public boolean isEnabled() {
    if (actualCaseExecution != null) {
      return actualCaseExecution.isEnabled();
    } else if (actualHistoricCaseActivityInstance != null) {
      return actualHistoricCaseActivityInstance.isEnabled();
    } else {
      return false;
    }
  }

}