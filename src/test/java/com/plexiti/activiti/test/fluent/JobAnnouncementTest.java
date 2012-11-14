package com.plexiti.activiti.test.fluent;

import com.plexiti.activiti.showcase.jobannouncement.model.JobAnnouncement;
import com.plexiti.activiti.showcase.jobannouncement.service.JobAnnouncementService;
import com.plexiti.helper.Entities;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static com.plexiti.activiti.showcase.jobannouncement.process.ProcessConstants.*;
import static com.plexiti.activiti.test.fluent.ActivitiFestConditions.*;
import static org.mockito.Mockito.*;

import static org.fest.assertions.api.Assertions.*;
import static com.plexiti.activiti.test.fluent.assertions.Assertions.*;

public class JobAnnouncementTest extends ActivitiFluentTest {
	
	@Mock
    public JobAnnouncementService jobAnnouncementService;
	@Mock
    public JobAnnouncement jobAnnouncement;

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PROCESS_RESOURCE, JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
	public void testHappyPath() {

        /*
         * Stub your services and domain model classes
         */
		when(jobAnnouncement.getId()).thenReturn(1L);
		when(jobAnnouncementService.findRequester(1L)).thenReturn(USER_MANAGER);
		when(jobAnnouncementService.findEditor(1L)).thenReturn(USER_STAFF);

        /*
         * Start the process with (optionally) process variables
         */
		start(new TestProcessInstance(JOBANNOUNCEMENT_PROCESS)
                .withVariable(Entities.idVariableName(JobAnnouncement.class), jobAnnouncement.getId())
        );
		
		assertThat(process().diagramLayout()).is(containingNode(TASK_DESCRIBE_POSITION));		
		assertThat(process().diagramLayout()).is(containingNode(TASK_REVIEW_ANNOUNCEMENT));
		assertThat(process().diagramLayout()).is(containingNode(TASK_CORRECT_ANNOUNCEMENT));
		assertThat(process().diagramLayout()).is(containingNode(TASK_INITIATE_ANNOUNCEMENT));

		assertThat(process().execution()).isStarted();

		assertThat(process().execution()).isAtActivity(TASK_DESCRIBE_POSITION);
		assertThat(process().currentTask()).hasCandidateGroup(ROLE_STAFF);
		assertThat(process().currentTask()).isUnassigned();

		process().claim(process().currentTask(), USER_STAFF);
		
		assertThat(process().currentTask()).isAssignedTo(USER_STAFF);

		process().complete(process().currentTask());

		assertThat(process().execution()).isAtActivity(TASK_REVIEW_ANNOUNCEMENT);
		assertThat(process().currentTask()).isAssignedTo(USER_MANAGER);

		process().complete(process().currentTask(), "approved", true);

		assertThat(process().execution()).isAtActivity(TASK_INITIATE_ANNOUNCEMENT);
		assertThat(process().currentTask()).hasCandidateGroup(ROLE_STAFF);
		assertThat(process().currentTask()).isUnassigned();

		process().claim(process().currentTask(), USER_STAFF);
		
		assertThat(process().currentTask()).isAssignedTo(USER_STAFF);

		process().complete(process().currentTask(), "twitter", true, "facebook", true);

        /*
         * Verify expected behavior
         */
		verify(jobAnnouncementService).findRequester(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
		verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());
		verify(jobAnnouncementService).notifyAboutPostings(jobAnnouncement.getId());

		// FIXME: assertThat(process().execution()).isFinished();
		
		verifyNoMoreInteractions(jobAnnouncementService);

        /*
         * You can also use assertions on the process execution history
         */
        List<HistoricActivityInstance> activityInstances = historicDataService.createHistoricActivityInstanceQuery().list();
        assertThat(activityInstances).hasSize(19);
        assertThat(extractProperty("activityName", String.class).from(activityInstances))
                .startsWith("Freie Stelle gemeldet", "Stelle beschreiben", "Stellenbeschreibung sichten", "OK?");

	}

    @Ignore
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

        assertThat(process().execution()).isStarted();

		assertThat(process().execution()).isAtActivity(TASK_REVIEW_ANNOUNCEMENT);

		process().complete(process().currentTask(), "approved", false);

		assertThat(process().execution()).isAtActivity(TASK_CORRECT_ANNOUNCEMENT);
		assertThat(process().currentTask()).isAssignedTo(USER_STAFF);

		process().complete(process().currentTask());
		process().complete(process().currentTask(), "approved", true);

		assertThat(process().execution()).isAtActivity(TASK_INITIATE_ANNOUNCEMENT);

		verify(jobAnnouncementService, times(2)).findRequester(jobAnnouncement.getId());
		verify(jobAnnouncementService).findEditor(jobAnnouncement.getId());
		verifyNoMoreInteractions(jobAnnouncementService);
	}
}
