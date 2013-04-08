package com.plexiti.activiti.test.fluent;

import com.plexiti.activiti.showcase.jobannouncement.model.JobAnnouncement;
import com.plexiti.activiti.showcase.jobannouncement.service.JobAnnouncementService;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTestRule;

import static com.plexiti.activiti.showcase.jobannouncement.process.ProcessConstants.*;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public class JobAnnouncementTest {

    @Rule
    public ProcessEngineRule activitiRule = new ProcessEngineRule();
    @Rule
    public FluentProcessEngineTestRule bpmnFluentTestRule = new FluentProcessEngineTestRule(this);

    @Mock
    public JobAnnouncementService jobAnnouncementService;
	@Mock
    public JobAnnouncement jobAnnouncement;

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PROCESS_RESOURCE, JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testHappyPath() {

        /*
         * Stub service and domain model methods
         */
		when(jobAnnouncement.getId()).thenReturn(1L);
		when(jobAnnouncementService.findRequester(1L)).thenReturn(USER_MANAGER);
		when(jobAnnouncementService.findEditor(1L)).thenReturn(USER_STAFF);

        newProcessInstance(JOBANNOUNCEMENT_PROCESS)
            .setVariable("jobAnnouncementId", jobAnnouncement.getId())
            .start();

		assertThat(processInstance())
            .isStarted()
            .isWaitingAt(TASK_DESCRIBE_POSITION);
		assertThat(processInstance().task())
            .hasCandidateGroup(ROLE_STAFF)
            .isUnassigned();

        processInstance().task().claim(USER_STAFF);
		
		assertThat(processInstance().task())
            .isAssignedTo(USER_STAFF);

        processInstance().task().complete();

		assertThat(processInstance())
            .isWaitingAt(TASK_REVIEW_ANNOUNCEMENT);
		assertThat(processInstance().task())
            .isAssignedTo(USER_MANAGER);

        processInstance().task().complete("approved", true);

		assertThat(processInstance())
            .isWaitingAt(TASK_INITIATE_ANNOUNCEMENT);
		assertThat(processInstance().task())
            .hasCandidateGroup(ROLE_STAFF)
            .isUnassigned();

        processInstance().task().claim(USER_STAFF);
		
		assertThat(processInstance().task())
            .isAssignedTo(USER_STAFF);

        processInstance().task().complete("twitter", true, "facebook", true);

        /*
         * Verify expected behavior
         */
		verify(jobAnnouncementService).findRequester(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());
		verify(jobAnnouncementService).notifyAboutPostings(jobAnnouncement.getId());

		assertThat(processInstance())
            .isFinished();
		
		verifyNoMoreInteractions(jobAnnouncementService);

	}

	@Test
    @Deployment(resources = { JOBANNOUNCEMENT_PROCESS_RESOURCE, JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPositionDescriptionNeedsToBeCorrectedPath() {

		when(jobAnnouncement.getId()).thenReturn(1L);

        newProcessInstance(JOBANNOUNCEMENT_PROCESS, new Move() {
            public void along() {
                testHappyPath();
            }
        }).setVariable("jobAnnouncementId", jobAnnouncement.getId())
        .startAndMoveTo(TASK_REVIEW_ANNOUNCEMENT);

        assertThat(processInstance())
            .isStarted()
            .isWaitingAt(TASK_REVIEW_ANNOUNCEMENT);

        processInstance().task().complete("approved", false);

		assertThat(processInstance())
            .isWaitingAt(TASK_CORRECT_ANNOUNCEMENT);
		assertThat(processInstance().task())
            .isAssignedTo(USER_STAFF);

        processInstance().task().complete();
        processInstance().task().complete("approved", true);

		assertThat(processInstance())
            .isWaitingAt(TASK_INITIATE_ANNOUNCEMENT);

		verify(jobAnnouncementService, times(2)).findRequester(jobAnnouncement.getId());
		verify(jobAnnouncementService).findEditor(jobAnnouncement.getId());
		verifyNoMoreInteractions(jobAnnouncementService);
	}
}
