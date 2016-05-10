# ![camunda logo](http://camunda.github.io/camunda-bpm-assert/resources/images/camunda.png)&nbsp;camunda-bpm-assert User Guide for CMMN

 * [Assertions](#assertions)
   * for case instances: [assertThat](#assertthat-caseinstance)
   * for case executions: [assertThat](#assertthat-caseexecution)
   * for case definitions: [assertThat](#assertthat-casedefinition)
   * for human tasks: [isActive](#humanTask-isActive), [isAvailable](#humanTask-isAvailable), [isCompleted](#humanTask-isCompleted), [isDisabled](#humanTask-isDisabled), [isEnabled](#humanTask-isEnabled), [isTerminated](#humanTask-isTerminated), [hasVariables](#humanTask-hasVariables), [hasNoVariables](#humanTask-hasNoVariables)
   * for milestones: [isAvailable](#milestone-isAvailable), [isCompleted](#milestone-isCompleted), [isTerminated](#milestone-isTerminated)
   * for process tasks: [isActive](#processTask-isActive), [isAvailable](#processTask-isAvailable), [isCompleted](#processTask-isCompleted), [isDisabled](#processTask-isDisabled), [isEnabled](#processTask-isEnabled), [isTerminated](#processTask-isTerminated), [hasVariables](#processTask-hasVariables), [hasNoVariables](#processTask-hasNoVariables)
   * for case tasks: [isActive](#caseTask-isActive), [isAvailable](#caseTask-isAvailable), [isCompleted](#caseTask-isCompleted), [isDisabled](#caseTask-isDisabled), [isEnabled](#caseTask-isEnabled), [isTerminated](#caseTask-isTerminated), [hasVariables](#caseTask-hasVariables), [hasNoVariables](#caseTask-hasNoVariables)
 
 * [Helpers](#helpers)
   * retrieve CaseService
     * [caseService()](#caseService)
   * lookup things
     * [caseExecution()](#caseExecution-String-CaseInstance)
     * [caseExecution()](#caseExecution-CaseExecutionQuery-CaseInstance)
     * [caseInstanceQuery()](#caseInstanceQuery)
     * [caseExecutionQuery()](#caseExecutionQuery)
     * [caseDefinitionQuery()](#caseDefinitionQuery)
   * manage lifecycles
     * [complete()](#complete-CaseExecution)
     * [disable()](#disable-CaseExecution)
     * [manuallyStart()](#manuallyStart-CaseExecution)
   * lookup variables
     * [Making assertions on the case variables map of an instance](#helpers-variables)

## Assertions

### assertThat(CaseInstance)

Entrypoint to CaseInstance assertions.

    assertThat(caseInstance)

Following this statement, you can fluently continue with your assertions, e.g.

    assertThat(caseInstance).isNotNull();


### assertThat(CaseDefinition)

Entrypoint to CaseDefinition assertions.

    assertThat(caseDefinition)

Following this statement, you can fluently continue with your assertions, e.g.

    assertThat(caseDefinition).isNotNull();


### assertThat(CaseExecution)

Entrypoint to CaseExecution assertions.

    assertThat(caseExecution)

Following this statement, you can fluently continue with your assertions, e.g.

    assertThat(caseExecution).isNotNull();

<a name="humanTask-hasVariables"/>
#### HumanTask: hasVariables

Assert that a human task holds at least one case variable:

```java
assertThat(caseInstance).humanTask("PI_TaskA").hasVariables();
```

Assert that a human task holds - aside potential other variables - one or more specified case variables:

```java
assertThat(caseInstance).humanTask("PI_TaskA")
  .hasVariables("approved")
  .hasVariables("jobAnnouncementId", "approved");
```

<a name="humanTask-hasNoVariables"/>
#### HumanTask: hasNoVariables

Assert that a human task holds no case variables at all:

```java
assertThat(caseInstance).humanTask("PI_TaskA").hasNoVariables();
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
<a name="milestone-isAvailable"/>
#### Milestone: isAvailable

Assert that a milestone is currently 'available':

```java
assertThat(caseInstance).milestone("M_ImportantState").isAvailable();
```
<a name="milestone-isCompleted"/>
#### Milestone: isCompleted

Assert that a milestone is currently 'completed':

```java
assertThat(caseInstance).milestone("M_ImportantState").isCompleted();
```

N.B.: A milestone that is completed (= has reached the Completed state) is sometimes also said to _have occurred_.
This is because the transition leading to the Completed state is labelled _occur_. 
<a name="milestone-isTerminated"/>
#### Milestone: isTerminated

Assert that a milestone is currently 'terminated':

```java
assertThat(caseInstance).milestone("M_ImportantState").isTerminated();
```

<a name="processTask-hasVariables"/>
#### ProcessTask: hasVariables

Assert that a process task holds at least one case variable:

```java
assertThat(caseInstance).processTask("PI_TaskA").hasVariables();
```

Assert that a process task holds - aside potential other variables - one or more specified case variables:

```java
assertThat(caseInstance).processTask("PI_TaskA")
  .hasVariables("approved")
  .hasVariables("jobAnnouncementId", "approved");
```

<a name="processTask-hasNoVariables"/>
#### ProcessTask: hasNoVariables

Assert that a process task holds no case variables at all:

```java
assertThat(caseInstance).processTask("PI_TaskA").hasNoVariables();
```

<a name="processTask-isActive"/>
#### ProcessTask: isActive

Assert that a process task is currently 'active':

```java
assertThat(caseInstance).processTask("PI_TaskA").isActive();
```
<a name="processTask-isAvailable"/>
#### ProcessTask: isAvailable

Assert that a process task is currently 'available':

```java
assertThat(caseInstance).processTask("PI_TaskA").isAvailable();
```
<a name="processTask-isCompleted"/>
#### ProcessTask: isCompleted

Assert that a process task is currently 'completed':

```java
assertThat(caseInstance).processTask("PI_TaskA").isCompleted();
```
<a name="processTask-isDisabled"/>
#### ProcessTask: isDisabled

Assert that a process task is currently 'disabled':

```java
assertThat(caseInstance).processTask("PI_TaskA").isDisabled();
```
<a name="processTask-isEnabled"/>
#### ProcessTask: isEnabled

Assert that a process task is currently 'enabled':

```java
assertThat(caseInstance).processTask("PI_TaskA").isEnabled();
```
<a name="processTask-isTerminated"/>
#### ProcessTask: isTerminated

Assert that a process task is currently 'terminated':

```java
assertThat(caseInstance).processTask("PI_TaskA").isTerminated();
```

<a name="caseTask-hasVariables"/>
#### CaseTask: hasVariables

Assert that a case task holds at least one case variable:

```java
assertThat(caseInstance).caseTask("PI_TaskA").hasVariables();
```

Assert that a case task holds - aside potential other variables - one or more specified case variables:

```java
assertThat(caseInstance).caseTask("PI_TaskA")
  .hasVariables("approved")
  .hasVariables("jobAnnouncementId", "approved");
```

<a name="caseTask-hasNoVariables"/> 
#### CaseTask: hasNoVariables

Assert that a case task holds no case variables at all:

```java
assertThat(caseInstance).caseTask("PI_TaskA").hasNoVariables();
```

<a name="caseTask-isActive"/>
#### CaseTask: isActive

Assert that a case task is currently 'active':

```java
assertThat(caseInstance).caseTask("PI_TaskA").isActive();
```
<a name="caseTask-isAvailable"/>
#### CaseTask: isAvailable

Assert that a case task is currently 'available':

```java
assertThat(caseInstance).caseTask("PI_TaskA").isAvailable();
```
<a name="caseTask-isCompleted"/>
#### CaseTask: isCompleted

Assert that a case task is currently 'completed':

```java
assertThat(caseInstance).caseTask("PI_TaskA").isCompleted();
```
<a name="caseTask-isDisabled"/>
#### CaseTask: isDisabled

Assert that a case task is currently 'disabled':

```java
assertThat(caseInstance).caseTask("PI_TaskA").isDisabled();
```
<a name="caseTask-isEnabled"/>
#### CaseTask: isEnabled

Assert that a case task is currently 'enabled':

```java
assertThat(caseInstance).caseTask("PI_TaskA").isEnabled();
```
<a name="caseTask-isTerminated"/>
#### CaseTask: isTerminated

Assert that a case task is currently 'terminated':

```java
assertThat(caseInstance).caseTask("PI_TaskA").isTerminated();
```

## Helpers

### caseService()

Shortcut for retrieving the process engine's CaseService. 

    caseService()
  
  
### caseInstanceQuery()

Helper method to easily create a new CaseInstanceQuery. The query is naked and will return all available CaseInstances. 
Please note that, in a test context, there is usually only one CaseInstance. Exceptions are sub-cases started via case tasks.

    caseInstanceQuery()

Can be used with [assertThat(CaseInstance)](#assertthat-caseinstance), e.g,
 
    assertThat(caseInstanceQuery().caseInstanceBusinessKey("foo").singleResult())
    
    
### caseExecutionQuery()

Helper method to easily create a new CaseExecutionQuery. The query is naked and will return all available CaseExecutions. 

    caseExecutionQuery()

Can be used with [assertThat(CaseExecution)](#assertthat-caseexecution), e.g.

    assertThat(caseExecutionQuery().activityId("foo").singleResult())


### caseDefinitionQuery()

Helper method to easily create a new CaseDefinitionQuery.

    caseDefinitionQuery()
    
Can be used with [assertThat(CaseDefinition)](#assertthat-casedefinition), e.g.

    assertThat(caseDefinitionQuery().caseDefinitionKey("foo").singleResult())
    

### complete(CaseExecution)
    
Helper method to easily complete a caseExecution.

    complete(caseExecution)    
    

### disable(CaseExecution)

Helper method to easily disable a case execution.

    disable(caseExecution)


### manuallyStart(CaseExecution)

Helper method to easily manually start a case execution.

    manuallyStart(caseExecution)


### caseExecution(String, CaseInstance)

Helper method to find any CaseExecution in the context of a CaseInstance.
A CaseExecution is used very often. Its activityId is the same as its planItem ID from the CMMN file.

    caseExecution(activityId, caseInstance)
    
You can further work with the found CaseExecution by passing it as argument to other functions, e.g.:   

    complete(caseExecution("foo", caseInstance))


### caseExecution(CaseExecutionQuery, CaseInstance)

Helper method to find any CaseExecution in the context of a CaseInstance.

    caseExecution(caseExecutionQuery, caseInstance)
    
You can further work with the found CaseExecution by passing it as argument to other functions, e.g.:   

    complete(caseExecution(caseExecutionQuery, caseInstance))
    
You can also easily combine it with [caseExecutionQuery()](#caseexecutionquery), e.g.:
    
    caseExecution(caseExecutionQuery().variableValueEquals("foo","bar"), caseInstance)

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

 * of type HumanTask [hasVariables](#humanTask-hasVariables)/[hasNoVariables](#humanTask-hasNoVariables) 
 * of type CaseTask [hasVariables](#caseTask-hasVariables)/[hasNoVariables](#caseTask-hasNoVariables)

(without leaving your current CaseAssert).