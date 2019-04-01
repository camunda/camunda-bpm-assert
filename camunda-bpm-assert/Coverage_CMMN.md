# CMMN Coverage

## AbstractCaseAssert

###  "Status" assertions

(Implementation, systematical tests, javadoc and markdown documentation)

- isAvailable()
- isEnabled()
- isDisabled()
- isActive()
- isCompleted()
- isClosed()
- isTerminated()

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
- humanTask(query)     
- humanTask(activityId)
- caseTask(query)     
- caseTask(activityId)
- processTask(query)     
- processTask(activityId)
- stage(query)     
- stage(activityId)
- milestone(query)     
- milestone(activityId)

### "Variable" assertions

- variables()
- hasVariables(String... names)
- hasNoVariables()

## CaseDefinitionAssert

(Implementation, systematical tests, javadoc and markdown documentation)

## CaseExecutionAssert

### Make inherited methods available public

- :white_check_mark: isAvailable()
- :white_check_mark: isEnabled()
- :white_check_mark: isDisabled()
- :white_check_mark: isActive()
- :white_check_mark: isCompleted()
- :white_check_mark: isTerminated()

- :white_check_mark: all "type" assertions
- all "variable" assertions

## CaseInstanceAssert 

### Make inherited methods available public

- :white_check_mark: isActive()
- :white_check_mark: isCompleted()
- :white_check_mark: isClosed()
- :white_check_mark: isTerminated()

- :white_check_mark: all "chained" assert objects
- all "variable" assertions

## CaseTaskAssert

### Make inherited methods available public

- :white_check_mark: isAvailable()
- :white_check_mark: isEnabled()
- :white_check_mark: isDisabled()
- :white_check_mark: isActive()
- :white_check_mark: isCompleted()
- :white_check_mark: isTerminated()

- :white_check_mark: all "variable" assertions

## HumanTaskAssert

### Make inherited methods available public

- :white_check_mark: isAvailable()
- :white_check_mark: isEnabled()
- :white_check_mark: isDisabled()
- :white_check_mark: isActive()
- :white_check_mark: isCompleted()
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

- :white_check_mark: isAvailable()
- :white_check_mark: isCompleted()
- :white_check_mark: isTerminated()

## ProcessTaskAssert

- :white_check_mark: isAvailable()
- :white_check_mark: isEnabled()
- :white_check_mark: isDisabled()
- :white_check_mark: isActive()
- :white_check_mark: isCompleted()
- :white_check_mark: isTerminated()

- :white_check_mark: all "variable" assertions

## StageAssert

### Make inherited methods available public

- :white_check_mark: isAvailable()
- :white_check_mark: isEnabled()
- :white_check_mark: isDisabled()
- :white_check_mark: isActive()
- :white_check_mark: isCompleted()
- :white_check_mark: isTerminated()

- :white_check_mark: all "chained" assert objects

- all "variable" assertions

## CmmnAwareTests

(Implementation, systematical tests, javadoc and markdown documentation)

- :white_check_mark: assertThat(caseInstance)
- :white_check_mark: assertThat(caseExecution)
- :white_check_mark: assertThat(caseDefinition)

### "Service" retrievers

(Implementation, systematical tests, javadoc and markdown documentation)

- :white_check_mark: caseService()
- :white_check_mark: caseInstanceQuery()
- :white_check_mark: caseExecutionQuery()
- :white_check_mark: caseDefinitionQuery()

### "Status" changers

(Implementation, systematical tests, javadoc and markdown documentation)

- :white_check_mark: complete(caseExecution)
- :white_check_mark: complete(caseExecution, variables)
- :white_check_mark: disable(caseExecution)
- :white_check_mark: manuallyStart(caseExecution)

### "CaseExecution" finders

(Implementation, systematical tests, javadoc and markdown documentation)

- caseExecution(activityId)
- caseExecution(query)
- :white_check_mark: caseExecution(activityId, parent)
- :white_check_mark: caseExecution(query, parent)
- descendantCaseExecution(query)

### static helper methods

- :white_check_mark: withVariables(key, value, furtherKeyValuePairs)
