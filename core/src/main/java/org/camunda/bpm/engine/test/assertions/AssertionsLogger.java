/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.TransitionInstance;
import org.camunda.commons.logging.BaseLogger;

/**
 * @author Ingo Richtsmeier
 *
 */
public class AssertionsLogger extends BaseLogger {
  
  public static final AssertionsLogger INSTANCE = BaseLogger.createLogger(
      AssertionsLogger.class, "BPM_ASSERT", "org.camunda.bpm.engine.test.assertions", "00");
  
  public void collectTransitionInstances(ActivityInstance activityInstance) {
    logDebug("001", "collecting transition instances for activity instance '{}' with Name '{}'", 
        activityInstance.getActivityId(),
        activityInstance.getActivityName());
  }
  
  public void foundTransitionInstances(TransitionInstance transitionInstance) {
    logDebug("002", "found transition instance '{}'", transitionInstance.getActivityId());
  }

}
