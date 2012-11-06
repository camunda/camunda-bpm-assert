# Introduction

A small library to facilitate the creation of [fluent](http://www.martinfowler.com/bliki/FluentInterface.html) unit tests
for your [Activiti](http://activit.org) and [camunda fox](http://www.camunda.com/fox) BPMN processes. It's a project spin-off of
[The Job Announcement](https://bitbucket.org/plexiti/the-job-announcement-fox), a web application built in order to
showcase a business process-centric application based on the [Java EE 6](http://www.oracle.com/technetwork/java/javaee/overview/index.html)
technology stack and the [camunda fox BPM Platform](http://www.camunda.com/fox).

An online version of the web application can be found at [http://the-job-announcement.com/](http://the-job-announcement.com/).

This project leverages two great testing libraries: [Fixtures for Easy Software Testing](http://fest.easytesting.org/) and
[Mockito](http://code.google.com/p/mockito/).

You can write fluent and more readable process unit tests like this one:

    :::java
    ...
    import com.plexiti.activiti.test.fluent.mocking.ActivitiMockitoTest;

    import static com.plexiti.activiti.test.fluent.ActivitiFestConditions.*;
    import static org.mockito.Mockito.*;
    import static org.fest.assertions.Assertions.assertThat;

    public class JobAnnouncementTest extends ActivitiMockitoTest {

        @Mock public JobAnnouncementService jobAnnouncementService;
        @Mock public JobAnnouncement jobAnnouncement;

        @Test
        @Deployment(resources = { JOBANNOUNCEMENT_PROCESS_RESOURCE,
                                  JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE })
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
    }
Check the included unit tests for a full example!


# Using it in your own project

To use in your project you will need to add this dependency

    :::xml
    <dependency>
        <groupId>com.plexiti.activiti</groupId>
        <artifactId>activiti-fluent-tests</artifactId>
        <version>0.1</version>
        <scope>test</scope>
    </dependency>

and this repository to your pom.xml

    :::xml
    <repository>
        <id>plexiti-public-releases</id>
        <name>plexiti Public Releases Repository</name>
        <url>https://nexus.schimak.at/nexus/content/repositories/plexiti-public-releases/</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>

# Feedback and Future Work

Suggestions, pull request, ... you name it! are always welcome!
