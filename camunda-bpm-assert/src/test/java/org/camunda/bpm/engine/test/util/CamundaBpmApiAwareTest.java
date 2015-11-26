package org.camunda.bpm.engine.test.util;

import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.camunda.bpm.engine.test.util.CamundaBpmApi;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class CamundaBpmApiAwareTest {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  public void testSupports74Api() {
    CamundaBpmApi.supports("7.0");
    CamundaBpmApi.supports("7.1");
    CamundaBpmApi.supports("7.2");
    CamundaBpmApi.supports("7.3");
    CamundaBpmApi.supports("7.4");
  }

}
