Scenario: Automatic processing

Given the process definition simple.bpmn
And the contract is automatically processible
And the contract processing succeeds
When the process simple-process is started
Then the contract is loaded
And the contract is processed automatically
And the process is finished with event event_contract_processed