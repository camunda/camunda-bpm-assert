# <img src="http://camunda.github.io/camunda-bpm-assert/resources/images/camunda.png" width="23" height="23"></img>&nbsp;Camunda BPM Assert

The community extension **Camunda BPM Assert** makes it easy to **assert** the status of your BPMN processes and CMMN cases when **driving** them forward in your typical unit test methods. Simply write code like

```groovy
assertThat(instance).isWaitingAt("UserTask_InformCustomer");
assertThat(task).hasCandidateGroup("Sales").isNotAssigned();
```

Furthermore a set of static helper methods is provided to make it easier to drive through a process. Based on the [80/20 principle](https://en.wikipedia.org/wiki/Pareto_principle) the library reaches out to make those things simple you need really often. You will e.g. often have a a single open task instance in your process instance. Then just write
 
```groovy
complete(task(instance), withVariables("approved", true));
```

<img src="http://camunda.github.io/camunda-bpm-assert/resources/images/spaghetti-test.jpg" align="left" width="400"><br/>The goal? More **readable code** - less **spaghetti tests**!<br/>The increased readability will make it much easier to dig through your test code, the next time you need to change it will ultimately come. And your increased efficiency will make your organisational stakeholders happy, too! :smile:<br/></img>

## <a href="https://travis-ci.org/camunda/camunda-bpm-assert"><img src="https://travis-ci.org/camunda/camunda-bpm-assert.svg?branch=master" align="right"/></a>Use it with confidence!

<img src="http://camunda.github.io/camunda-bpm-assert/resources/images/community-award.png" align="right" width="76">Camunda BPM Assert works with **all versions of Camunda BPM** since 7.0 up to the most recent and *all the Java versions* (1.6, 1.7., 1.8) still relevant for Camunda BPM installations out there. This is continuously verified by executing around 350 test cases against a [travis ci test matrix](https://travis-ci.org/camunda/camunda-bpm-assert). In 2014, the library won the **Camunda BPM Community Award**. 

## Get started in _3 simple steps_!

<a href="https://maven-badges.herokuapp.com/maven-central/org.camunda.bpm.extension/camunda-bpm-assert"><img src="https://maven-badges.herokuapp.com/maven-central/org.camunda.bpm.extension/camunda-bpm-assert/badge.svg" align="right"/></a>**1.** Add a maven **test dependency** to your project:

```xml  
<dependency>
    <groupId>org.camunda.bpm.extension</groupId>
    <artifactId>camunda-bpm-assert</artifactId>
    <version>1.2</version> <!-- Use 2.0-alpha1 for the CMMN assertions preview! -->
    <scope>test</scope>
</dependency>
```

Note however, that if you use a [Camunda BPM Maven Archetype](https://docs.camunda.org/manual/latest/user-guide/process-applications/maven-archetypes/) to create your project, Camunda BPM Assert is already included in your project setup.

**2.** Add a **static import** to your test class

Create your test case just as described in the [Camunda BPM Testing Guide](https://docs.camunda.org/manual/latest/user-guide/testing/). As recommended at the end of that guide, add Camunda BPM Assert by statically importing it in your test class:

```groovy  
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
```

**3.** Start using the assertions in your **test methods**

You have now access to all the assertions provided by Joel Costigliola's [AssertJ](http://joel-costigliola.github.io/assertj/) rich assertions library - plus the additional Camunda BPM assertions building upon them. Now assume you want to assert that your process instance is actually **started**, **waiting** at a specific **user task** and that task should yet be **unassigned**, but waiting to be assigned to a user of a specific group? Then write:

```groovy
assertThat(processInstance).isStarted()
  .task().hasDefinitionKey("edit")
    .hasCandidateGroup("human-resources")
    .isNotAssigned();
```

<img src="http://camunda.github.io/camunda-bpm-assert/resources/images/green-bar.png" align="right"></img> Green bar? 

Congrats! You are successfully using Camunda BPM Assert. Find a more detailed description of the assertions and helper methods available in the Camunda BPM Assert [**User Guide**](./camunda-bpm-assert/README.md).

## Further Resources

* [User Guide](./camunda-bpm-assert/README.md)
* [API Docs](http://camunda.github.io/camunda-bpm-assert/apidocs/) 
* [Issue Tracker](https://github.com/camunda/camunda-bpm-assert/issues) 
* [Roadmap](https://github.com/camunda/camunda-bpm-assert/issues/milestones?state=open&with_issues=no) 
* [Download](https://github.com/camunda/camunda-bpm-assert/releases)
* [Continuous Integration](https://travis-ci.org/camunda/camunda-bpm-assert)

## Maintenance &amp; License

<a href="http://plexiti.com"><img src="http://plexiti.com/en/img/logo.png" align="right" width="210"></img></a>Martin Schimak<br/>[Blog](http://plexiti.com) &#8226; [GitHub](https://github.com/martinschimak) &#8226; [eMail](mailto:martin.schimak@plexiti.com)<br/>Apache License, Version 2.0

## Contributors

<a href="http://www.holisticon.de"><img src="https://www.holisticon.de/wp-content/uploads/2013/05/holisticon-logo-hamburg.gif" align="right" /></a>[Jan Galinski](https://github.com/jangalinski) (Holisticon AG)<br>
[Martin Günther](https://github.com/margue) (Holisticon AG)<br>
[Malte Sörensen](https://github.com/malteser) (Holisticon AG)<br>
[Simon Zambrovski](https://github.com/zambrovski) (Holisticon AG)

... and [many others](https://github.com/camunda/camunda-bpm-assert/graphs/contributors). You want to **contribute**? You are very welcome! Please contact me directly via [eMail](mailto:martin.schimak@plexiti.com).
