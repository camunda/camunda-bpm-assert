# ![camunda logo](./camunda-bpm-assert/src/main/resources/images/camunda.png)&nbsp;camunda-bpm-assert 

This little community-extension to [camunda BPM](http://camunda.org) wants to make it easier to write tests for BPMN process definitions executed with the camunda process engine. 
For that reason, a set of **assertions** based on Joel Costigliola's [AssertJ](https://github.com/joel-costigliola/assertj-core) rich assertions library for java are provided, as well as a few little helpers to make it a bit easier to drive you through your process.
 
Hopefully this will also make it easier to change your tests half a year after you have written them yourself!-) In short: more **readable code** - less **spaghetti tests**!

![Spaghetti Test](./camunda-bpm-assert/src/main/resources/images/spaghetti-test.jpg)

### Table of Contents

 * [Getting started](#getting-started)
   1. [Maven dependency](#maven-dependency)
   1. [Static import & Initialisation](#static-import)
   1. [Start using it!](#start-using)
 * [Resources](#resources), [Maintainer](#maintainer) and [License](#license)
 * Also check out the [User Guide](./camunda-bpm-assert/README.md) for a more detailed documentation.

<a name="getting-started"/>
## Getting started

**BEWARE: The project is currently (*January 2014*) undergoing a major rework. Therefore it will last a few more days until a version *0.7* will be published. Until then, please feel free to checkout below mentioned 0.7-SNAPSHOT.** Previous versions found in the download section do not fully comply to the information given in this guide.

<a name="maven-dependency"/>
### Maven dependency

First, [declare the camunda BPM maven repository](http://www.camunda.org/get-started/#apache-maven) - if you haven't yet done so. Then you find here the coordinates for including camunda-bpm-assert in Apache Maven Projects:

```xml  
	<dependency>
	    <groupId>org.camunda.bpm.incubation</groupId>
    	<artifactId>camunda-bpm-assert</artifactId>
    	<version>0.7-SNAPSHOT</version>
    	<scope>test</scope>
	</dependency>
```

<a name="static-import"/>
### Add a static import to your test class

Then, create your test case just as described in [camunda BPM Testing](http://docs.camunda.org/latest/guides/user-guide/#testing). Two things you have to add then 

 * the following static import:

```xml  
	import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
```
  
 * the initialisation code to make the assertions aware of your process engine, e.g. via the junit rule:
 
```java  
   @Before
   public void setUp() {
     init(processEngineRule.getProcessEngine());
   }
```
	
<a name="start-using"/>
### Start using camunda-bpm-assert!

Now you have access to all the assertions provided by the fest library - plus the customised camunda BPM assertions provided by this library. Let's assume you want to assert that your process instance 

 * was actually started 
 * is waiting at a specific user task and
 * the task is yet unassigned, but waiting to be assigned to a user of a specific group:
 
![Green Bar](./camunda-bpm-assert/src/main/resources/images/job-announcement.png)

Then write:

```java	
	assertThat(processInstance).isStarted()
		.task().hasDefinitionKey("edit")
			.hasCandidateGroup("SB-Personal")
			.isUnassigned();
```

Green bar? 

![Green Bar](./camunda-bpm-assert/src/main/resources/images/green-bar.png)

Congrats! You are successfully using camunda-bpm-assert. Find a more detailed description of the assertions and helper methods available in the [**camunda-bpm-assert User Guide**](./camunda-bpm-assert/README.md).

<a name="resources"/>
## Resources

* [User Guide](./camunda-bpm-assert/README.md)
* [API Docs](http://camunda.github.io/camunda-bpm-assert/apidocs/) 
* [Issue Tracker](https://github.com/camunda/camunda-bpm-assert/issues) 
* [Roadmap](https://github.com/camunda/camunda-bpm-assert/issues) 
* [Download](https://github.com/camunda/camunda-bpm-assert/releases)
* [Questions at camunda BPM users list](https://groups.google.com/forum/?fromgroups#!forum/camunda-bpm-users)
* [Feedback at camunda BPM dev list](https://groups.google.com/forum/?fromgroups#!forum/camunda-bpm-dev)
* Want to **contribute**? You are very welcome! Please contact me directly via [eMail](mailto:martin.schimak@plexiti.com).

<a name="maintainer"/>
## Maintainer

Martin Schimak - [eMail](mailto:martin.schimak@plexiti.com) - [GitHub](https://github.com/martinschimak) - [Blog](http://plexiti.com)

<a name="license"/>
## License

Apache License, Version 2.0
