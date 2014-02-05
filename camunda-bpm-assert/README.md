# ![camunda logo](http://camunda.github.io/camunda-bpm-assert/resources/images/camunda.png)&nbsp;camunda-bpm-assert User Guide

This little community-extension to [camunda BPM](http://camunda.org) wants to make it easier to write tests for BPMN process definitions executed with the camunda process engine. 
For that reason, a set of **assertions** based on Joel Costigliola's [AssertJ](https://github.com/joel-costigliola/assertj-core) rich assertions library for java are provided as well as a few little **helpers** to make it a bit easier to drive you through your process.

[Getting started](../README.md#getting-started)

### Table of Contents

 * [Assertions](#assertions)
   * for process instances: [isActive](#processInstance-isActive), [isEnded](#processInstance-isEnded), [isNotEnded](#processInstance-isNotEnded), [isStarted](#processInstance-isStarted), [isSuspended](#processInstance-isSuspended), [hasPassed](#processInstance-hasPassed), [isWaitingAt](#processInstance-isWaitingAt), [isWaitingAtExactly](#processInstance-isWaitingAtExactly)
   * for jobs: [hasDeploymentId](#job-hasDeploymentId), [hasDueDate](#job-hasDueDate), [hasId](#job-hasId), [hasRetries](#job-hasRetries)
   * for tasks: [isAssignedTo](#task-isAssignedTo), [isNotAssigned](#task-isNotAssigned), [hasCandidateGroup](#task-hasCandidateGroup), [hasDefinitionKey](#task-hasDefinitionKey), [hasDescription](#task-hasDescription), [hasDueDate](#task-hasDueDate), [hasId](#task-hasId), [hasName](#task-hasName)
 
 * [Helpers](#helpers)
   * [Claiming tasks](#helpers-claim)
   * [Completing tasks](#helpers-complete)
   * [Completing tasks and passing process variables](#helpers-variables)
   * [Creating queries](#helpers-queries)
   * [Accessing engine and engine API services](#helpers-services)
   * [Making assertions on the only task of an instance](#helpers-task)
   * [Making assertions on a specific task of an instance](#helpers-task-taskquery)
   * [Accessing last asserted task](#helpers-task-last)
   
 * [Advanced Usage](#advanced)
   * [Using a non-default process engine](#non-default-engine)

<a name="assertions"/>
## Assertions

<a name="processInstance-isActive"/>
#### Instance: isActive

Assert that a process instance is currently 'active', so neither suspended nor ended:

```java
assertThat(processInstance).isActive();
```

<a name="processInstance-isEnded"/>
#### Instance: isEnded

Assert that a process instance is already ended:

```java
assertThat(processInstance).isEnded();
```

<a name="processInstance-isNotEnded"/>
#### Instance: isNotEnded

Assert that a process instance is not ended:

```java
assertThat(processInstance).isNotEnded();
```

<a name="processInstance-isStarted"/>
#### Instance: isStarted

Assert that a process instance is started:

```java
assertThat(processInstance).isStarted();
```

<a name="processInstance-isSuspended"/>
#### Instance: isSuspended

Assert that a process instance is suspended:

```java
assertThat(processInstance).isSuspended();
```

<a name="processInstance-hasPassed"/>
#### Instance: hasPassed

Assert that a process instance has passed a specified activity:

```java
assertThat(processInstance).hasPassed("edit");
```

Assert that a process instance has passed several specified activities:

```java
assertThat(processInstance).hasPassed("edit", "correct");
```

<a name="processInstance-isWaitingAt"/>
#### Instance: isWaitingAt

Assert that a process instance is currently waiting at a specified activity:

```java
assertThat(processInstance).isWaitingAt("edit");
```

Assert that a process instance is currently waiting at several specified activities:

```java
assertThat(processInstance).isWaitingAt("edit", "correct");
```

<a name="processInstance-isWaitingAtExactly"/>
#### Instance: isWaitingAtExactly

Assert that a process instance is currently waiting at exactly one specified activity:

```java
assertThat(processInstance).isWaitingAtExactly("edit");
```

Assert that a process instance is currently waiting at exactly the several specified activities:

```java
assertThat(processInstance).isWaitingAtExactly("edit", "correct");
```


<a name="job-hasDeploymentId"/>
#### Job: hasDeploymentId

```java
assertThat(job).hasDeploymentId(deploymentId);
```
 
<a name="job-hasDueDate"/>
#### Job: hasDueDate

Assert that a job is due at a specific date:

```java
assertThat(job).hasDueDate(dueDate);
```

<a name="job-hasId"/>
#### Job: hasId

Assert a specific internal id for the job:

```java
assertThat(job).hasId(id);
```

<a name="job-hasRetries"/>
#### Job: hasRetries

Assert that a job has a specific number of retries left:

```java
assertThat(job).hasRetries(3);
```

<a name="task-isAssignedTo"/>
#### Task: isAssignedTo

Assert that a specified user is assigned to a task:

```java
assertThat(task).isAssignedTo("kermit");
```

<a name="task-isNotAssigned"/>
#### Task: isNotAssigned

Assert that a task is currently not assigned to any user:

```java
assertThat(task).isNotAssigned();
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

<a name="helpers-claim"/>
#### Claiming tasks

You can directly claim a task by means of a static helper method:

```java
claim(task, "fozzie"); 
```

<a name="helpers-complete"/>
#### Completing tasks

You can directly complete a task by means of static helper method:

```java
complete(task);
```

<a name="helpers-variables"/>
#### Completing tasks and passing process variables

You can directly construct a map of process variables by passing a sequence 
of key/value pairs to the static helper method "withVariables":

```java
Map<String, Object> variables = withVariables("documentId", 5, "approved", true); 
```

You can therefore e.g. write

```java
complete(task, withVariables("documentId", 5, "approved", true)); 
```

<a name="helpers-queries"/>
#### Creating queries

You can directly create queries by means of static helper methods:

```java
TaskQuery taskQuery = taskQuery();
JobQuery jobQuery = jobQuery();
ProcessInstanceQuery processInstanceQuery = processInstanceQuery();
ExecutionQuery executionQuery = executionQuery();
```

You can therefore e.g. write

```java
assertThat(processInstance).task(taskQuery().taskAssignee("fozzie")).hasCandidateGroup("human-resources-department");
```

<a name="helpers-services"/>
#### Accessing engine and engine API services

You can directly access the engine and API services by means of static helper methods:

```java
ProcessEngine engine = processEngine();

AuthorizationService authorizationService = authorizationService();
FormService formService = formService();
HistoryService historyService = historyService();
IdentityService identityService = identityService();
ManagementService managementService = managementService();
RepositoryService repositoryService = repositoryService();
RuntimeService runtimeService = runtimeService();
TaskService taskService = taskService();
```

<a name="helpers-task"/>
#### Making assertions on the only task of an instance
 
You can retrieve a "chained" task assert inspecting the one and only 
one task currently available in the context of a process instance...

```java
assertThat(processInstance).task();
```

... in order to directly make assertions on it, e.g. 

```java
assertThat(processInstance).task().isNotAssigned();
```

<a name="helpers-task-taskquery"/>
#### Making assertions on a specific task of an instance

You can retrieve a "chained" task assert inspecting a very specific task currently 
available in the context of a process instance...

```java
assertThat(processInstance).task(taskQuery().taskAssignee("fozzie"));
```

... in order to directly make assertions on it, e.g. 

```java
assertThat(processInstance).task(taskQuery().taskAssignee("fozzie")).isAssignedTo("fozzie");
```

<a name="helpers-task-last"/>
#### Accessing the last task under test of an assertion

You can directly access the last asserted task by means of a static helper method task():

```java
assertThat(processInstance).task().hasDefinitionKey("review-and-approve")
...
Task lastAsserted = task();
```
  
You can therefore e.g. write ...

```java
assertThat(processInstance).task().hasDefinitionKey("review-and-approve");
complete(task(), withVariables("documentId", 5, "approved", true)); 
```

<a name="advanced"/>
## Advanced topics

<a name="non-default-engine"/>
#### Using a non-default process engine 

In case you want to test with a process engine, which is not the default engine, 
you can bind a specific process engine to your testing thread by means of
the following initialisation code. This makes the assertions aware of your process 
engine, e.g.:
 
```java  
   @Before
   public void setUp() {
     init(processEngineRule.getProcessEngine());
   }
```
