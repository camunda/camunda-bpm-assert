# Camunda Platform Assert User Guide for CMMN

### Table of Contents

 * [Assertions](#assertions)
   * for case instances: [assertThat](#assertThat-caseInstance)
   * for case executions: [assertThat](#assertThat-caseExecution)
   * for case definitions: [assertThat](#assertThat-caseDefinition)
   * for human tasks: [isActive](#humanTask-isActive), [isAvailable](#humanTask-isAvailable), [isCompleted](#humanTask-isCompleted), [isDisabled](#humanTask-isDisabled), [isEnabled](#humanTask-isEnabled), [isTerminated](#humanTask-isTerminated), [hasVariables](#humanTask-hasVariables), [hasNoVariables](#humanTask-hasNoVariables)
   * for milestones: [isAvailable](#milestone-isAvailable), [isCompleted](#milestone-isCompleted), [isTerminated](#milestone-isTerminated)
   * for process tasks: [isActive](#processTask-isActive), [isAvailable](#processTask-isAvailable), [isCompleted](#processTask-isCompleted), [isDisabled](#processTask-isDisabled), [isEnabled](#processTask-isEnabled), [isTerminated](#processTask-isTerminated), [hasVariables](#processTask-hasVariables), [hasNoVariables](#processTask-hasNoVariables)
   * for case tasks: [isActive](#caseTask-isActive), [isAvailable](#caseTask-isAvailable), [isCompleted](#caseTask-isCompleted), [isDisabled](#caseTask-isDisabled), [isEnabled](#caseTask-isEnabled), [isTerminated](#caseTask-isTerminated), [hasVariables](#caseTask-hasVariables), [hasNoVariables](#caseTask-hasNoVariables)
   * for stages: [isActive](#stage-isActive), [isAvailable](#stage-isAvailable), [isCompleted](#stage-isCompleted), [isDisabled](#stage-isDisabled), [isEnabled](#stage-isEnabled), [isTerminated](#stage-isTerminated)
 
 * [Helpers](#helpers)
   * retrieve CaseService
     * [caseService()](#caseservice)
   * lookup things
     * [caseInstanceQuery()](#caseinstancequery)
     * [caseExecutionQuery()](#caseexecutionquery)
     * [caseDefinitionQuery()](#casedefinitionquery)
	 * [caseExecution()](#caseexecutionstring-caseinstance)
     * [caseExecution()](#caseexecutioncaseexecutionquery-caseinstance)
   * manage lifecycles
     * [complete()](#completecaseexecution)
     * [complete(caseExecution, variables)](#helpers-complete-variables)
     * [disable()](#disablecaseexecution)
     * [manuallyStart()](#manuallystartcaseexecution)
   * lookup variables
     * [Making assertions on the case variables map of an instance](#helpers-variables)

## Assertions

<a name="assertThat-caseInstance"></a>
#### assertThat(CaseInstance)

Entrypoint to CaseInstance assertions.

    assertThat(caseInstance)

Following this statement, you can fluently continue with your assertions, e.g.

    assertThat(caseInstance).isNotNull();

<a name="assertThat-caseExecution"></a>
#### assertThat(CaseDefinition)

Entrypoint to CaseDefinition assertions.

    assertThat(caseDefinition)

Following this statement, you can fluently continue with your assertions, e.g.

    assertThat(caseDefinition).isNotNull();

<a name="assertThat-caseDefinition"></a>
#### assertThat(CaseExecution)

Entrypoint to CaseExecution assertions.

    assertThat(caseExecution)

Following this statement, you can fluently continue with your assertions, e.g.

    assertThat(caseExecution).isNotNull();

<a name="humanTask-hasVariables"></a>
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

<a name="humanTask-hasNoVariables"></a>
#### HumanTask: hasNoVariables

Assert that a human task holds no case variables at all:

```java
assertThat(caseInstance).humanTask("PI_TaskA").hasNoVariables();
```

<a name="humanTask-isActive"></a>
#### HumanTask: isActive

Assert that a human task is currently 'active':

```java
assertThat(caseInstance).humanTask("PI_TaskA").isActive();
```
<a name="humanTask-isAvailable"></a>
#### HumanTask: isAvailable

Assert that a human task is currently 'available':

```java
assertThat(caseInstance).humanTask("PI_TaskA").isAvailable();
```
<a name="humanTask-isCompleted"></a>
#### HumanTask: isCompleted

Assert that a human task is currently 'completed':

```java
assertThat(caseInstance).humanTask("PI_TaskA").isCompleted();
```
<a name="humanTask-isDisabled"></a>
#### HumanTask: isDisabled

Assert that a human task is currently 'disabled':

```java
assertThat(caseInstance).humanTask("PI_TaskA").isDisabled();
```
<a name="humanTask-isEnabled"></a>
#### HumanTask: isEnabled

Assert that a human task is currently 'enabled':

```java
assertThat(caseInstance).humanTask("PI_TaskA").isEnabled();
```
<a name="humanTask-isTerminated"></a>
#### HumanTask: isTerminated

Assert that a human task is currently 'terminated':

```java
assertThat(caseInstance).humanTask("PI_TaskA").isTerminated();
```
<a name="milestone-isAvailable"></a>
#### Milestone: isAvailable

Assert that a milestone is currently 'available':

```java
assertThat(caseInstance).milestone("M_ImportantState").isAvailable();
```
<a name="milestone-isCompleted"></a>
#### Milestone: isCompleted

Assert that a milestone is currently 'completed':

```java
assertThat(caseInstance).milestone("M_ImportantState").isCompleted();
```

N.B.: A milestone that is completed (= has reached the Completed state) is sometimes also said to _have occurred_.
This is because the transition leading to the Completed state is labelled _occur_. 
<a name="milestone-isTerminated"></a>
#### Milestone: isTerminated

Assert that a milestone is currently 'terminated':

```java
assertThat(caseInstance).milestone("M_ImportantState").isTerminated();
```

<a name="processTask-hasVariables"></a>
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

<a name="processTask-hasNoVariables"></a>
#### ProcessTask: hasNoVariables

Assert that a process task holds no case variables at all:

```java
assertThat(caseInstance).processTask("PI_TaskA").hasNoVariables();
```

<a name="processTask-isActive"></a>
#### ProcessTask: isActive

Assert that a process task is currently 'active':

```java
assertThat(caseInstance).processTask("PI_TaskA").isActive();
```
<a name="processTask-isAvailable"></a>
#### ProcessTask: isAvailable

Assert that a process task is currently 'available':

```java
assertThat(caseInstance).processTask("PI_TaskA").isAvailable();
```
<a name="processTask-isCompleted"></a>
#### ProcessTask: isCompleted

Assert that a process task is currently 'completed':

```java
assertThat(caseInstance).processTask("PI_TaskA").isCompleted();
```
<a name="processTask-isDisabled"></a>
#### ProcessTask: isDisabled

Assert that a process task is currently 'disabled':

```java
assertThat(caseInstance).processTask("PI_TaskA").isDisabled();
```
<a name="processTask-isEnabled"></a>
#### ProcessTask: isEnabled

Assert that a process task is currently 'enabled':

```java
assertThat(caseInstance).processTask("PI_TaskA").isEnabled();
```
<a name="processTask-isTerminated"></a>
#### ProcessTask: isTerminated

Assert that a process task is currently 'terminated':

```java
assertThat(caseInstance).processTask("PI_TaskA").isTerminated();
```

<a name="caseTask-hasVariables"></a>
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

<a name="caseTask-hasNoVariables"></a> 
#### CaseTask: hasNoVariables

Assert that a case task holds no case variables at all:

```java
assertThat(caseInstance).caseTask("PI_TaskA").hasNoVariables();
```

<a name="caseTask-isActive"></a>
#### CaseTask: isActive

Assert that a case task is currently 'active':

```java
assertThat(caseInstance).caseTask("PI_TaskA").isActive();
```
<a name="caseTask-isAvailable"></a>
#### CaseTask: isAvailable

Assert that a case task is currently 'available':

```java
assertThat(caseInstance).caseTask("PI_TaskA").isAvailable();
```
<a name="caseTask-isCompleted"></a>
#### CaseTask: isCompleted

Assert that a case task is currently 'completed':

```java
assertThat(caseInstance).caseTask("PI_TaskA").isCompleted();
```
<a name="caseTask-isDisabled"></a>
#### CaseTask: isDisabled

Assert that a case task is currently 'disabled':

```java
assertThat(caseInstance).caseTask("PI_TaskA").isDisabled();
```
<a name="caseTask-isEnabled"></a>
#### CaseTask: isEnabled

Assert that a case task is currently 'enabled':

```java
assertThat(caseInstance).caseTask("PI_TaskA").isEnabled();
```
<a name="caseTask-isTerminated"></a>
#### CaseTask: isTerminated

Assert that a case task is currently 'terminated':

```java
assertThat(caseInstance).caseTask("PI_TaskA").isTerminated();
```

<a name="stage-isActive"></a>
#### Stage: isActive

Assert that a stage is currently 'active':

```java
assertThat(caseInstance).stage("PI_StageA").isActive();
```
<a name="stage-isAvailable"></a>
#### Stage: isAvailable

Assert that a stage is currently 'available':

```java
assertThat(caseInstance).stage("PI_StageA").isAvailable();
```
<a name="stage-isCompleted"></a>
#### Stage: isCompleted

Assert that a stage is currently 'completed':

```java
assertThat(caseInstance).stage("PI_StageA").isCompleted();
```
<a name="stage-isDisabled"></a>
#### Stage: isDisabled

Assert that a stage is currently 'disabled':

```java
assertThat(caseInstance).stage("PI_StageA").isDisabled();
```
<a name="stage-isEnabled"></a>
#### Stage: isEnabled

Assert that a stage is currently 'enabled':

```java
assertThat(caseInstance).stage("PI_StageA").isEnabled();
```
<a name="stage-isTerminated"></a>
#### Stage: isTerminated

Assert that a stage is currently 'terminated':

```java
assertThat(caseInstance).stage("PI_StageA").isTerminated();
```

## Helpers

#### caseService()

Shortcut for retrieving the process engine's CaseService. 

    caseService()
  
  
#### caseInstanceQuery()

Helper method to easily create a new CaseInstanceQuery. The query is naked and will return all available CaseInstances. 
Please note that, in a test context, there is usually only one CaseInstance. Exceptions are sub-cases started via case tasks.

    caseInstanceQuery()

Can be used with [assertThat(CaseInstance)](#assertThat-caseInstance), e.g,
 
    assertThat(caseInstanceQuery().caseInstanceBusinessKey("foo").singleResult())
    
    
#### caseExecutionQuery()

Helper method to easily create a new CaseExecutionQuery. The query is naked and will return all available CaseExecutions. 

    caseExecutionQuery()

Can be used with [assertThat(CaseExecution)](#assertThat-caseExecution), e.g.

    assertThat(caseExecutionQuery().activityId("foo").singleResult())


#### caseDefinitionQuery()

Helper method to easily create a new CaseDefinitionQuery.

    caseDefinitionQuery()
    
Can be used with [assertThat(CaseDefinition)](#assertThat-caseDefinition), e.g.

    assertThat(caseDefinitionQuery().caseDefinitionKey("foo").singleResult())
    

#### caseExecution(String, CaseInstance)

Helper method to find any CaseExecution in the context of a CaseInstance.
A CaseExecution is used very often. Its activityId is the same as its planItem ID from the CMMN file.

    caseExecution(activityId, caseInstance)
    
You can further work with the found CaseExecution by passing it as argument to other functions, e.g.:   

    complete(caseExecution("foo", caseInstance))


#### caseExecution(CaseExecutionQuery, CaseInstance)

Helper method to find any CaseExecution in the context of a CaseInstance.

    caseExecution(caseExecutionQuery, caseInstance)
    
You can further work with the found CaseExecution by passing it as argument to other functions, e.g.:   

    complete(caseExecution(caseExecutionQuery, caseInstance))
    
You can also easily combine it with [caseExecutionQuery()](#caseexecutionquery), e.g.:
    
    caseExecution(caseExecutionQuery().variableValueEquals("foo","bar"), caseInstance)
	
	
#### complete(CaseExecution)
    
Helper method to easily complete a caseExecution.

    complete(caseExecution)    
    
	
<a name="helpers-complete-variables"></a>
#### complete(caseExecution, variables)

You can directly construct a map of case variables by passing a sequence 
of key/value pairs to the static helper method "withVariables":

```java
Map<String, Object> variables = withVariables("documentId", 5, "approved", true); 
```

You can therefore e.g. write

```java
complete(caseExecution, withVariables("documentId", 5, "approved", true)); 
```


#### disable(CaseExecution)

Helper method to easily disable a case execution.

    disable(caseExecution)


#### manuallyStart(CaseExecution)

Helper method to easily manually start a case execution.

    manuallyStart(caseExecution)


<a name="helpers-variables"></a>
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
