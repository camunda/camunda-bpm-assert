# Introduction

This library aims at easing testing when developing process applications based on [camunda BPM](http://camunda.org). We reach out to

* ease the readability and maintainability of process model tests
* make the writing of process model  tests more fluent and more fun
* make it easy to mock the services available to a process instance

In particular the library

* provides a [fluent](http://www.martinfowler.com/bliki/FluentInterface.html) API so you can focus on your process expert's domain knowledge while writing (and reading!) your tests

```java
...
assertThat(processInstance()).isWaitingAt("review");
assertThat(processTask()).isAssignedTo("piggie");
processTask().complete("approved", true);
...
```

* allows you to directly "jump" to a specific process activity by fast-forwarding along an execution path of another test scenario

```java
...
newProcessInstance("job-announcement", new Move() {
    public void along() {
        testHappyPath();
    }
}).withVariable("jobAnnouncementId", jobAnnouncement.getId())
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

This project is a spin-off of [The Job Announcement](https://github.com/plexiti/the-job-announcement-fox), a showcase
for a business process-centric application based on the [Java EE 6](http://www.oracle.com/technetwork/java/javaee/overview/index.html)
technology stack and the [camunda BPM Platform](http://camunda.org). An online version of The Job Announcement can be found at [http://the-job-announcement.com/](http://the-job-announcement.com/) and the source code on [GitHub](https://github.com/plexiti/the-job-announcement-fox).

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

This library support two unit testing approaches:

## Tests that use the `extends ProcessEngineTest` mechanism

NOTE: is this a legacy approach?

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

## Tests that use the `@Rule` approach

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

# Frequently Asked Questions (FAQs)

## Do you support Arquillian tests?

TODO

# Examples

## Example: Job Announcement Test
```java
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
            .withVariable("jobAnnouncementId", jobAnnouncement.getId())
            .start();

		assertThat(processInstance())
            .isStarted()
            .isWaitingAt(TASK_DESCRIBE_POSITION);
		assertThat(processTask())
            .hasCandidateGroup(ROLE_STAFF)
            .isUnassigned();

		processTask().claim(USER_STAFF);

		assertThat(processTask())
            .isAssignedTo(USER_STAFF);

		processTask().complete();

		assertThat(processInstance())
            .isWaitingAt(TASK_REVIEW_ANNOUNCEMENT);
		assertThat(processTask())
            .isAssignedTo(USER_MANAGER);

		processTask().complete("approved", true);

		assertThat(processInstance())
            .isWaitingAt(TASK_INITIATE_ANNOUNCEMENT);
		assertThat(processTask())
            .hasCandidateGroup(ROLE_STAFF)
            .isUnassigned();

		processTask().claim(USER_STAFF);

		assertThat(processTask())
            .isAssignedTo(USER_STAFF);

		processTask().complete("twitter", true, "facebook", true);

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
        }).withVariable("jobAnnouncementId", jobAnnouncement.getId())
        .startAndMoveTo(TASK_REVIEW_ANNOUNCEMENT);

        assertThat(processInstance())
            .isStarted()
            .isWaitingAt(TASK_REVIEW_ANNOUNCEMENT);

		processTask().complete("approved", false);

		assertThat(processInstance())
            .isWaitingAt(TASK_CORRECT_ANNOUNCEMENT);
		assertThat(processTask())
            .isAssignedTo(USER_STAFF);

        processTask().complete();
		processTask().complete("approved", true);

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
                            .withVariable("auctionId", auction.getId())
                            .start();
                    return auction.getId();
                }
            });

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                auction.setAuthorized(true);
                processTask().complete();
                return null;
            }
        }).when(auctionService).authorizeAuction(anyString(), anyBoolean());

        when(auctionService.locateHighestBidId(anyLong())).thenReturn(1L);

        // Execute the test

        auctionService.createAuction(auction);

        assertThat(processInstance()).isStarted().isWaitingAt("authorizeAuction");
        assertThat(processVariable("auctionId")).exists().isDefined().asLong().isEqualTo(1);

        auctionService.authorizeAuction(processTask().getId(), true);

        assertThat(processInstance()).isWaitingAt("IntermediateCatchEvent_1");

        processJob().execute();

        assertThat(processInstance()).isWaitingAt("UserTask_2");

        processTask().complete();

        assertThat(processInstance()).isFinished();

    }

}
```

# Using it in your own project

To use in your project you will need to add this dependency

```xml
<dependency>
    <groupId>com.plexiti.activiti</groupId>
    <artifactId>activiti-fluent-tests</artifactId>
    <version>0.4</version>
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

# Feedback

Suggestions, pull requests, ... you name it... are very welcome!
