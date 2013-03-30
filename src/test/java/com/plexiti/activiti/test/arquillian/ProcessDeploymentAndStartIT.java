package com.plexiti.activiti.test.arquillian;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

import javax.inject.Inject;
import static org.fest.assertions.api.Assertions.*;
import java.util.HashMap;
import java.util.List;

@RunWith(Arquillian.class)
public class ProcessDeploymentAndStartIT {

  @Deployment
  public static WebArchive createDeployment() {
    MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class)
      .goOffline()
      .loadMetadataFromPom("pom.xml");

    return ShrinkWrap.create(WebArchive.class, "process-smoke-test.war")
      .addAsLibraries(resolver.artifact("org.camunda.bpm:camunda-engine-cdi").resolveAsFiles())
      .addAsLibraries(resolver.artifact("org.camunda.bpm.javaee:camunda-ejb-client").resolveAsFiles())

      // prepare as process application archive for fox platform
      .addAsWebResource("META-INF/processes.xml", "WEB-INF/classes/META-INF/processes.xml")
      .addAsResource("com/plexiti/activiti/showcase/jobannouncement/process/job-announcement.bpmn")
      .addAsResource("com/plexiti/activiti/showcase/jobannouncement/process/job-announcement-publication.bpmn")

      // add fluent assertions dependency
      .addAsLibraries(resolver.artifact("org.easytesting:fest-assert-core").resolveAsFiles())
      ;
  }

  @Inject
  private RuntimeService runtimeService;

  @Test
  public void testDeploymentAndStartInstance() throws InterruptedException {
    assertThat(runtimeService).isNotNull();

    HashMap<String, Object> variables = new HashMap<String, Object>();
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("job-announcement", variables);
    String id = processInstance.getId();
    assertThat(processInstance).isNotNull();
    System.out.println("Started process instance id " + id);

    List<String> activityIds = runtimeService.getActiveActivityIds(id);

    assertThat(activityIds.size()).isEqualTo(1);
    assertThat("edit").isIn(activityIds);
  }
}
