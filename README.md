# Introduction

> ACHTUNG! The code in this project is in an EARLY DRAFT state. Future releases *will* brake your tests
> since the API will definitely change . We are following the ["Release Early, Release Often"](http://www.catb.org/~esr/writings/cathedral-bazaar/cathedral-bazaar/ar01s04.html) principle here in order
> to get feedback from the Activiti developer community.

A library aiming at improving the developer experience when developing process-centric applications based on [Activiti](http://activiti.org) and [camunda fox](http://www.camunda.com/fox) BPMN platforms.
This library provides:
* a Test Driven Development (TDD) approach by allowing you to mock your service implementation
* a [fluent](http://www.martinfowler.com/bliki/FluentInterface.html) API so you can focus on your tests
* a way to test alternate process execution paths by allowing you to re-use your "happy path" test code 

This project is a spin-off of [The Job Announcement](https://github.com/plexiti/the-job-announcement-fox), a showcase
for a business process-centric application based on the [Java EE 6](http://www.oracle.com/technetwork/java/javaee/overview/index.html)
technology stack and the [camunda fox BPM Platform](http://www.camunda.com/fox). An online version of The Job Announcement can be found at [http://the-job-announcement.com/](http://the-job-announcement.com/).

This project leverages two great testing libraries: [Fixtures for Easy Software Testing](http://fest.easytesting.org/) and
[Mockito](http://code.google.com/p/mockito/).

## Example: Job Announcement Test
```java
...
public class JobAnnouncementTest extends ActivitiFluentTest {
	
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
		start(new TestProcessInstance(JOBANNOUNCEMENT_PROCESS)
                .withVariable(Entities.idVariableName(JobAnnouncement.class), jobAnnouncement.getId())
        );
		
		assertThat(process().diagramLayout()).is(containingNode(TASK_DESCRIBE_POSITION));		
		assertThat(process().diagramLayout()).is(containingNode(TASK_REVIEW_ANNOUNCEMENT));
		assertThat(process().diagramLayout()).is(containingNode(TASK_CORRECT_ANNOUNCEMENT));
		assertThat(process().diagramLayout()).is(containingNode(TASK_INITIATE_ANNOUNCEMENT));

		assertThat(process().execution()).isStarted();

		assertThat(process().execution()).isWaitingAt(TASK_DESCRIBE_POSITION);
		assertThat(process().currentTask()).hasCandidateGroup(ROLE_STAFF);
		assertThat(process().currentTask()).isUnassigned();

		process().claim(process().currentTask(), USER_STAFF);
		
		assertThat(process().currentTask()).isAssignedTo(USER_STAFF);

		process().complete(process().currentTask());

		assertThat(process().execution()).isWaitingAt(TASK_REVIEW_ANNOUNCEMENT);
		assertThat(process().currentTask()).isAssignedTo(USER_MANAGER);

		process().complete(process().currentTask(), "approved", true);

		assertThat(process().execution()).isWaitingAt(TASK_INITIATE_ANNOUNCEMENT);
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

		assertThat(process().execution()).isFinished();
		
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

        assertThat(process().execution()).isStarted();

		assertThat(process().execution()).isWaitingAt(TASK_REVIEW_ANNOUNCEMENT);

		process().complete(process().currentTask(), "approved", false);

		assertThat(process().execution()).isWaitingAt(TASK_CORRECT_ANNOUNCEMENT);
		assertThat(process().currentTask()).isAssignedTo(USER_STAFF);

		process().complete(process().currentTask());
		process().complete(process().currentTask(), "approved", true);

		assertThat(process().execution()).isWaitingAt(TASK_INITIATE_ANNOUNCEMENT);

		verify(jobAnnouncementService, times(2)).findRequester(jobAnnouncement.getId());
		verify(jobAnnouncementService).findEditor(jobAnnouncement.getId());
		verifyNoMoreInteractions(jobAnnouncementService);
	}
}
```

## Example:  Auction Process Test
```java
...
public class AuctionProcessTest extends ActivitiFluentTest {

    @Mock
    public AuctionService auctionService;

    /*
     * Added since some of the process expressions use ${auction. ...}
     */
    @Mock(name = "auction")
    public Auction theAuction = new Auction("Auction name", "Auction description", new Date());

    @Test
    @Deployment(resources = { "com/camunda/showcase/auction/auction-process.bpmn" })
    public void testProcessDeployment() {

       assertThat(processDefinition("Auction Process")).isDeployed();
    }

    @Test
    @Deployment(resources = { "com/camunda/showcase/auction/auction-process.bpmn" })
    public void testWalkThroughProcess() throws Exception {

        /*
         * Set up the test fixtures and expectations
         */
        Auction auction = new Auction("Cheap Ferrari!", "Ferrari Testarossa on sale!", new Date());

        /*
         * Set up the expectations for the auctionService using Mockito
         */
        when(auctionService.createAuction(auction))
             .thenAnswer(new Answer() {
                 public Object answer(InvocationOnMock invocation) {
                     Auction auction = (Auction) invocation.getArguments()[0];

                     // Assign an id to the auction
                     auction.setId(new Long(1));

                     Map<String, Object> processVariables = new HashMap<String, Object>();
                     processVariables.put("auctionId", auction.getId());
                     // start the process
                     startProcessInstanceByKey("auction-process", processVariables);

                     return auction.getId();
                 }
             });

        /*
         * Start the test
         */
         auctionService.createAuction(auction);

         /*
          * The process should have been started from the service implementation
          */
         assertThat(process().execution()).isStarted();

         // The process variable "auctionId" must exist
         assertThat(process().variable("auctionId"))
                 .exists()
                 .isDefined()
                 // The ID of the auction should also have been set!
                 .asLong().isEqualTo(1);

         assertThat(process().execution()).isWaitingAt("authorizeAuction");
         Task authorizeAuctionTask = findTaskByTaskId("authorizeAuction");
         auctionService.authorizeAuction(authorizeAuctionTask.getId(), true);

         // end complete task 1 ///////////////

         // wait for auction end //////////////

         // wait for 6 seconds
         Thread.sleep(6000);

         // end wait for auction end //////////

         // complete task 2 ///////////////////
         ProcessInstance pi = process().getActualProcessInstance();
         List<Task> tasksAfterTimer = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
         Assert.assertEquals(1,tasksAfterTimer.size());

         Task billingAndShippingTask = tasksAfterTimer.get(0);

         // complete task
         taskService.complete(billingAndShippingTask.getId());

         // end complete task 2 ///////////////

         // check if process instance is really ended

         long runningInstancesCount = runtimeService
                 .createProcessInstanceQuery()
                 .processInstanceId(pi.getId())
                 .count();

         Assert.assertEquals(0,runningInstancesCount);
     }
}
```

# Using it in your own project

To use in your project you will need to add this dependency

```xml
<dependency>
    <groupId>com.plexiti.activiti</groupId>
    <artifactId>activiti-fluent-tests</artifactId>
    <version>0.1</version>
    <scope>test</scope>
</dependency>
```
and this repository to your pom.xml

```xml
<repository>
    <id>plexiti-public-releases</id>
    <name>plexiti Public Releases Repository</name>
    <url>http://repository.plexiti.com/content/repositories/releases/</url>
    <releases>
        <enabled>true</enabled>
    </releases>
    <snapshots>
        <enabled>false</enabled>
    </snapshots>
</repository>
```

# Feedback and Future Work

Suggestions, pull requests, ... you name it... are very welcome!
