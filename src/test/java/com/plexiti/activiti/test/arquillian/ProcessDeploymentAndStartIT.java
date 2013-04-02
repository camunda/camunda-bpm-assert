package com.plexiti.activiti.test.arquillian;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;

@RunWith(Arquillian.class)
public class ProcessDeploymentAndStartIT {

  @Deployment
  public static WebArchive createDeployment() {
      return prepareDeployment("process-deployment-and-start-test.war")
          .addAsResource("com/plexiti/activiti/showcase/jobannouncement/process/job-announcement.bpmn")
          .addAsResource("com/plexiti/activiti/showcase/jobannouncement/process/job-announcement-publication.bpmn")
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
