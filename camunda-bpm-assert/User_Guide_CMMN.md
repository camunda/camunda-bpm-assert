# ![camunda logo](http://camunda.github.io/camunda-bpm-assert/resources/images/camunda.png)&nbsp;camunda-bpm-assert User Guide for CMMN

 * [Assertions](#assertions)
    * for human tasks: [isActive](#humanTask-isActive), [isAvailable](#humanTask-isAvailable), [isCompleted](#humanTask-isCompleted), [isDisabled](#humanTask-isDisabled), [isEnabled](#humanTask-isEnabled), [isTerminated](#humanTask-isTerminated)
 
 * [Helpers](#helpers)

## Assertions

<a name="humanTask-isActive"/>
#### HumanTask: isActive

Assert that a human task is currently 'active':

```java
assertThat(caseInstance).humanTask(""PI_TaskA"").isActive();
```
<a name="humanTask-isAvailable"/>
#### HumanTask: isAvailable

Assert that a human task is currently 'available':

```java
assertThat(caseInstance).humanTask(""PI_TaskA"").isAvailable();
```
<a name="humanTask-isCompleted"/>
#### HumanTask: isCompleted

Assert that a human task is currently 'completed':

```java
assertThat(caseInstance).humanTask(""PI_TaskA"").isCompleted();
```
<a name="humanTask-isDisabled"/>
#### HumanTask: isDisabled

Assert that a human task is currently 'disabled':

```java
assertThat(caseInstance).humanTask(""PI_TaskA"").isDisabled();
```
<a name="humanTask-isEnabled"/>
#### HumanTask: isEnabled

Assert that a human task is currently 'enabled':

```java
assertThat(caseInstance).humanTask(""PI_TaskA"").isEnabled();
```
<a name="humanTask-isTerminated"/>
#### HumanTask: isTerminated

Assert that a human task is currently 'terminated':

```java
assertThat(caseInstance).humanTask(""PI_TaskA"").isTerminated();
```


## Helpers

tbd


