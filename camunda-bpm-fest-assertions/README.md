# ![camunda logo](./src/main/resources/images/camunda.png)&nbsp;camunda-bpm-assert User Guide

This little community-extension to [camunda BPM](http://camunda.org) wants to make it easier to write tests for BPMN process definitions executed with the camunda process engine. 
For that reason, a set of **assertions** based on the [fest-2](https://github.com/alexruiz/fest-assert-2.x/wiki) fixtures for easy software testing are provided as well as a few little **helpers** to make it a bit easier to drive you through your process.

[Getting started](../README.md#getting-started)

### Table of Contents

 * [Assertions](#assertions)
   * Instance: [isAssignedTo](#processInstance-isActive)
   * Instance: [isAssignedTo](#processInstance-isEnded)
   * Instance: [isAssignedTo](#processInstance-isStarted)
   * Instance: [isAssignedTo](#processInstance-isWaitingAt)
   * Task: [isAssignedTo](#task-isAssignedTo)
   * Task: [isUnassigned](#task-isUnassigned)
   * Task: [hasCandidateGroup](#task-hasCandidateGroup)
   * Task: [hasDefinitionKey](#task-hasDefinitionKey)
   * Task: [hasDescription](#task-hasDescription)
   * Task: [hasDueDate](#task-hasDueDate)
   * Task: [hasId](#task-hasId)
   * Task: [hasName](#task-hasName)
 
 * [Helpers](#helpers)  

<a name="assertions"/>
## Assertions

<a name="processInstance-isActive"/>
#### Instance: isActive

Assert that a process instance is currently 'active', so neither suspended nor finished:

```java
assertThat(processInstance).isActive();
```

<a name="processInstance-isEnded"/>
#### Instance: isEnded

Assert that a process instance has already ended:

```java
assertThat(processInstance).isEnded();
```

<a name="processInstance-isStarted"/>
#### Instance: isStarted

Assert that a process instance is started:

```java
assertThat(processInstance).isStarted();
```

<a name="processInstance-isWaitingAt"/>
#### Instance: isWaitingAt

Assert that a process instance is currently waiting at a specified activity:

```java
assertThat(processInstance).isWaitingAt("edit");
```

<a name="task-isAssignedTo"/>
#### Task: isAssignedTo

Assert that a specified user is assigned to a task:

```java
assertThat(task).isAssignedTo("kermit");
```

<a name="task-isUnassigned"/>
#### Task: isUnassigned

Assert that a task is currently not assigned to any user:

```java
assertThat(task).isUnassigned();
```

<a name="task-hasCandidateGroup"/>
#### Task: hasCandidateGroup

Assert that a task is connected with a specified candidate group:

```java
assertThat(task).hasCandidateGroup("human-resources-department");
```

<a name="task-hasDefinitionKey"/>
#### Task: hasDefinitionKey

Assert that a task has the specified definition key (aka the id attribute of the <code>&lt;userTask id="review-and-approve" .../&gt;</code> element in the process definition BPMN 2.0 XML file):

```java
assertThat(task).hasDefinitionKey("review-and-approve");
```

<a name="task-hasDescription"/>
#### Task: hasDescription

Assert that the task has the specified free text description:

```java
assertThat(task).hasDescription("Please review and approve the result document.");
```

<a name="task-hasDueDate"/>
#### Task: hasDueDate

Assert that a task is due at a specified date:

```java
assertThat(task).hasDueDate(expectedDueDate);
```

<a name="task-hasId"/>
#### Task: hasId

Assert that a task has the specified internal id:

```java
assertThat(task).hasId("1");
```

<a name="task-hasName"/>
#### Task: hasName

Assert that the task has the specified name:

```java
assertThat(task).hasName("Review and approve");
```

<a name="helpers"/>
## Helpers

