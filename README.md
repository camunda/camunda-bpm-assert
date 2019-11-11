# <img src="http://camunda.github.io/camunda-bpm-assert/resources/images/camunda.png" width="23" height="23"></img>&nbsp;Camunda BPM Assert

<a href="https://travis-ci.org/camunda/camunda-bpm-assert"><img src="https://travis-ci.org/camunda/camunda-bpm-assert.svg?branch=master"/></a>
<a href="./LICENSE"><img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
<a href="https://maven-badges.herokuapp.com/maven-central/org.camunda.bpm.assert/camunda-bpm-assert"><img src="https://maven-badges.herokuapp.com/maven-central/org.camunda.bpm.assert/camunda-bpm-assert/badge.svg"/></a>

**Camunda BPM Assert** makes it easy to assert the status of your BPMN processes and CMMN cases when driving them forward in your typical unit test methods. Simply write code like

```groovy
assertThat(instance).isWaitingAt("UserTask_InformCustomer");
assertThat(task).hasCandidateGroup("Sales").isNotAssigned();
```

Furthermore a set of static helper methods is provided to make it easier to drive through a process. Based on the [80/20 principle](https://en.wikipedia.org/wiki/Pareto_principle) the library reaches out to make those things simple you need really often. You will e.g. often have a a single open task instance in your process instance. Then just write
 
```groovy
complete(task(instance), withVariables("approved", true));
```

## Compatibility

Camunda BPM Assert works with the versions of Camunda BPM as shown [in the documentation](https://docs.camunda.org/manual/latest/user-guide/testing/#assertions-version-compatibility) and multiple Java versions (1.8+). This is continuously verified by executing around 500 test cases against a [travis ci test matrix](https://travis-ci.org/camunda/camunda-bpm-assert). 

## Get started in _3 simple steps_!

**1.** Add a maven **test dependency** to your project:

```xml  
<dependency>
    <groupId>org.camunda.bpm.assert</groupId>
    <artifactId>camunda-bpm-assert</artifactId>
    <version>5.0.0</version>
    <scope>test</scope>
</dependency>
```

Additionally, Joel Costigliola's [AssertJ](https://assertj.github.io/doc/) needs to be provided as a dependency with a version that is compatible with the one documented in the [compatibility matrix](https://docs.camunda.org/manual/latest/user-guide/testing/#assertions-version-compatibility).

Please note that if you use [Spring Boot](https://spring.io/projects/spring-boot) or the [Camunda Spring Boot Starter](https://docs.camunda.org/manual/latest/user-guide/spring-boot-integration/) in your project, AssertJ is already included in your project's setup.

**2.** Add a **static import** to your test class

Create your test case just as described in the [Camunda BPM Testing Guide](https://docs.camunda.org/manual/latest/user-guide/testing/) and add Camunda BPM Assert by statically importing it in your test class:

```groovy  
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
```

**3.** Start using the assertions in your **test methods**

You now have access to all the Camunda BPM assertions. Assuming you want to assert that your process instance is actually **started**, **waiting** at a specific **user task** and that task should yet be **unassigned**, but waiting to be assigned to a user of a specific group, just write:

```groovy
assertThat(processInstance).isStarted()
  .task().hasDefinitionKey("edit")
    .hasCandidateGroup("human-resources")
    .isNotAssigned();
```

In case you want to combine Camunda BPM Assert with the assertions provided by AssertJ, your imports should look like this:
```groovy  
import static org.assertj.core.api.Assertions.*;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
```

Find a more detailed description of the assertions and helper methods available in the Camunda BPM Assert [**User Guide**](./docs/README.md).

## Further Resources

* [Documentation](https://docs.camunda.org/manual/latest/user-guide/testing#camunda-assertions)
* [User Guide](./docs/README.md)
* [Issue Tracker](https://app.camunda.com/jira/browse/CAM/component/14065) 
* [Contributing](./CONTRIBUTING.md)

## License

The source files in this repository are made available under the Apache License, Version 2.0.

## Credits

The Camunda BPM Assert project used to be the community extension, created and supported by

<img src="http://camunda.github.io/camunda-bpm-assert/resources/images/community-award.png" align="right" width="76">

[Martin Schimak](https://github.com/martinschimak) (plexiti GmbH)<a href="http://plexiti.com"><img src="http://plexiti.com/en/img/logo.png" align="right" width="210"></img></a><br>
[Jan Galinski](https://github.com/jangalinski) (Holisticon AG)<br>
[Martin Günther](https://github.com/margue) (Holisticon AG)<br>
[Malte Sörensen](https://github.com/malteser) (Holisticon AG)<br>
<a href="http://www.holisticon.de"><img src="https://www.holisticon.de/wp-content/uploads/2013/05/holisticon-logo-hamburg.gif" align="right" /></a>[Simon Zambrovski](https://github.com/zambrovski) (Holisticon AG)


... and [many others](https://github.com/camunda/camunda-bpm-assert/graphs/contributors).

In 2014, the library won the **Camunda BPM Community Award**.

Starting from version 3.0.0 it was adopted as part of the Camunda BPM Platform.
