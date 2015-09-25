package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
import org.camunda.bpm.engine.runtime.CaseExecution;

/**
 * Provides an abstraction of milestones.
 * See superclass for explanations.
 *
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @see AbstractPlanItemHolder
 */
public class MilestoneHolder extends AbstractPlanItemHolder {

  public MilestoneHolder(CaseExecution caseExecution, HistoricCaseActivityInstance historicCaseActivityInstance) {
    super(caseExecution, historicCaseActivityInstance);
  }
}
