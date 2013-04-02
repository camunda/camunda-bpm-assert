package com.plexiti.activiti.test.fluent;

import com.plexiti.activiti.showcase.jobannouncement.model.JobAnnouncement;
import com.plexiti.activiti.showcase.jobannouncement.service.JobAnnouncementService;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTestRule;

import static com.plexiti.activiti.showcase.jobannouncement.process.ProcessConstants.JOBANNOUNCEMENT_PUBLICATION_PROCESS;
import static com.plexiti.activiti.showcase.jobannouncement.process.ProcessConstants.JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public class JobAnnouncementPublicationTest {

    @Rule
    public ProcessEngineRule activitiRule = new ProcessEngineRule();
    @Rule
    public FluentProcessEngineTestRule bpmnFluentTestRule = new FluentProcessEngineTestRule(this);

	@Mock
    public JobAnnouncementService jobAnnouncementService;
	@Mock
    public JobAnnouncement jobAnnouncement;

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishOnlyOnCompanyWebsite() {
		
		when(jobAnnouncement.getId()).thenReturn(1L);

        newProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.setVariable("jobAnnouncementId", jobAnnouncement.getId())
			.setVariable("twitter", false)
			.setVariable("facebook", false)
		.start();
		
		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService, never()).postToTwitter(any(Long.class));
		verify(jobAnnouncementService, never()).postToFacebook(any(Long.class));

		assertThat(processInstance()).isFinished();

		verifyNoMoreInteractions(jobAnnouncementService);

	}

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishAlsoOnTwitter() {
		
		newProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.setVariable("jobAnnouncementId", jobAnnouncement.getId())
			.setVariable("twitter", true)
			.setVariable("facebook", false)
		.start();

		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
		verify(jobAnnouncementService, never()).postToFacebook(any(Long.class));

		assertThat(processInstance()).isFinished();

		verifyNoMoreInteractions(jobAnnouncementService);
		
	}

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishAlsoOnFacebook() {

        newProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.setVariable("jobAnnouncementId", jobAnnouncement.getId())
			.setVariable("twitter", false)
			.setVariable("facebook", true)
		.start();

		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService, never()).postToTwitter(any(Long.class));
		verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());

        assertThat(processInstance()).isFinished();

		verifyNoMoreInteractions(jobAnnouncementService);
		
	}

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishAlsoOnFacebookAndTwitter() {

        newProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.setVariable("jobAnnouncementId", jobAnnouncement.getId())
			.setVariable("facebook", true)
			.setVariable("twitter", true)
		.start();

		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());

		assertThat(processInstance()).isFinished();

		verifyNoMoreInteractions(jobAnnouncementService);
		
	}
}
