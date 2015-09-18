## ProcessEngineTests

| CaseInstance     | active | completed | closed |
|------------------|--------|-----------|--------|
| caseInstance(id) |        |           |        |
  
| Stage          | available | enabled | active | completed |
|----------------|-----------|---------|--------|-----------|
| start(id)      |           |         |        |           |
| complete(id)   |           |         |        |           |
| stage(id)      |           |         |        |           |
| milestone(id)  |           |         |        |           |

| Task           | available | enabled | active | completed |
|----------------|-----------|---------|--------|-----------|
| start(id)      |     x     |    x    |    x   |     x     |
| complete(id)   |     x     |    x    |    x   |     x     |
| humanTask(id)  |     x     |    x    |    x   |     x     |
| caseTask(id)   |           |         |        |           |
| processTask(id)|           |         |        |           |

## ProcessEngineAssertions

| entry points    | Task | Stage | CaseInstance |
|-----------------|------|-------|--------------|
| assertThat(...) |   x  |       |              |
 
## TaskAssert

| Task                | available | enabled | active | completed |
|---------------------|-----------|---------|--------|-----------|
| isAvailable()       |           |         |        |           |
| isEnabled()         |           |         |        |           |
| isActive()          |     x     |    x    |    x   |     x     |
| isCompleted()       |           |         |        |           |
| hasAssignee()       |           |         |        |           |
| hasCandidateUser()  |           |         |        |           |
| hasCandidateGroup() |           |         |        |           |
| variable(name)      |           |         |        |           |

## StageAssert

| Stage          | available | enabled | active | completed |
|----------------|-----------|---------|--------|-----------|
| isAvailable()  |           |         |        |           |
| isEnabled()    |           |         |        |           |
| isActive()     |           |         |        |           |
| isCompleted()  |           |         |        |           |
| humanTask(id)  |           |         |        |           |
| caseTask(id)   |           |         |        |           |
| processTask(id)|           |         |        |           |
| stage(id)      |           |         |        |           |
| variable(name) |           |         |        |           |

## MilestoneAssert

| Milestone     | available | completed |
|---------------|-----------|-----------|
| isAvailable() |           |           |
| isCompleted() |           |           |
