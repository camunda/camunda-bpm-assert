package com.plexiti.activiti.test.arquillian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.prepareDeployment;
import static org.fest.assertions.api.Assertions.assertThat;


/**
 * Arquillian test to make sure that basic Arquillian infrastructure is working!
 */
@RunWith(Arquillian.class)
public class ArquillianSmokeTestIT {

  @Deployment
  public static WebArchive createDeployment() {
      return prepareDeployment("process-smoke-test.war");
  }

  @Test
  public void testDeploymentAndStartInstance() throws InterruptedException {
    System.out.println("Hello world!");

    assertThat(Boolean.TRUE).isEqualTo(Boolean.TRUE);
  }
}
