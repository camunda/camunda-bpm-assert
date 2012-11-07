package com.plexiti.activiti.test.fluent;

import com.plexiti.activiti.showcase.jobannouncement.model.JobAnnouncement;
import com.plexiti.activiti.showcase.jobannouncement.service.JobAnnouncementService;
import com.plexiti.activiti.test.fluent.mocking.ActivitiMockitoTest;
import com.plexiti.helper.Entities;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.mockito.Mock;

import static com.plexiti.activiti.showcase.jobannouncement.process.ProcessConstants.*;
import static com.plexiti.activiti.test.fluent.ActivitiFestConditions.*;
import static org.mockito.Mockito.*;

import static org.fest.assertions.api.Assertions.*;

public class JobAnnouncementTest extends ActivitiMockitoTest {
	
	@Mock public JobAnnouncementService jobAnnouncementService;
	@Mock public JobAnnouncement jobAnnouncement;
	
	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PROCESS_RESOURCE, JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testHappyPath() {
		
		when(jobAnnouncement.getId()).thenReturn(1L);
		when(jobAnnouncementService.findRequester(1L)).thenReturn(USER_MANAGER);
		when(jobAnnouncementService.findEditor(1L)).thenReturn(USER_STAFF);
		
		start(new TestProcessInstance(JOBANNOUNCEMENT_PROCESS)
			.withVariable(Entities.idVariableName(JobAnnouncement.class), jobAnnouncement.getId())
		);
		
		assertThat(process().diagramLayout()).is(containingNode(TASK_DESCRIBE_POSITION));		
		assertThat(process().diagramLayout()).is(containingNode(TASK_REVIEW_ANNOUNCEMENT));
		assertThat(process().diagramLayout()).is(containingNode(TASK_CORRECT_ANNOUNCEMENT));
		assertThat(process().diagramLayout()).is(containingNode(TASK_INITIATE_ANNOUNCEMENT));

		assertThat(process().execution()).is(started());

		assertThat(process().execution()).is(atActivity(TASK_DESCRIBE_POSITION));
		assertThat(process().currentTask()).is(inCandidateGroup(ROLE_STAFF));
		assertThat(process().currentTask()).is(unassigned());

		process().claim(process().currentTask(), USER_STAFF);
		
		assertThat(process().currentTask()).isNot(unassigned());
		assertThat(process().currentTask()).is(assignedTo(USER_STAFF));

		process().complete(process().currentTask());

		assertThat(process().execution()).is(atActivity(TASK_REVIEW_ANNOUNCEMENT));
		assertThat(process().currentTask()).isNot(unassigned());
		assertThat(process().currentTask()).is(assignedTo(USER_MANAGER));

		process().complete(process().currentTask(), "approved", true);

		assertThat(process().execution()).is(atActivity(TASK_INITIATE_ANNOUNCEMENT));
		assertThat(process().currentTask()).is(inCandidateGroup(ROLE_STAFF));
		assertThat(process().currentTask()).is(unassigned());

		process().claim(process().currentTask(), USER_STAFF);
		
		assertThat(process().currentTask()).isNot(unassigned());
		assertThat(process().currentTask()).is(assignedTo(USER_STAFF));

		process().complete(process().currentTask(), "twitter", true, "facebook", true);
		
		verify(jobAnnouncementService).findRequester(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());
		verify(jobAnnouncementService).notifyAboutPostings(jobAnnouncement.getId());

		assertThat(process().execution()).is(finished());
		
		verifyNoMoreInteractions(jobAnnouncementService);
		
	}

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PROCESS_RESOURCE, JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testPositionDescriptionNeedsToBeCorrectedPath() {

		when(jobAnnouncement.getId()).thenReturn(1L);

		start(new TestProcessInstance(JOBANNOUNCEMENT_PROCESS) { 
				public void moveAlong() { 
					testHappyPath(); 
				}
			}
			.withVariable(Entities.idVariableName(JobAnnouncement.class), jobAnnouncement.getId())
		).moveTo(TASK_REVIEW_ANNOUNCEMENT);

		assertThat(process().execution()).is(atActivity(TASK_REVIEW_ANNOUNCEMENT));

		process().complete(process().currentTask(), "approved", false);

		assertThat(process().execution()).is(atActivity(TASK_CORRECT_ANNOUNCEMENT));
		assertThat(process().currentTask()).isNot(unassigned());
		assertThat(process().currentTask()).is(assignedTo(USER_STAFF));

		process().complete(process().currentTask());
		process().complete(process().currentTask(), "approved", true);

		assertThat(process().execution()).is(atActivity(TASK_INITIATE_ANNOUNCEMENT));

		verify(jobAnnouncementService, times(2)).findRequester(jobAnnouncement.getId());
		verify(jobAnnouncementService).findEditor(jobAnnouncement.getId());
		verifyNoMoreInteractions(jobAnnouncementService);
		
	}
	
}
