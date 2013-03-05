package com.plexiti.activiti.test.fluent;

import com.plexiti.activiti.showcase.jobannouncement.model.JobAnnouncement;
import com.plexiti.activiti.showcase.jobannouncement.service.JobAnnouncementService;
import com.plexiti.helper.Entities;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.mockito.Mock;

import static com.plexiti.activiti.showcase.jobannouncement.process.ProcessConstants.*;
import static com.plexiti.activiti.test.fluent.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public class JobAnnouncementPublicationTest extends ActivitiFluentTestCase {
	
	@Mock
    public JobAnnouncementService jobAnnouncementService;
	@Mock
    public JobAnnouncement jobAnnouncement;

	String positionDescription = "- 10+ years Java development experience\n- Good knowledge of BPMN 2.0";

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishOnlyOnCompanyWebsite() {
		
		when(jobAnnouncement.getId()).thenReturn(1L);
		
		start(new TestProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.withVariable(Entities.idVariableName(JobAnnouncement.class), jobAnnouncement.getId())
			.withVariable("twitter", false)
			.withVariable("facebook", false)
		);
		
		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService, never()).postToTwitter(any(Long.class));
		verify(jobAnnouncementService, never()).postToFacebook(any(Long.class));

		assertThat(process().execution()).isFinished();

		verifyNoMoreInteractions(jobAnnouncementService);

	}

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishAlsoOnTwitter() {
		
		start(new TestProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.withVariable(Entities.idVariableName(JobAnnouncement.class), jobAnnouncement.getId())
			.withVariable("twitter", true)
			.withVariable("facebook", false)
		);

		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
		verify(jobAnnouncementService, never()).postToFacebook(any(Long.class));

		assertThat(process().execution()).isFinished();

		verifyNoMoreInteractions(jobAnnouncementService);
		
	}

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishAlsoOnFacebook() {
		
		start(new TestProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.withVariable(Entities.idVariableName(JobAnnouncement.class), jobAnnouncement.getId())
			.withVariable("twitter", false)
			.withVariable("facebook", true)
		);

		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService, never()).postToTwitter(any(Long.class));
		verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());

        assertThat(process().execution()).isFinished();

		verifyNoMoreInteractions(jobAnnouncementService);
		
	}

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPublishAlsoOnFacebookAndTwitter() {
		
		start(new TestProcessInstance(JOBANNOUNCEMENT_PUBLICATION_PROCESS)
			.withVariable(Entities.idVariableName(JobAnnouncement.class), jobAnnouncement.getId())
			.withVariable("facebook", true)
			.withVariable("twitter", true)
		);

		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());

		assertThat(process().execution()).isFinished();

		verifyNoMoreInteractions(jobAnnouncementService);
		
	}
}
