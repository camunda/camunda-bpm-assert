package com.plexiti.activiti.test.fluent;

import com.plexiti.activiti.showcase.jobannouncement.model.JobAnnouncement;
import com.plexiti.activiti.showcase.jobannouncement.service.JobAnnouncementService;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static com.plexiti.activiti.showcase.jobannouncement.process.ProcessConstants.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import static com.plexiti.activiti.test.fluent.FluentBpmnTests.*;

/**
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public class JobAnnouncementPublicationTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();
    @Rule
    public FluentBpmnTestRule bpmnFluentTestRule = new FluentBpmnTestRule(this);

	@Mock
    public JobAnnouncementService jobAnnouncementService;
	@Mock
    public JobAnnouncement jobAnnouncement;

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishOnlyOnCompanyWebsite() {
		
		when(jobAnnouncement.getId()).thenReturn(1L);

        newProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.withVariable("jobAnnouncementId", jobAnnouncement.getId())
			.withVariable("twitter", false)
			.withVariable("facebook", false)
		.start();
		
		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService, never()).postToTwitter(any(Long.class));
		verify(jobAnnouncementService, never()).postToFacebook(any(Long.class));

		assertThat(processExecution()).isFinished();

		verifyNoMoreInteractions(jobAnnouncementService);

	}

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishAlsoOnTwitter() {
		
		newProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.withVariable("jobAnnouncementId", jobAnnouncement.getId())
			.withVariable("twitter", true)
			.withVariable("facebook", false)
		.start();

		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
		verify(jobAnnouncementService, never()).postToFacebook(any(Long.class));

		assertThat(processExecution()).isFinished();

		verifyNoMoreInteractions(jobAnnouncementService);
		
	}

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishAlsoOnFacebook() {

        newProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.withVariable("jobAnnouncementId", jobAnnouncement.getId())
			.withVariable("twitter", false)
			.withVariable("facebook", true)
		.start();

		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService, never()).postToTwitter(any(Long.class));
		verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());

        assertThat(processExecution()).isFinished();

		verifyNoMoreInteractions(jobAnnouncementService);
		
	}

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishAlsoOnFacebookAndTwitter() {

        newProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.withVariable("jobAnnouncementId", jobAnnouncement.getId())
			.withVariable("facebook", true)
			.withVariable("twitter", true)
		.start();

		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());

		assertThat(processExecution()).isFinished();

		verifyNoMoreInteractions(jobAnnouncementService);
		
	}
}
