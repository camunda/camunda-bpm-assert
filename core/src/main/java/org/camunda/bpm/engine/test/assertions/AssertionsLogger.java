package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.TransitionInstance;
import org.camunda.commons.logging.BaseLogger;

public class AssertionsLogger extends BaseLogger {
  
  public static final AssertionsLogger INSTANCE = BaseLogger.createLogger(
      AssertionsLogger.class, "BPM_ASSERT", "org.camunda.bpm.engine.test.assertions", "00");
  
  public void collectTransitionInstances(ActivityInstance activityInstance) {
    logDebug("001", "collecting transition instances for activity instance '{}' with ID '{}'", 
        activityInstance.getActivityName(),
        activityInstance.getActivityId());
  }
  
  public void foundTransitionInstances(TransitionInstance transitionInstance) {
    logDebug("002", "found transition instance '{}'", transitionInstance.getActivityName());
  }

}
