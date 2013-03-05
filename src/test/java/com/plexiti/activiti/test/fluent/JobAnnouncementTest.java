package com.plexiti.activiti.test.fluent;

import com.plexiti.activiti.showcase.jobannouncement.model.JobAnnouncement;
import com.plexiti.activiti.showcase.jobannouncement.service.JobAnnouncementService;
import com.plexiti.activiti.test.fluent.engine.FluentBpmnProcessInstanceImpl;
import com.plexiti.helper.Entities;
import org.activiti.engine.test.Deployment;
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
public class JobAnnouncementTest extends FluentBpmnTestCase {
	
    /*
     * Mock your services and domain model classes
     */
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

        /*
         * Start the process with (optionally) process variables
         */
		start(new FluentBpmnProcessInstanceImpl(JOBANNOUNCEMENT_PROCESS)
             .withVariable(Entities.idVariableName(JobAnnouncement.class), jobAnnouncement.getId())
        );
		
		assertThat(process().diagramLayout()).isContainingNode(TASK_DESCRIBE_POSITION);
		assertThat(process().diagramLayout()).isContainingNode(TASK_REVIEW_ANNOUNCEMENT);
		assertThat(process().diagramLayout()).isContainingNode(TASK_CORRECT_ANNOUNCEMENT);
		assertThat(process().diagramLayout()).isContainingNode(TASK_INITIATE_ANNOUNCEMENT);

		assertThat(process().execution()).isStarted();

		assertThat(process().execution()).isWaitingAt(TASK_DESCRIBE_POSITION);
		assertThat(process().task()).hasCandidateGroup(ROLE_STAFF);
		assertThat(process().task()).isUnassigned();

		process().task().claim(USER_STAFF);
		
		assertThat(process().task()).isAssignedTo(USER_STAFF);

		process().task().complete();

		assertThat(process().execution()).isWaitingAt(TASK_REVIEW_ANNOUNCEMENT);
		assertThat(process().task()).isAssignedTo(USER_MANAGER);

		process().task().complete("approved", true);

		assertThat(process().execution()).isWaitingAt(TASK_INITIATE_ANNOUNCEMENT);
		assertThat(process().task()).hasCandidateGroup(ROLE_STAFF);
		assertThat(process().task()).isUnassigned();

		process().task().claim(USER_STAFF);
		
		assertThat(process().task()).isAssignedTo(USER_STAFF);

		process().task().complete("twitter", true, "facebook", true);

        /*
         * Verify expected behavior
         */
		verify(jobAnnouncementService).findRequester(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());
		verify(jobAnnouncementService).notifyAboutPostings(jobAnnouncement.getId());

		assertThat(process().execution()).isFinished();
		
		verifyNoMoreInteractions(jobAnnouncementService);

	}

	@Test
    @Deployment(resources = { JOBANNOUNCEMENT_PROCESS_RESOURCE, JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPositionDescriptionNeedsToBeCorrectedPath() {

		when(jobAnnouncement.getId()).thenReturn(1L);

		start(new FluentBpmnProcessInstanceImpl(JOBANNOUNCEMENT_PROCESS) {
		        public void moveAlong() {
			   		testHappyPath();
				}
			}.withVariable(Entities.idVariableName(JobAnnouncement.class), jobAnnouncement.getId())
		).moveTo(TASK_REVIEW_ANNOUNCEMENT);

        assertThat(process().execution()).isStarted();

		assertThat(process().execution()).isWaitingAt(TASK_REVIEW_ANNOUNCEMENT);

		process().task().complete("approved", false);

		assertThat(process().execution()).isWaitingAt(TASK_CORRECT_ANNOUNCEMENT);
		assertThat(process().task()).isAssignedTo(USER_STAFF);

        process().task().complete();
		process().task().complete("approved", true);

		assertThat(process().execution()).isWaitingAt(TASK_INITIATE_ANNOUNCEMENT);

		verify(jobAnnouncementService, times(2)).findRequester(jobAnnouncement.getId());
		verify(jobAnnouncementService).findEditor(jobAnnouncement.getId());
		verifyNoMoreInteractions(jobAnnouncementService);
	}
}
