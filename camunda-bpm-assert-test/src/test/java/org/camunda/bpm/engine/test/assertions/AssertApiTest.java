package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.camunda.bpm.engine.test.util.CamundaBpmApi;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class AssertApiTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  public void testSupports74Api() {
    assumeApi("7.4");
    assertApi("7.4");
  }

  @Test(expected = AssertionError.class)
  public void testNotSupports74Api() {
    Assume.assumeFalse(CamundaBpmApi.supports("7.4"));
    assertApi("7.4");
  }

}
