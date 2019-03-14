# ![camunda logo](http://camunda.github.io/camunda-bpm-assert/resources/images/camunda.png)&nbsp;camunda-bpm-assert User Guide for BPMN

### Table of Contents

 * [Assertions](#assertions)
   * for process instances: [isActive](#processInstance-isActive), [isEnded](#processInstance-isEnded), [isNotEnded](#processInstance-isNotEnded), [isStarted](#processInstance-isStarted), [isSuspended](#processInstance-isSuspended), [hasPassed](#processInstance-hasPassed), [hasPassedInOrder](#processInstance-hasPassedInOrder), [hasNotPassed](#processInstance-hasNotPassed), [hasVariables](#processInstance-hasVariables), [hasNoVariables](#processInstance-hasNoVariables), [hasProcessDefinitionKey](#processInstance-hasProcessDefinitionKey), [isWaitingAt](#processInstance-isWaitingAt), [isNotWaitingAt](#processInstance-isNotWaitingAt), [isWaitingAtExactly](#processInstance-isWaitingAtExactly), [isWaitingFor](#processInstance-isWaitingFor), [isNotWaitingFor](#processInstance-isNotWaitingFor)
   * for process definitions: [hasActiveInstances](#processDefinition-hasActiveInstances)
   * for jobs: [hasActivityId](#job-hasActivityId), [hasDeploymentId](#job-hasDeploymentId), [hasDueDate](#job-hasDueDate), [hasId](#job-hasId), [hasRetries](#job-hasRetries)
   * for tasks: [isAssignedTo](#task-isAssignedTo), [isNotAssigned](#task-isNotAssigned), [hasCandidateGroup](#task-hasCandidateGroup), [hasCandidateGroupAssociated](#task-hasCandidateGroupAssociated), [hasCandidateUser](#task-hasCandidateUser), [hasCandidateUserAssociated](#task-hasCandidateUserAssociated), [hasDefinitionKey](#task-hasDefinitionKey), [hasDescription](#task-hasDescription), [hasDueDate](#task-hasDueDate), [hasFormKey](#task-hasFormKey), [hasId](#task-hasId), [hasName](#task-hasName)
 
 * [Helpers](#helpers)
   * [Claiming tasks](#helpers-claim)
   * [Unclaiming tasks](#helpers-unclaim)
   * [Completing tasks](#helpers-complete)
   * [Completing tasks and passing process variables](#helpers-complete-variables)
   * [Executing jobs](#helpers-execute)
   * [Sending messages](#helpers-send)
   * [Sending messages and passing process variables](#helpers-send-variables)
   * [Creating queries](#helpers-queries)
   * [Accessing engine and engine API services](#helpers-services)
   * [Making assertions on the only task of an instance](#helpers-task)
   * [Making assertions on a specific task of an instance](#helpers-task-taskquery)
   * [Making assertions on the only job of an instance](#helpers-job)
   * [Making assertions on a specific jobs of an instance](#helpers-job-jobquery)
   * [Making assertions on the only called process instance of a super instance](#helpers-called-process-instance)
   * [Making assertions on a specific called process instance of a super instance](#helpers-called-process-instance-jobquery)
   * [Making assertions on the process variables map of an instance](#helpers-variables)
   * [Accessing tasks in the context of a process instance under test](#helpers-task-last)
   * [Accessing jobs in the context of a process instance under test](#helpers-job-last)
   * [Accessing called process instances in the context of a process instance under test](#helpers-called-process-instance-last)
   * [Accessing process definitions](#helpers-process-definition)

<a name="assertions"></a>
## Assertions

<a name="processInstance-isActive"></a>
#### Instance: isActive

Assert that a process instance is currently 'active', so neither suspended nor ended:

```java
assertThat(processInstance).isActive();
```

<a name="processInstance-isEnded"></a>
#### Instance: isEnded

Assert that a process instance is already ended:

```java
assertThat(processInstance).isEnded();
```

<a name="processInstance-isNotEnded"></a>
#### Instance: isNotEnded

Assert that a process instance is not ended:

```java
assertThat(processInstance).isNotEnded();
```

<a name="processInstance-isStarted"></a>
#### Instance: isStarted

Assert that a process instance is started:

```java
assertThat(processInstance).isStarted();
```

<a name="processInstance-isSuspended"></a>
#### Instance: isSuspended

Assert that a process instance is suspended:

```java
assertThat(processInstance).isSuspended();
```

<a name="processInstance-hasPassed"></a>
#### Instance: hasPassed

Assert that a process instance has passed a specified activity:

```java
assertThat(processInstance).hasPassed("edit");
```

Assert that a process instance has passed several specified activities:

```java
assertThat(processInstance).hasPassed("edit", "correct");
```

<a name="processInstance-hasPassedInOrder"></a>
#### Instance: hasPassedInOrder

Assert that a process instance has passed several specified activities 
exactly in the given order:

```java
assertThat(processInstance).hasPassedInOrder("edit", "review", "correct");
```

You can even assert that a specific activity has been passed several times:

```java
assertThat(processInstance).hasPassedInOrder("edit", "review", "correct", "review", "correct", "review", "publish");
```

It doesn't matter whether other activities have been passed in between those
specified activities. Everything that matters is that the order is correct.
So in the example just given, this more minimalistic assertion would also pass:

```java
assertThat(processInstance).hasPassedInOrder("edit", "review", "publish");
```

<a name="processInstance-hasNotPassed"></a>
#### Instance: hasNotPassed

Assert that a process instance has not passed a specified activity:

```java
assertThat(processInstance).hasNotPassed("edit");
```

Assert that a process instance has not passed any of several specified activities:

```java
assertThat(processInstance).hasNotPassed("edit", "correct");
```

<a name="processInstance-hasVariables"></a>
#### Instance: hasVariables

Assert that a process instance holds at least one process variable:

```java
assertThat(processInstance).hasVariables();
```

Assert that a process instance holds - aside potential other variables - one or more specified process variables:

```java
assertThat(processInstance)
  .hasVariables("approved")
  .hasVariables("jobAnnouncementId", "approved");
```

<a name="processInstance-hasNoVariables"></a>
#### Instance: hasNoVariables

Assert that a process instance holds no process variables at all:

```java
assertThat(processInstance).hasNoVariables();
```

<a name="processInstance-hasProcessDefinitionKey"></a>
#### Instance: hasProcessDefinitionKey

Assert that a process instance is based on a specific process definition:

```java
assertThat(processInstance).hasProcessDefinitionKey("myProcessDefinitionKey");
```

<a name="processInstance-isWaitingAt"></a>
#### Instance: isWaitingAt

Assert that a process instance is currently waiting at a specified activity:

```java
assertThat(processInstance).isWaitingAt("edit");
```

Assert that a process instance is currently waiting at several specified activities:

```java
assertThat(processInstance).isWaitingAt("edit", "correct");
```

<a name="processInstance-isNotWaitingAt"></a>
#### Instance: isNotWaitingAt

Assert that a process instance is currently NOT waiting at a specified activity:

```java
assertThat(processInstance).isNotWaitingAt("edit");
```

Assert that a process instance is currently NOT waiting at several specified activities:

```java
assertThat(processInstance).isNotWaitingAt("edit", "correct");
```

<a name="processInstance-isWaitingAtExactly"></a>
#### Instance: isWaitingAtExactly

Assert that a process instance is currently waiting at exactly one specified activity:

```java
assertThat(processInstance).isWaitingAtExactly("edit");
```

Assert that a process instance is currently waiting at exactly the several specified activities:

```java
assertThat(processInstance).isWaitingAtExactly("edit", "correct");
```

<a name="processInstance-isWaitingFor"></a>
#### Instance: isWaitingFor

Assert that a process instance is currently waiting for a specified message:

```java
assertThat(processInstance).isWaitingFor("myMessage");
```

Assert that a process instance is currently waiting for several specified messages:

```java
assertThat(processInstance).isWaitingFor("myMessage", "yourMessage");
```

<a name="processInstance-isNotWaitingFor"></a>
#### Instance: isNotWaitingFor

Assert that a process instance is currently NOT waiting for a specified message:

```java
assertThat(processInstance).isNotWaitingFor("myMessage");
```

Assert that a process instance is currently NOT waiting for any of several specified messages:

```java
assertThat(processInstance).isNotWaitingFor("myMessage", "yourMessage");
```

<a name="processDefinition-hasActiveInstances"></a>
#### Definition: hasActiveInstances

Assert that a process definition currently has exactly the expected number of 'active' 
(so neither ended nor suspended) instances:

```java
assertThat(processDefinition).hasActiveInstances("1");
```

<a name="job-hasActivityId"></a>
#### Job: hasActivityId

Assert that a job is based on an activity definition with a specific id:

```java
assertThat(job).hasActivityId("ServiceTask_1");
```
 
<a name="job-hasDeploymentId"></a>
#### Job: hasDeploymentId

Assert that a job has a specific deployment id:

```java
assertThat(job).hasDeploymentId(deploymentId);
```
 
<a name="job-hasDueDate"></a>
#### Job: hasDueDate

Assert that a job is due at a specific date:

```java
assertThat(job).hasDueDate(dueDate);
```

<a name="job-hasId"></a>
#### Job: hasId

Assert a specific internal id for the job:

```java
assertThat(job).hasId(id);
```

<a name="job-hasRetries"></a>
#### Job: hasRetries

Assert that a job has a specific number of retries left:

```java
assertThat(job).hasRetries(3);
```

<a name="task-isAssignedTo"></a>
#### Task: isAssignedTo

Assert that a specified user is assigned to a task:

```java
assertThat(task).isAssignedTo("kermit");
```

<a name="task-isNotAssigned"></a>
#### Task: isNotAssigned

Assert that a task is currently not assigned to any user:

```java
assertThat(task).isNotAssigned();
```

<a name="task-hasCandidateGroup"></a>
#### Task: hasCandidateGroup

Assert that a task is is currently waiting to be assigned 
to a user of the specified candidate group.

```java
assertThat(task).hasCandidateGroup("human-resources-department");
```

Note that (in line with Camunda's interpretation of the term 
'candidate') **assigned** tasks will not pass this assertion. 
However, the next assertion discussed here, would pass:                      
                      
<a name="task-hasCandidateGroupAssociated"></a>
#### Task: hasCandidateGroupAssociated

Use from Camunda BPM **7.3** on: 

Assert the expectation that a task is currently associated to the 
specified candidate group - no matter whether it is already assigned to a 
specific user or not.

```java
assertThat(task).hasCandidateGroupAssociated("human-resources-department");
```

<a name="task-hasCandidateUser"></a>
#### Task: hasCandidateUser

Assert that a task is currently waiting to be assigned to a specified candidate user:

```java
assertThat(task).hasCandidateUser("kermit");
```

Note that (in line with Camunda's interpretation of the term 
'candidate') **assigned** tasks will not pass this assertion. 
However, the next assertion discussed here, would pass:                      

<a name="task-hasCandidateUserAssociated"></a>
#### Task: hasCandidateUserAssociated

Use from Camunda BPM **7.3** on: 

Assert the expectation that a task is currently associated to the 
specified candidate user - no matter whether it is already assigned to a 
specific user or not.

```java
assertThat(task).hasCandidateUserAssociated("kermit");
```

<a name="task-hasDefinitionKey"></a>
#### Task: hasDefinitionKey

Assert that a task has the specified definition key (aka the id attribute of the <code>&lt;userTask id="review-and-approve" .../&gt;</code> element in the process definition BPMN 2.0 XML file):

```java
assertThat(task).hasDefinitionKey("review-and-approve");
```

<a name="task-hasDescription"></a>
#### Task: hasDescription

Assert that the task has the specified free text description:

```java
assertThat(task).hasDescription("Please review and approve the result document.");
```

<a name="task-hasDueDate"></a>
#### Task: hasDueDate

Assert that a task is due at a specified date:

```java
assertThat(task).hasDueDate(expectedDueDate);
```

<a name="task-hasFormKey"></a>
#### Task: hasFormKey

Assert that a task is associated to a specified form (key):

```java
assertThat(task).hasFormKey("myForm.html");
```

<a name="task-hasId"></a>
#### Task: hasId

Assert that a task has the specified internal id:

```java
assertThat(task).hasId("1");
```

<a name="task-hasName"></a>
#### Task: hasName

Assert that the task has the specified name:

```java
assertThat(task).hasName("Review and approve");
```

<a name="helpers"></a>
## Helpers

<a name="helpers-claim"></a>
#### Claiming tasks

You can directly claim a task by means of a static helper method:

```java
claim(task, "fozzie"); 
```

<a name="helpers-unclaim"></a>
#### Unclaiming tasks

You can directly unclaim a task by means of a static helper method:

```java
unclaim(task); 
```

<a name="helpers-complete"></a>
#### Completing tasks

You can directly complete a task by means of a static helper method:

```java
complete(task);
```

<a name="helpers-complete-variables"></a>
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

<a name="helpers-execute"></a>
#### Executing jobs

You can directly execute a job by means of a static helper method:

```java
execute(job());
```

<a name="helpers-queries"></a>
#### Creating queries

You can directly create queries by means of a static helper methods:

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

<a name="helpers-services"></a>
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

<a name="helpers-task"></a>
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

<a name="helpers-task-taskquery"></a>
#### Making assertions on a specific task of an instance

You can retrieve a "chained" task assert inspecting a very specific task currently 
available in the context of a process instance...

```java
assertThat(processInstance).task("edit");
```

or

```java
assertThat(processInstance).task(taskQuery().taskAssignee("fozzie"));
```

... in order to directly make assertions on it, e.g. 

```java
assertThat(processInstance).task("edit").isAssignedTo("fozzie");
```

<a name="helpers-job"></a>
#### Making assertions on the only job of an instance
 
You can retrieve a "chained" job assert inspecting the one and only 
one job currently available in the context of a process instance...

```java
assertThat(processInstance).job();
```

... in order to directly make assertions on it, e.g. 

```java
assertThat(processInstance).job().hasRetries(0);
```

<a name="helpers-job-jobquery"></a>
#### Making assertions on a specific job of an instance

You can retrieve a "chained" job assert inspecting a very specific job currently 
available in the context of a process instance...

```java
assertThat(processInstance).job("ServiceTask_1");
```
or

```java
assertThat(processInstance).job(jobQuery().executionId(executionId));
```

... in order to directly make assertions on it, e.g. 

```java
assertThat(processInstance).job("ServiceTask_1").hasRetries(0);
```

<a name="helpers-called-process-instance"></a>
#### Making assertions on the only called process of a super process instance
 
You can retrieve a "chained" process instance assert inspecting the one and only 
called process instance currently available in the context of a super process instance...

```java
assertThat(processInstance).calledProcessInstance();
```

... in order to directly make assertions on it, e.g. 

```java
assertThat(processInstance).calledProcessInstance().hasProcessDefinitionKey("mySubProcessDefinitionKey");
```

<a name="helpers-called-process-instance-jobquery"></a>
#### Making assertions on a specific called process instance of a super process instance

You can retrieve a "chained" process instance assert inspecting a very specific called process instance currently 
available in the context of a super process instance, either by means of a processDefinitionKey...

```java
assertThat(processInstance).calledProcessInstance("mySubProcessDefinitionKey");
```
or even by means of a more sophisticated processInstanceQuery

```java
assertThat(processInstance).calledProcessInstance(processInstanceQuery().processDefinitionKey("mySubProcessDefinitionKey"));
```

... in order to directly make assertions on it, e.g. 

```java
assertThat(processInstance).calledProcessInstance("mySubProcessDefinitionKey").isNotNull();
```

<a name="helpers-variables"></a>
#### Making assertions on the process variables map of an instance

You can retrieve a "chained" process variables map assert inspecting all the process variables 
available in the context of a process instance...

```java
assertThat(processInstance).variables();
```

... in order to directly make assertions on them, e.g. 

```java
assertThat(processInstance).variables()
  .hasSize(2).containsEntry("approved", true);
```

You may want to compare that with the other possibility to assert whether a process instance 
[hasVariables](#processInstance-hasVariables) (without leaving your current ProcessInstanceAssert). 

<a name="helpers-task-last"></a>
#### Accessing tasks in the context of a process instance under test

You can directly access tasks in the context of the last asserted process 
instance by means of static helper methods:

```java
assertThat(processInstance).isNotNull();
...
Task onlyTaskOflastAssertedProcessInstance = task();
Task someTaskOflastAssertedProcessInstance = task("review-and-approve");
Task sameTaskOflastAssertedProcessInstance = task(taskQuery().taskDefinitionKey("review-and-approve"));
```
  
You can therefore e.g. write ...

```java
assertThat(processInstance).task().hasDefinitionKey("review-and-approve");
complete(task(), withVariables("documentId", 5, "approved", true)); 
```

Furthermore you can directly access tasks in the context of a *specified* process 
instance by means of static helper methods:

```java
Task onlyTaskOfProcessInstance = task(processInstance);
Task someTaskOfProcessInstance = task("review-and-approve", processInstance);
Task sameTaskOfProcessInstance = task(taskQuery().taskDefinitionKey("review-and-approve"), processInstance);
```
  
You can therefore e.g. write ...

```java
complete(task("review-and-approve", processInstance), withVariables("documentId", 5, "approved", true)); 
```

<a name="helpers-job-last"></a>
#### Accessing jobs in the context of a process instance under test

You can directly access jobs in the context of the last asserted process 
instance by means of static helper methods:

```java
assertThat(processInstance).isNotNull();
...
Job onlyJobOflastAssertedProcessInstance = job();
Job someJobOflastAssertedProcessInstance = job("publish");
Job someJobOflastAssertedProcessInstance = job(jobQuery().executionId(executionId));
```
  
You can therefore e.g. write ...

```java
assertThat(processInstance).job("publish").isNotNull();
execute(job("publish")); 
```

Furthermore you can directly access jobs in the context of a *specified* process 
instance by means of static helper methods:

```java
Task onlyJobOfProcessInstance = job(processInstance);
Task someJobOfProcessInstance = job("publish", processInstance);
Task sameJobOfProcessInstance = job(jobQuery().executable(), processInstance);
```
  
You can therefore e.g. write ...

```java
execute(job("publish", processInstance)); 
```

<a name="helpers-called-process-instance-last"></a>
#### Accessing called process instances in the context of a process instance under test

You can directly access called process instances in the context of the last asserted process 
instance by means of static helper methods:

```java
assertThat(processInstance).isNotNull();
...
ProcessInstance onlyCalledProcessInstanceOflastAssertedProcessInstance = calledProcessInstance();
ProcessInstance someCalledProcessInstanceOflastAssertedProcessInstance = calledProcessInstance("myCalledProcessDefinitionKey");
ProcessInstance someCalledProcessInstanceOflastAssertedProcessInstance = calledProcessInstance(processInstanceQuery().processDefinitionKey("myCalledProcessDefinitionKey"));
```
  
You can therefore e.g. write ...

```java
assertThat(processInstance).isNotNull();
ProcessInstance calledProcessInstance = calledProcessInstance(); 
```

Furthermore you can directly access jobs in the context of a *specified* super process 
instance by means of static helper methods:

```java
ProcessInstance onlyCalledProcessInstanceOfProcessInstance = calledProcessInstance(superProcessInstance);
ProcessInstance someCalledProcessInstanceOfProcessInstance = calledProcessInstance("myCalledProcessDefinitionKey", superProcessInstance);
ProcessInstance sameCalledProcessInstanceOfProcessInstance = calledProcessInstance(processInstanceQuery().processDefinitionKey("myCalledProcessDefinitionKey"), superProcessInstance);
```
  
You can therefore e.g. write ...

```java
ProcessInstance calledProcessInstance = calledProcessInstance("myCalledProcessDefinitionKey", superProcessInstance); 
```

<a name="helpers-process-definition"></a>
#### Accessing process definitions

You can directly access process definitions by means of static helper methods:

```java
ProcessDefinition processDefinitionOflastAssertedProcessInstance = processDefinition();
ProcessDefinition processDefinitionOfSpecifiedProcessInstance = processDefinition(processInstance);
ProcessDefinition processDefinitionOfSpecifiedProcessDefinitionKey = processDefinition("myProcessDefintionKey");
ProcessDefinition processDefinitionConformingToSpecifiedQuery = processDefinition(processDefinitionQuery().processDefinitionKey("myProcessDefintionKey");
```
  
In order to check, whether your last asserted process instance is the only currently running 
instance of its own process definition you can therefore e.g. write ...

```java
assertThat(processDefinition()).hasActiveInstances(1);
```
                           
