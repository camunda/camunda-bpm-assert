package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.runtime.CaseExecution;

/**
 * Provides an abstraction of Stages.
 * See superclass for explanations.
 *
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @see AbstractPlanItemHolder
 */
public class StageHolder extends AbstractPlanItemHolder {

  public StageHolder(CaseExecution caseExecution, HistoricCaseActivityInstance historicCaseActivityInstance) {
    super(caseExecution, historicCaseActivityInstance);
  }
}
