**NOTE:** This project is part of the [camunda BPM incubation space](https://github.com/camunda/camunda-bpm-incubation). You can check the presentation of this project [camunda BPM dev list](https://groups.google.com/forum/#!msg/camunda-bpm-dev/m8VDRnZe55A/YsZ2QwnFOPcJ). Questions, issues, ideas, feedback, … are greatly appreciated and should be made the [camunda BPM dev list](https://groups.google.com/forum/?fromgroups#!forum/camunda-bpm-dev) list.

# Introduction

This library aims at improving test creation **and maintenance** when developing process applications based on [the camunda BPM platform](http://camunda.org). Specifically, it focuses on the following aspects:

* ease the readability and maintainability of process model tests
* make the creation of process model tests more fluent and, thus more fun!
* make it easy to mock the services available to a process instance

In particular the library

* provides a [fluent](http://www.martinfowler.com/bliki/FluentInterface.html) API so you can focus on your process expert's domain knowledge while writing (and reading!) your tests:

```java
...
assertThat(processInstance()).isWaitingAt("review");
assertThat(processInstance().task()).isAssignedTo("piggie");
processInstance().task().claim(USER_STAFF);
processInstance().task().complete("approved", true);
...
```

* makes it very easy to mock services available to process instance and resolveable by UEL expressions used in its definition. Just use the [Mockito](http://code.google.com/p/mockito/) `@Mock` annotation and use the Mockito fluent API to define and verify your expectations:

```java
...
@Mock
public JobAnnouncementService jobAnnouncementService;
@Mock
public JobAnnouncement jobAnnouncement;
...
when(jobAnnouncementService.findRequester(1L)).thenReturn(USER_MANAGER);
when(jobAnnouncementService.findEditor(1L)).thenReturn(USER_STAFF);
...
verify(jobAnnouncementService, times(2)).findRequester(jobAnnouncement.getId());
verify(jobAnnouncementService).findEditor(jobAnnouncement.getId());
verifyNoMoreInteractions(jobAnnouncementService);
```

* allows you to directly "jump" to a specific process activity by fast-forwarding along an execution path of another test scenario:

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

This project is a spin-off of [The Job Announcement](https://github.com/plexiti/the-job-announcement), a showcase for a business process-centric application based on the [Java EE 6](http://www.oracle.com/technetwork/java/javaee/overview/index.html) technology stack and the [camunda BPM Platform](http://camunda.org). An online version of The Job Announcement can be found at [http://the-job-announcement.com/](http://the-job-announcement.com/) and the source code on [GitHub](https://github.com/plexiti/the-job-announcement).

This project leverages two great testing libraries

* [Fixtures for Easy Software Testing](http://fest.easytesting.org/) and
* [Mockito](http://code.google.com/p/mockito/).

# How to use this library in your own project

## Changes to your project's pom.xml

Declare the camunda BPM repository and make sure you also add the `<updatePolicy>` element so maven always downloads the latest SNAPSHOT: 

```xml
<repositories>
	<repository>
		<id>camunda-bpm-nexus</id>
		<name>camunda-bpm-nexus</name>
		<url>https://app.camunda.com/nexus/content/groups/public</url>
		<snapshots>
			<updatePolicy>always</updatePolicy>
		</snapshots>
	</repository>
</repositories>
...
```

Add the following test dependencies:

```xml
<dependency>
    <groupId>org.camunda.bpm.incubation</groupId>
    <artifactId>camunda-bpm-fluent-assertions</artifactId>
    <version>0.4-SNAPSHOT</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.camunda.bpm.incubation</groupId>
    <artifactId>camunda-bpm-fluent-engine-api</artifactId>
    <version>0.4-SNAPSHOT</version>
    <scope>test</scope>
</dependency>
```

**IMPORTANT**: since Maven uses [the "nearest" dependency resolution strategy by default](http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html), you should make sure that **if you declare JUnit in your project's POM, you should use at least `junit:junit:4.9`**, i.e.:

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.9</version>
    <scope>test</scope>
</dependency>
```

The reason is that this library leverages the [org.junit.rules.TestRule](https://github.com/junit-team/junit/blob/master/src/main/java/org/junit/rules/TestRule.java) interface introduced in JUnit 4.9.

## Greenfield Tests

If you are creating a new test from scratch then this is the recommended approach:

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

For the full test check [here](https://github.com/plexiti/the-job-announcement/blob/master/src/test/java/com/plexiti/camunda/bpm/showcase/jobannouncement/process/JobAnnouncementTest.java).

## How to Refactor Your Existing Tests to Use This Library

This library supports three existing approaches to set up and execute your tests:

* the JUnit `extends ProcessEngineTest` mechanism
* the JUnit `@Rule` mechanism
* the `@RunWith(Arquillian.class)` to test within a container

### Tests that use the JUnit `extends ProcessEngineTest` mechanism

1. Add static import for `FluentProcessEngineTests.*`;
1. Replace class your test class inherits from with `FluentProcessEngineTestCase`

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

**IMPORTANT**: If you have a `setUp()` method in your test, make sure the very first thing this method does is `super.setUp()`!

For the full test check [here](https://github.com/camunda/camunda-bpm-fluent-testing/blob/master/camunda-bpm-fluent-integration-tests/src/test/java/org/camunda/bpm/engine/test/bpmn/usertask/TaskDueDateExtensionsTest.java).

### Tests that use the JUnit `@Rule` mechanism

1. Add static import for `FluentProcessEngineTests.*`;
1. Add a `FluentProcessEngineTestRule` to your test class

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

### Tests that use the Arquillian framework to test within a container

1. Add static import for `FluentProcessEngineTests.*`;
1. Add a `FluentProcessEngineTestRule` to your test class
1. **Optionally** consider to make use of convenience method `FluentProcessEngineTests.prepareDeployment()` 

```java
...
@RunWith(Arquillian.class)
public class ProcessDeploymentAndStartIT {

    @Deployment
    public static WebArchive createDeployment() {
		...
    }

    @Test
    public void testDeploymentAndStartInstance() throws Exception { 
    	...
    }
...
```

do as follows:

```java
...
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;
...
@RunWith(Arquillian.class)
public class ProcessDeploymentAndStartIT {

    @Deployment
    public static WebArchive createDeployment() {
    	...
        // add camunda BPM fluent testing dependency
        .addAsLibraries(resolver.artifact("org.camunda.bpm.incubation:camunda-bpm-fluent-engine-api").resolveAsFiles())
        .addAsLibraries(resolver.artifact("org.camunda.bpm.incubation:camunda-bpm-fluent-assertions").resolveAsFiles())
		...
    }

    @Rule
    public FluentProcessEngineTestRule bpmnFluentTestRule = new FluentProcessEngineTestRule(this);

    @Test
    public void testDeploymentAndStartInstance() throws InterruptedException {
      newProcessInstance(JOBANNOUNCEMENT_PROCESS).start();
      assertThat(processInstance()).isStarted();
      System.out.println("Started process instance with id " + processInstance().getId());

      assertThat(processInstance()).isWaitingAt(TASK_DESCRIBE_POSITION);
    }
...
```

For the full test check [here](https://github.com/plexiti/the-job-announcement/blob/master/src/test/java/com/plexiti/camunda/bpm/showcase/jobannouncement/process/ProcessDeploymentAndStartIT.java).


# Projects using this library

The following opens source projects are using the camunda BPM fluent testing library:

* **The Job Announcement**: [https://github.com/plexiti/the-job-announcement](https://github.com/plexiti/the-job-announcement) 

# Feedback

Suggestions, pull requests, ... you name it... are very welcome! Meet us on the [camunda BPM dev list](https://groups.google.com/forum/?fromgroups#!forum/camunda-bpm-dev) list.
