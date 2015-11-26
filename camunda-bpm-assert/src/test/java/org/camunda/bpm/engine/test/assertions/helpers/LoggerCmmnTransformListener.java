package org.camunda.bpm.engine.test.assertions.helpers;

import org.camunda.bpm.engine.delegate.CaseExecutionListener;
import org.camunda.bpm.engine.delegate.DelegateCaseExecution;
import org.camunda.bpm.engine.impl.cmmn.model.CmmnActivity;
import org.camunda.bpm.engine.impl.cmmn.transformer.AbstractCmmnTransformListener;
import org.camunda.bpm.model.cmmn.instance.*;

/**
 * Created by Malte on 08.09.2015.
 */
public class LoggerCmmnTransformListener extends AbstractCmmnTransformListener {
  public static CaseExecutionListener listener = new CaseExecutionListener() {
    public void notify(DelegateCaseExecution var1) throws Exception {
      System.out.printf("Execution Event: %s %s\n", var1.getEventName(), var1.getActivityId());
    }

    ;
  };

  protected void addListeners(CmmnActivity activity) {
    if (activity != null) {
      activity.addBuiltInListener(CaseExecutionListener.CREATE, listener);
      activity.addBuiltInListener(CaseExecutionListener.ENABLE, listener);
      activity.addBuiltInListener(CaseExecutionListener.START, listener);
      activity.addBuiltInListener(CaseExecutionListener.MANUAL_START, listener);
      activity.addBuiltInListener(CaseExecutionListener.COMPLETE, listener);
      activity.addBuiltInListener(CaseExecutionListener.TERMINATE, listener);
      activity.addBuiltInListener(CaseExecutionListener.OCCUR, listener);
    }
  }

  public void transformHumanTask(PlanItem planItem, HumanTask humanTask, CmmnActivity activity) {
    addListeners(activity);
  }

  public void transformProcessTask(PlanItem planItem, ProcessTask processTask, CmmnActivity activity) {
    addListeners(activity);
  }

  public void transformCaseTask(PlanItem planItem, CaseTask caseTask, CmmnActivity activity) {
    addListeners(activity);
  }

  public void transformTask(PlanItem planItem, Task task, CmmnActivity activity) {
    addListeners(activity);
  }

  public void transformStage(PlanItem planItem, Stage stage, CmmnActivity activity) {
    addListeners(activity);
  }

  public void transformMilestone(PlanItem planItem, Milestone milestone, CmmnActivity activity) {
    addListeners(activity);
  }
}

