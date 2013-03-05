package com.plexiti.activiti.test.fluent;

import com.plexiti.activiti.showcase.jobannouncement.model.JobAnnouncement;
import com.plexiti.activiti.showcase.jobannouncement.service.JobAnnouncementService;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static com.plexiti.activiti.showcase.jobannouncement.process.ProcessConstants.*;
import static org.mockito.Mockito.*;

import static com.plexiti.activiti.test.fluent.FluentBpmnTests.*;

/**
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public class JobAnnouncementTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();
    @Rule
    public FluentBpmnTestRule bpmnFluentTestRule = new FluentBpmnTestRule(this);

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
            .withVariable("jobAnnouncementId", jobAnnouncement.getId())
            .start();

		assertThat(processDiagramLayout()).isContainingNode(TASK_DESCRIBE_POSITION);
		assertThat(processDiagramLayout()).isContainingNode(TASK_REVIEW_ANNOUNCEMENT);
		assertThat(processDiagramLayout()).isContainingNode(TASK_CORRECT_ANNOUNCEMENT);
		assertThat(processDiagramLayout()).isContainingNode(TASK_INITIATE_ANNOUNCEMENT);

		assertThat(processExecution()).isStarted();

		assertThat(processExecution()).isWaitingAt(TASK_DESCRIBE_POSITION);
		assertThat(processTask()).hasCandidateGroup(ROLE_STAFF);
		assertThat(processTask()).isUnassigned();

		processTask().claim(USER_STAFF);
		
		assertThat(processTask()).isAssignedTo(USER_STAFF);

		processTask().complete();

		assertThat(processExecution()).isWaitingAt(TASK_REVIEW_ANNOUNCEMENT);
		assertThat(processTask()).isAssignedTo(USER_MANAGER);

		processTask().complete("approved", true);

		assertThat(processExecution()).isWaitingAt(TASK_INITIATE_ANNOUNCEMENT);
		assertThat(processTask()).hasCandidateGroup(ROLE_STAFF);
		assertThat(processTask()).isUnassigned();

		processTask().claim(USER_STAFF);
		
		assertThat(processTask()).isAssignedTo(USER_STAFF);

		processTask().complete("twitter", true, "facebook", true);

        /*
         * Verify expected behavior
         */
		verify(jobAnnouncementService).findRequester(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());
		verify(jobAnnouncementService).notifyAboutPostings(jobAnnouncement.getId());

		assertThat(processExecution()).isFinished();
		
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
        }).withVariable("jobAnnouncementId", jobAnnouncement.getId())
        .startAndMoveTo(TASK_REVIEW_ANNOUNCEMENT);

        assertThat(processExecution()).isStarted();

		assertThat(processExecution()).isWaitingAt(TASK_REVIEW_ANNOUNCEMENT);

		processTask().complete("approved", false);

		assertThat(processExecution()).isWaitingAt(TASK_CORRECT_ANNOUNCEMENT);
		assertThat(processTask()).isAssignedTo(USER_STAFF);

        processTask().complete();
		processTask().complete("approved", true);

		assertThat(processExecution()).isWaitingAt(TASK_INITIATE_ANNOUNCEMENT);

		verify(jobAnnouncementService, times(2)).findRequester(jobAnnouncement.getId());
		verify(jobAnnouncementService).findEditor(jobAnnouncement.getId());
		verifyNoMoreInteractions(jobAnnouncementService);
	}
}
