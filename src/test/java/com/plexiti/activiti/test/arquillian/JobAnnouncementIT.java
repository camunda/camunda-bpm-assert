package com.plexiti.activiti.test.arquillian;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.fluent.FluentLookups;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;
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

import static com.plexiti.activiti.showcase.jobannouncement.process.ProcessConstants.*;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;

@RunWith(Arquillian.class)
public class JobAnnouncementIT {

  @Deployment
  public static WebArchive createDeployment() {
    MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class)
      .goOffline()
      .loadMetadataFromPom("pom.xml");

    return ShrinkWrap.create(WebArchive.class, "job-announcement-test.war")
      .addAsLibraries(resolver.artifact("org.camunda.bpm:camunda-engine-cdi").resolveAsFiles())
      .addAsLibraries(resolver.artifact("org.camunda.bpm.javaee:camunda-ejb-client").resolveAsFiles())
      // add fluent assertions dependency
      .addAsLibraries(resolver.artifact("org.easytesting:fest-assert-core").resolveAsFiles())
      // add camunda BPM fluent assertions
      /*
       * Once the fluent testing is available via maven then we will just need to add a line here
       */
      .addPackage("org.camunda.bpm.engine.fluent")
      .addPackage("org.camunda.bpm.engine.test.fluent")
      .addPackage("org.camunda.bpm.engine.test.fluent.assertions")
      .addPackage("org.camunda.bpm.engine.test.fluent.mocking")
      .addPackage("org.camunda.bpm.engine.test.fluent.support")

      // needed because camunda BPM fluent test classes use them
      /*
       * TODO: this classes are packaged in org.camunda.bpm:camunda-engine:jar will this JAR be
       *       brought in as a transitive dependency?
       */
      .addClass(ProcessEngineTestCase.class)
      .addClass(ProcessEngineRule.class)

      // prepare as process application archive for fox platform
      .addAsWebResource("META-INF/processes.xml", "WEB-INF/classes/META-INF/processes.xml")
      .addAsResource("com/plexiti/activiti/showcase/jobannouncement/process/job-announcement.bpmn")
      .addAsResource("com/plexiti/activiti/showcase/jobannouncement/process/job-announcement-publication.bpmn")

      ;
  }

  @Inject
  private RuntimeService runtimeService;

  @Test
  public void testEndToEnd() throws InterruptedException {

    /*
     * FIXME: hack that needs to be removed
     */
    FluentLookups.before(this);
    long jobAnnouncementId = 1L;
    newProcessInstance(JOBANNOUNCEMENT_PROCESS)
      .withVariable("jobAnnouncementId", jobAnnouncementId)
      .start();
    System.out.println("Started process instance id " + jobAnnouncementId);

    assertThat(processInstance())
      .isStarted()
      .isWaitingAt(TASK_DESCRIBE_POSITION);
    assertThat(processTask())
      .hasCandidateGroup(ROLE_STAFF)
      .isUnassigned();

    /*
     * FIXME: hack that needs to be removed
     */
    FluentLookups.after(this);
  }
}
