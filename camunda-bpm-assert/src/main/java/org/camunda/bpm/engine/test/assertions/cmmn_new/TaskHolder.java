package org.camunda.bpm.engine.test.assertions.cmmn_new;

import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.runtime.CaseExecution;

/**
 * Created by Malte on 18.09.2015.
 */
public class TaskHolder {

  private final CaseExecution actualCaseExecution;
  private final HistoricCaseActivityInstance actualHistoricCaseActivityInstance;


  public TaskHolder(CaseExecution caseExecution) {
    this.actualCaseExecution = caseExecution;
    this.actualHistoricCaseActivityInstance = null;
  }

  public TaskHolder(HistoricCaseActivityInstance historicCaseActivityInstance) {
    this.actualCaseExecution = null;
    this.actualHistoricCaseActivityInstance = historicCaseActivityInstance;
  }


  public CaseExecution getActualCaseExecution() {
    return actualCaseExecution;
  }

  public HistoricCaseActivityInstance getActualHistoricCaseActivityInstance() {
    return actualHistoricCaseActivityInstance;
  }
}
