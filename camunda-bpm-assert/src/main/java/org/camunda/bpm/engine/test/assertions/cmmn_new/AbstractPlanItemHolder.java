package org.camunda.bpm.engine.test.assertions.cmmn_new;

import org.camunda.bpm.engine.history.HistoricCaseActivityInstance;
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

  public CaseExecution getActualCaseExecution() {
    return actualCaseExecution;
  }

  public HistoricCaseActivityInstance getActualHistoricCaseActivityInstance() {
    return actualHistoricCaseActivityInstance;
  }
}
