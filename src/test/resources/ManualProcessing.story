Scenario: Manual processing

GivenStories: ManualFallback.story
When the contract is processed manually
Then the process is finished with event event_contract_processed

Scenario: Manual processing after automatic failure

GivenStories: ManualFallback2.story
When the contract is processed manually
Then the process is finished with event event_contract_processed


Scenario: Manual processing with errors

GivenStories: ManualFallback.story
When the contract is processed manually with errors
Then the contract processing is cancelled
And the process is finished with event event_processing_cancelled

Scenario: Manual processing after automatic failure with errors

GivenStories: ManualFallback2.story
When the contract is processed manually with errors
Then the contract processing is cancelled
And the process is finished with event event_processing_cancelled
