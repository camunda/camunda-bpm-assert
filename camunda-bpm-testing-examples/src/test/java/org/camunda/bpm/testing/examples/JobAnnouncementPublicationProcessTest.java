package org.camunda.bpm.testing.examples;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.testing.examples.jobannouncement.JobAnnouncement;
import org.camunda.bpm.testing.examples.jobannouncement.JobAnnouncementService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.camunda.bpm.engine.test.fluent.ProcessEngineTests.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAnnouncementPublicationProcessTest {

  @Rule public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Mock public JobAnnouncementService jobAnnouncementService;
  @Mock public JobAnnouncement jobAnnouncement;

  // Some boilerplate - we can easily get rid of again by 
  @Before // deciding where to ultimately put the jUnit integration
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    Mocks.register("jobAnnouncementService", jobAnnouncementService);
    Mocks.register("jobAnnouncement", jobAnnouncement);
    init(processEngineRule.getProcessEngine());
  }

  @Test
  @Deployment(resources = { "camunda-testing-job-announcement-publication.bpmn" })
  public void testPublishOnlyOnCompanyWebsite() {

    when(jobAnnouncement.getId()).thenReturn(1L);

    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "camunda-testing-job-announcement-publication",
      withVariables(
        "jobAnnouncementId", jobAnnouncement.getId(),
        "twitter", false,
        "facebook", false
      )
    );

    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService, never()).postToTwitter(any(Long.class));
    verify(jobAnnouncementService, never()).postToFacebook(any(Long.class));

    assertThat(processInstance).isFinished();

    verifyNoMoreInteractions(jobAnnouncementService);

  }

  @Test
  @Deployment(resources = { "camunda-testing-job-announcement-publication.bpmn" })
  public void testPublishAlsoOnTwitter() {

    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "camunda-testing-job-announcement-publication",
      withVariables(
        "jobAnnouncementId", jobAnnouncement.getId(),
        "twitter", true,
        "facebook", false
      )
    );

    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
    verify(jobAnnouncementService, never()).postToFacebook(any(Long.class));

    assertThat(processInstance).isFinished();

    verifyNoMoreInteractions(jobAnnouncementService);

  }

  @Test
  @Deployment(resources = { "camunda-testing-job-announcement-publication.bpmn" })
  public void testPublishAlsoOnFacebook() {

    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "camunda-testing-job-announcement-publication",
      withVariables(
        "jobAnnouncementId", jobAnnouncement.getId(),
        "twitter", false,
        "facebook", true
      )
    );

    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService, never()).postToTwitter(any(Long.class));
    verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());

    assertThat(processInstance).isFinished();

    verifyNoMoreInteractions(jobAnnouncementService);

  }

  @Test
  @Deployment(resources = { "camunda-testing-job-announcement-publication.bpmn" })
  public void testPublishAlsoOnFacebookAndTwitter() {

    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "camunda-testing-job-announcement-publication",
      withVariables(
        "jobAnnouncementId", jobAnnouncement.getId(),
        "twitter", true,
        "facebook", true
      )
    );

    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());

    assertThat(processInstance).isFinished();

    verifyNoMoreInteractions(jobAnnouncementService);

  }

}
