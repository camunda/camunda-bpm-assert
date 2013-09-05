package org.camunda.bpm.engine.fluent;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.fluent.FluentDeploymentImpl;
import org.camunda.bpm.engine.impl.fluent.FluentProcessEngineImpl;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class FluentDeploymentTest {

  @Rule
  public final ProcessEngineRule processEngineRule = new ProcessEngineRule();

  private FluentDeployment fluentDeployment;

  @Before
  public void setUp() throws Exception {
    fluentDeployment = new FluentDeploymentImpl(new FluentProcessEngineImpl(processEngineRule.getProcessEngine()));
  }

  @Test
  public void shouldDeploySimpleProcess() {
    final String deploymentId = fluentDeployment.name("foo").addClasspathResource("SimpleProcessTest.bpmn").addClasspathResource("OtherSimpleProcessTest.bpmn")
        .deploy();
    assertNotNull(deploymentId);
    final Deployment deployment = processEngineRule.getRepositoryService().createDeploymentQuery().deploymentId(deploymentId).singleResult();
    assertNotNull(deployment);
    assertThat(deployment.getName(), is("foo"));
    assertTrue(deployment.getDeploymentTime().before(new Date()));
  }

  @Test(expected = ProcessEngineException.class)
  public void shouldFailForNonExistingFile() throws Exception {
    fluentDeployment.addClasspathResource("foo").deploy();
  }
}
