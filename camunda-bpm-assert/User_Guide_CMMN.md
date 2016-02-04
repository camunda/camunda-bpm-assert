# ![camunda logo](http://camunda.github.io/camunda-bpm-assert/resources/images/camunda.png)&nbsp;camunda-bpm-assert User Guide for CMMN

 * [Assertions](#assertions)
   * for case instances: [assertThat](#assertthat-caseinstance)
   * for case executions: [assertThat](#assertthat-caseexecution)
   * for case definitions: [assertThat](#assertthat-casedefinition)
 
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
