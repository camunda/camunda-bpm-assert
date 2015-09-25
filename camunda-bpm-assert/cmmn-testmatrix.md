# CMMN Test Matrix

The following matrixes show which assertions are planned and which of them are already implemented and tested. 

Please note that this is not a replacement for a documentation :)

## ProcessEngineTests

| Stage          | available | enabled | active | completed |
|----------------|-----------|---------|--------|-----------|
| start(id)      |     x     |    x    |    x   |     x     |
| complete(id)   |     x     |    x    |    x   |     x     |
| stage(id)      |     x     |    x    |    x   |     x     |

| Milestone      | created | occurred |
|----------------|---------|----------|
| milestone(id)  |    x    |     x    |

| Task           | available | enabled | active | completed |
|----------------|-----------|---------|--------|-----------|
| start(id)      |     x     |    x    |    x   |     x     |
| complete(id)   |     x     |    x    |    x   |     x     |
| humanTask(id)  |     x     |    x    |    x   |     x     |
| caseTask(id)   |     x     |    x    |    x   |     x     |
| processTask(id)|           |         |        |           |

## ProcessEngineAssertions

| entry points    | Task | Stage | CaseInstance | Milestone |
|-----------------|------|-------|--------------|-----------|
| assertThat(...) |   x  |   x   |              |     x     |
 
## CaseInstanceAssert

| CaseInstance        | available | enabled | active | completed |
|---------------------|-----------|---------|--------|-----------|
| isAvailable()       |           |         |        |           |
| isEnabled()         |           |         |        |           |
| isActive()          |           |         |        |           |
| isCompleted()       |           |         |        |           |

## TaskAssert

| Task                | available | enabled | active | completed |
|---------------------|-----------|---------|--------|-----------|
| isAvailable()       |     x     |    x    |    x   |     x     |
| isEnabled()         |     x     |    x    |    x   |     x     |
| isActive()          |     x     |    x    |    x   |     x     |
| isCompleted()       |     x     |    x    |    x   |     x     |
| hasAssignee()       |           |         |        |           |
| hasCandidateUser()  |           |         |        |           |
| hasCandidateGroup() |           |         |        |           |
| variable(name)      |           |         |        |           |

## StageAssert

| Stage          | available | enabled | active | completed |
|----------------|-----------|---------|--------|-----------|
| isAvailable()  |     x     |    x    |    x   |     x     |
| isEnabled()    |     x     |    x    |    x   |     x     |
| isActive()     |     x     |    x    |    x   |     x     |
| isCompleted()  |     x     |    x    |    x   |     x     |
| humanTask(id)  |           |         |        |           |
| caseTask(id)   |           |         |        |           |
| processTask(id)|           |         |        |           |
| stage(id)      |           |         |        |           |
| variable(name) |           |         |        |           |

## MilestoneAssert

| Milestone  | created | occurred |
|------------|---------|----------|
| hasOccured |    x    |     x    |