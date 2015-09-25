package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
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
}
