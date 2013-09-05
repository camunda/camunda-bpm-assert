Scenario: Automatic processing fails 

Given the process definition "simple.bpmn"
And the contract is automatically processible
And the contract processing fails
When the process "simple-process" is started 
Then the step "task_process_contract_manually" is reached