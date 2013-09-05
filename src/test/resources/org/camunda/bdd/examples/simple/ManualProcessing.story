Scenario: Manual processing

GivenStories: org/camunda/bdd/examples/simple/ManualFallback.story
When contract is processed manually
Then the process is finished with event event_contract_processed

Scenario: Manual processing after automatic failure

GivenStories: org/camunda/bdd/examples/simple/ManualFallback2.story
When contract is processed manually
Then the process is finished with event event_contract_processed


Scenario: Manual processing with errors

GivenStories: org/camunda/bdd/examples/simple/ManualFallback.story
When contract is processed manually with errors
Then contract processing is cancelled
And the process is finished with event event_processing_cancelled

Scenario: Manual processing after automatic failure with errors

GivenStories: org/camunda/bdd/examples/simple/ManualFallback2.story
When contract is processed manually with errors
Then contract processing is cancelled
And the process is finished with event event_processing_cancelled
