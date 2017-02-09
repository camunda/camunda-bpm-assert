package org.camunda.bpm.engine.test.assertions.examples;

import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ClaimsCaseTest {

  @Rule
  public final ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = "camunda-testing-claims.cmmn")
  public void testDeployment() throws Exception {
    // nothing here, test successful if deployment works
    assertThat(Mockito.mock(CaseExecution.class)).isNotNull();
  }
  
}
