# CMMN Coverage

## AbstractCaseAssert

###  "Status" assertions

(Implementation, systematical tests, javadoc and markdown documentation)

- isAvailable()
- isEnabled()
- isDisabled()
- isActive()
- isSuspended()
- isCompleted()
- isClosed()
- isFailed()

### "Type" assertions

(Implementation, systematical tests, javadoc and markdown documentation)

- isCaseInstance()
- isStage()
- isHumanTask()
- isProcessTask()
- isCaseTask()
- isMilestone()

### "Chained" assert objects

(Implementation, systematical tests, javadoc and markdown documentation)

- caseExecution(query)
- caseExecution(activityId)
- caseExecution()
- humanTask(query)     
- humanTask(activityId)
- humanTask()          
- caseTask(query)     
- caseTask(activityId)
- caseTask()          
- processTask(query)     
- processTask(activityId)
- processTask()          
- stage(query)     
- stage(activityId)
- stage()          
- milestone(query)     
- milestone(activityId)
- milestone()          

### "Variable" assertions

- variables()
- hasVariables(String... names)
- hasNoVariables()

## CaseDefinitionAssert

(Implementation, systematical tests, javadoc and markdown documentation)

## CaseExecutionAssert

### Make inherited methods available public

- all "status" assertions
- all "type" assertions
- all "variable" assertions

## CaseInstanceAssert 

### Make inherited methods available public

- isActive()
- isCompleted()
- isClosed()
- isTerminated()
- isFailed()
- isSuspended()

- all "chained" assert objects
- all "variable" assertions

## CaseTaskAssert

### Make inherited methods available public

- isAvailable()
- isEnabled()
- isDisabled()
- isActive()
- isSuspended()
- isCompleted()
- isFailed()
- isTerminated()

- all "variable" assertions

## HumanTaskAssert

### Make inherited methods available public

- :white_check_mark: isAvailable()
- :white_check_mark: isEnabled()
- :white_check_mark: isDisabled()
- :white_check_mark: isActive()
- isSuspended()
- :white_check_mark: isCompleted()
- isFailed()
- :white_check_mark: isTerminated()

- :white_check_mark: all "variable" assertions

### "HumanTask" specific assertions

(Implementation, systematical tests, javadoc and markdown documentation)

- hasAssignee(userId)
- hasCandidateUser(userId)
- hasCandidateUserAssociated(userId)
- hasCandidateGroup(groupId)
- hasCandidateGroupAssociated(groupId)

## MilestoneAssert

- isAvailable()
- isCompleted()
- isTerminated()

:white_check_mark:

## ProcessTaskAssert

- isAvailable()
- isEnabled()
- isDisabled()
- isActive()
- isSuspended()
- isCompleted()
- isFailed()
- isTerminated()

- all "variable" assertions

:white_check_mark:

## StageAssert

### Make inherited methods available public

- isAvailable()
- isEnabled()
- isDisabled()
- isActive()
- isSuspended()
- isCompleted()
- isFailed()
- isTerminated()

- all "variable" assertions

## CmmnAwareAssertions

(Implementation, systematical tests, javadoc and markdown documentation)

- assertThat(caseExecution)
- assertThat(caseDefinition)

## CmmnAwareTests

(Implementation, systematical tests, javadoc and markdown documentation)

:white_check_mark:

### "Service" retrievers

(Implementation, systematical tests, javadoc and markdown documentation)

- caseService()
- caseInstanceQuery()
- caseExecutionQuery()
- caseDefinitionQuery()

### "Status" changers

(Implementation, systematical tests, javadoc and markdown documentation)

- complete(caseExecution)
- disable(caseExecution)
- manuallyStart(caseExecution)

### "CaseExecution" finders

(Implementation, systematical tests, javadoc and markdown documentation)

- caseExecution()
- caseExecution(activityId)
- caseExecution(query)
- caseExecution(parent)
- caseExecution(activityId, parent)
- caseExecution(query, parent)
- descendantCaseExecution(query)