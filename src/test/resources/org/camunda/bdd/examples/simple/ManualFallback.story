Scenario: Automatic processing not possible 

Given the process definition "simple.bpmn"
And the contract is not automatically processible
When the process "simple-process" is started 
Then the step "task_process_contract_manually" is reached