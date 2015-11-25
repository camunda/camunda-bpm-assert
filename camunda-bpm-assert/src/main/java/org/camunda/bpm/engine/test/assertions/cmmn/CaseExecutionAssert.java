package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.CaseExecution;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenther@holisticon.de>
 */
public class CaseExecutionAssert extends AbstractCaseAssert<CaseExecutionAssert, CaseExecution> {

  protected CaseExecutionAssert(final ProcessEngine engine, final CaseExecution actual) {
    super(engine, actual, CaseExecutionAssert.class);
  }

  public static CaseExecutionAssert assertThat(final ProcessEngine engine, final CaseExecution actual) {
    return new CaseExecutionAssert(engine, actual);
  }

  @Override
  public CaseInstanceAssert isCaseInstance() {
    return super.isCaseInstance();
  }

  @Override
  public StageAssert isStage() {
    return super.isStage();
  }

  @Override
  public HumanTaskAssert isHumanTask() {
    return super.isHumanTask();
  }

  @Override
  public ProcessTaskAssert isProcessTask() {
    return super.isProcessTask();
  }

  @Override
  public CaseTaskAssert isCaseTask() {
    return super.isCaseTask();
  }

  @Override
  public MilestoneAssert isMilestone() {
    return super.isMilestone();
  }

}
