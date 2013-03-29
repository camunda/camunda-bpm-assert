package com.plexiti.activiti.test.arquillian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

import static org.fest.assertions.api.Assertions.*;


/**
 * Arquillian test to make sure that basic Arquillian infrastructure is working!
 */
@RunWith(Arquillian.class)
public class ArquillianSmokeTestIT {

  @Deployment
  public static WebArchive createDeployment() {
    MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class)
      .loadMetadataFromPom("pom.xml");

    return ShrinkWrap.create(WebArchive.class, "process-smoke-test.war")
      // add fluent assertions dependency
      .addAsLibraries(resolver.artifact("org.easytesting:fest-assert-core").resolveAsFiles())
      ;
  }

  @Test
  public void testDeploymentAndStartInstance() throws InterruptedException {
    System.out.println("Hello world!");

    assertThat(Boolean.TRUE).isEqualTo(Boolean.TRUE);
  }
}
