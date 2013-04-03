# Introduction

This library aims at easing testing when developing process applications based on [camunda BPM](http://camunda.org). We reach out to

* ease the readability and maintainability of process model tests
* make the writing of process model tests more fluent and more fun
* make it easy to mock the services available to a process instance

In particular the library

* provides a [fluent](http://www.martinfowler.com/bliki/FluentInterface.html) API so you can focus on your process expert's domain knowledge while writing (and reading!) your tests

```java
...
assertThat(processInstance()).isWaitingAt("review");
assertThat(processInstance().task()).isAssignedTo("piggie");
processInstance().task().complete("approved", true);
...
```

* allows you to directly "jump" to a specific process activity by fast-forwarding along an execution path of another test scenario

```java
...
newProcessInstance("job-announcement", new Move() {
    public void along() {
        testHappyPath();
    }
}).setVariable("jobAnnouncementId", jobAnnouncement.getId())
.startAndMoveTo("review");
...
``` 

* makes it very easy to mock services available to process instance and resolveable by UEL expressions used in its definition. Just use the Mockito @Mock Annotation and you are done.

```java
...
@Mock
public JobAnnouncementService jobAnnouncementService;
...
```

This project is a spin-off of [The Job Announcement](https://github.com/plexiti/the-job-announcement-fox), a showcase for a business process-centric application based on the [Java EE 6](http://www.oracle.com/technetwork/java/javaee/overview/index.html) technology stack and the [camunda BPM Platform](http://camunda.org). An online version of The Job Announcement can be found at [http://the-job-announcement.com/](http://the-job-announcement.com/) and the source code on [GitHub](https://github.com/plexiti/the-job-announcement-fox).

This project leverages two great testing libraries

* [Fixtures for Easy Software Testing](http://fest.easytesting.org/) and
* [Mockito](http://code.google.com/p/mockito/).

# How to use this library in your own project

## Greenfield Tests

```java
...
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static com.plexiti.activiti.showcase.jobannouncement.process.ProcessConstants.*;
import static com.plexiti.activiti.test.fluent.FluentProcessEngineTests.*;
import static org.mockito.Mockito.*;

public class JobAnnouncementTest {

    @Rule
    public ProcessEngineRule activitiRule = new ProcessEngineRule();

    @Rule
    public FluentProcessEngineTestRule bpmnFluentTestRule = new FluentProcessEngineTestRule(this);

    @Mock
    public JobAnnouncementService jobAnnouncementService;

	@Test
	@Deployment(resources = { JOBANNOUNCEMENT_PROCESS_RESOURCE })
	public void testHappyPath() {
	
	...
	
	}
```

## How to Refactor Your Existing Tests to Use This Library

This library supports three existing approaches to set up and execute your tests:

* the JUnit `extends ProcessEngineTest` mechanism
* the JUnit `@Rule` mechanism
* the @RunWith(Arquillian.class) to test within a container

## Tests that use the JUnit `extends ProcessEngineTest` mechanism

Example:

```java
...
public class TaskDueDateExtensionsTest extends ProcessEngineTestCase {

  @Deployment
  public void testDueDateExtension() throws Exception { ... }
...
```

replace `ProcessEngineTestCase` with `FluentProcessEngineTestCase`:

```java
...
public class TaskDueDateExtensionsTest extends FluentProcessEngineTestCase {

  @Deployment
  public void testDueDateExtension() throws Exception { ... }
...
```
NOTE: If you have a setUp() method in your test, make sure the very first thing this method does is `super.setUp()`!

## Tests that use the JUnit `@Rule` mechanism

```java
...
public class TaskDueDateExtensionsTest extends FluentProcessEngineTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment
  public void testMethod() throws Exception { ... }
...
```

do as follows:

```java
...
public class TaskDueDateExtensionsTest extends FluentProcessEngineTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Rule
  public FluentProcessEngineTestRule bpmnFluentTestRule = new FluentProcessEngineTestRule(this);

  @Test
  @Deployment
  public void testMethod() throws Exception { ... }
...
```

## Tests that use the Arquillian framework to test within a container

```java
...
@RunWith(Arquillian.class)
public class JobAnnouncementIT {

    @Deployment
    public static WebArchive createDeployment() {
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class)
            .goOffline()
            .loadMetadataFromPom("pom.xml");

        return ShrinkWrap.create(WebArchive.class, finalName)
            .addAsLibraries(resolver.artifact("org.camunda.bpm:camunda-engine-cdi").resolveAsFiles())
            .addAsLibraries(resolver.artifact("org.camunda.bpm.javaee:camunda-ejb-client").resolveAsFiles())
            .addAsWebResource("META-INF/processes.xml", "WEB-INF/classes/META-INF/processes.xml");
    }

    @Test
    public void testMethod() throws Exception { ... }
...
```

do as follows:

```java
...
@RunWith(Arquillian.class)
public class JobAnnouncementIT {

    @Deployment
    public static WebArchive createDeployment() {
        return prepareDeployment("job-announcement-test.war")
            .addAsResource("com/plexiti/activiti/showcase/jobannouncement/process/job-announcement.bpmn")
            .addAsResource("com/plexiti/activiti/showcase/jobannouncement/process/job-announcement-publication.bpmn");
    }

    @Rule
    public FluentProcessEngineTestRule bpmnFluentTestRule = new FluentProcessEngineTestRule(this);

    @Test
    public void testMethod() throws Exception { ... }
...
```

# Examples

## Example: Job Announcement Test
```java
...
public class JobAnnouncementTest {

    @Rule
    public ProcessEngineRule processEngineRule = new ProcessEngineRule();
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
```

## Example: Auction Process Test
```java
public class AuctionProcessTest extends FluentProcessEngineTestCase {

    @Mock
    public AuctionService auctionService;

    @Mock
    public TwitterPublishService twitterPublishService;

    @Test
    @Deployment(resources = { "com/camunda/showcase/auction/auction-process.bpmn" })
    public void testProcessDeployment() {
       assertThat(processDefinition("Auction Process")).isDeployed();
    }

    @Test
    @Deployment(resources = { "com/camunda/showcase/auction/auction-process.bpmn" })
    public void testWalkThroughProcess() throws Exception {

        // Set up test fixtures

        final Auction auction = new Auction();
        auction.setName("Cheap Ferrari!");
        auction.setDescription("Ferrari Testarossa on sale!");
        auction.setEndTime(new Date());

        Mocks.register("auction", auction);

        when(auctionService.createAuction((Auction) anyObject()))
            .thenAnswer(new Answer() {
                public Object answer(InvocationOnMock invocation) {
                    auction.setId(1L);
                    newProcessInstance("auction-process")
                            .setVariable("auctionId", auction.getId())
                            .start();
                    return auction.getId();
                }
            });

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                auction.setAuthorized(true);
                processInstance().task().complete();
                return null;
            }
        }).when(auctionService).authorizeAuction(anyString(), anyBoolean());

        when(auctionService.locateHighestBidId(anyLong())).thenReturn(1L);

        // Execute the test

        auctionService.createAuction(auction);

        assertThat(processInstance()).isStarted().isWaitingAt("authorizeAuction");
        assertThat(processInstance().getVariable("auctionId")).exists().isDefined().asLong().isEqualTo(1);

        auctionService.authorizeAuction(processInstance().task().getId(), true);

        assertThat(processInstance()).isWaitingAt("IntermediateCatchEvent_1");

        processInstance().job().execute();

        assertThat(processInstance()).isWaitingAt("UserTask_2");

        processInstance().task().complete();

        assertThat(processInstance()).isFinished();

    }

}
```

# Feedback

Suggestions, pull requests, ... you name it... are very welcome!
