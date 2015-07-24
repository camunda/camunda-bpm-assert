package org.camunda.bpm.engine.test.assertions.cmmn;

import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 * @author Martin Günther <martin.guenter@holisticon.de>
 */
public class TaskWithSentryTest {

  public static final String TASK_A = "PI_HT_A";
  public static final String TASK_B = "PI_HT_B";

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "cmmn/TaskWithSentryTest.cmmn"
  })
  public void todo_write_tests() {
  }

}
