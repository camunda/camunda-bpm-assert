Scenario: Automatic processing

Given the process definition "simple.bpmn"
And the contract is automatically processible
And the contract processing succeeds
When the process "simple-process" is started 
Then the contract is loaded
And the contract is processed automatically
And the process is finished with event Contract processed

Scenario: Automatic processing not possible 

Given the process definition "simple.bpmn"
And the contract is not automatically processible
When the process "simple-process" is started 
Then the step "task_process_contract_manually" is reached

Scenario: Automatic processing fails 

Given the process definition "simple.bpmn"
And the contract is automatically processible
And the contract processing fails
When the process "simple-process" is started 
Then the step "task_process_contract_manually" is reached
