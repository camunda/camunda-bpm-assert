package org.camunda.bpm.engine.test.assertions.examples;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.ProcessEngineTests;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.test.assertions.examples.jobannouncement.JobAnnouncement;
import org.camunda.bpm.engine.test.assertions.examples.jobannouncement.JobAnnouncementService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.mockito.Mockito.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAnnouncementProcessTest {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Mock
  public JobAnnouncementService jobAnnouncementService;
  @Mock
  public JobAnnouncement jobAnnouncement;

          // Some boilerplate - we can easily get rid of again by 
  @Before // deciding where to ultimately put the jUnit integration
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    Mocks.register("jobAnnouncementService", jobAnnouncementService);
    Mocks.register("jobAnnouncement", jobAnnouncement);
    ProcessEngineTests.init(processEngineRule.getProcessEngine());
  }

  @After
  public void tearDown() {
    Mocks.reset();
    ProcessEngineTests.reset();
  }
  
  @Test
  @Deployment(resources = {
    "camunda-testing-job-announcement.bpmn",
    "camunda-testing-job-announcement-publication.bpmn"
  })
  public void testHappyPath() {

    when(jobAnnouncement.getId()).thenReturn(1L);
    when(jobAnnouncementService.findRequester(1L)).thenReturn("gonzo");
    when(jobAnnouncementService.findEditor(1L)).thenReturn("fozzie");

    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "camunda-testing-job-announcement",
      withVariables("jobAnnouncementId", jobAnnouncement.getId())
    );

    assertThat(processInstance).isStarted().task().hasDefinitionKey("edit").hasCandidateGroup("engineering").isUnassigned();

    claim(task(), "fozzie");

    assertThat(task()).isAssignedTo("fozzie");
    // and just to show off more possibilities...
    assertThat(processInstance).task(taskQuery().taskAssignee("fozzie")).hasDefinitionKey("edit");

    complete(task());

    assertThat(processInstance).task().hasDefinitionKey("review").isAssignedTo("gonzo");

    complete(task(), withVariables("approved", true));

    assertThat(processInstance).task().hasDefinitionKey("publish").hasCandidateGroup("engineering").isUnassigned();

    // claim and complete could be combined, too
    complete(claim(task(), "fozzie"), withVariables("twitter", true, "facebook", true));

    verify(jobAnnouncementService).findRequester(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());
    verify(jobAnnouncementService).notifyAboutPostings(jobAnnouncement.getId());

    assertThat(processInstance).isFinished();

    verifyNoMoreInteractions(jobAnnouncementService);

  }

}
