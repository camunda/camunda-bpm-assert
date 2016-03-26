# ![camunda logo](http://camunda.github.io/camunda-bpm-assert/resources/images/camunda.png)&nbsp;camunda-bpm-assert User Guide for CMMN

 * [Assertions](#assertions)
    * for human tasks: [isActive](#humanTask-isActive), [isAvailable](#humanTask-isAvailable), [isCompleted](#humanTask-isCompleted), [isDisabled](#humanTask-isDisabled), [isEnabled](#humanTask-isEnabled), [isTerminated](#humanTask-isTerminated), [hasVariables](#humanTask-hasVariables), [hasNoVariables](#humanTask-hasNoVariables)
 
 * [Helpers](#helpers)
   * [Making assertions on the case variables map of an instance](#helpers-variables)

## Assertions

<a name="humanTask-hasVariables"/>
#### Instance: hasVariables

Assert that a human task holds at least one case variable:

```java
assertThat(humanTask).hasVariables();
```

Assert that a human task holds - aside potential other variables - one or more specified case variables:

```java
assertThat(humanTask)
  .hasVariables("approved")
  .hasVariables("jobAnnouncementId", "approved");
```

<a name="humanTask-hasNoVariables"/>
#### Instance: hasNoVariables

Assert that a human task holds no case variables at all:

```java
assertThat(humanTask).hasNoVariables();
```

<a name="humanTask-isActive"/>
#### HumanTask: isActive

Assert that a human task is currently 'active':

```java
assertThat(caseInstance).humanTask("PI_TaskA").isActive();
```
<a name="humanTask-isAvailable"/>
#### HumanTask: isAvailable

Assert that a human task is currently 'available':

```java
assertThat(caseInstance).humanTask("PI_TaskA").isAvailable();
```
<a name="humanTask-isCompleted"/>
#### HumanTask: isCompleted

Assert that a human task is currently 'completed':

```java
assertThat(caseInstance).humanTask("PI_TaskA").isCompleted();
```
<a name="humanTask-isDisabled"/>
#### HumanTask: isDisabled

Assert that a human task is currently 'disabled':

```java
assertThat(caseInstance).humanTask("PI_TaskA").isDisabled();
```
<a name="humanTask-isEnabled"/>
#### HumanTask: isEnabled

Assert that a human task is currently 'enabled':

```java
assertThat(caseInstance).humanTask("PI_TaskA").isEnabled();
```
<a name="humanTask-isTerminated"/>
#### HumanTask: isTerminated

Assert that a human task is currently 'terminated':

```java
assertThat(caseInstance).humanTask("PI_TaskA").isTerminated();
```


## Helpers

<a name="helpers-variables"/>
#### Making assertions on the case variables map of an case execution

You can retrieve a "chained" case variables map assert inspecting all the case variables 
available in the context of a case execution...

```java
assertThat(caseExecution).variables();
```

... in order to directly make assertions on them, e.g. 

```java
assertThat(caseExecution).variables()
  .hasSize(2).containsEntry("approved", true);
```

You may want to compare that with the other possibility to assert whether a case execution 
[hasVariables](#humanTask-hasVariables)/[hasNoVariables](#humanTask-hasNoVariables) (without leaving your current CaseAssert). 


