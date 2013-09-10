**NOTE:** This project is part of the [camunda BPM incubation space](https://github.com/camunda/camunda-bpm-testing). 
You can check the presentation of this project [camunda BPM dev list](https://groups.google.com/forum/#!msg/camunda-bpm-dev/m8VDRnZe55A/YsZ2QwnFOPcJ). 
Questions, issues, ideas, feedback, … are greatly appreciated and should be made the [camunda BPM dev list](https://groups.google.com/forum/?fromgroups#!forum/camunda-bpm-dev) list.

**NOTE:** Currently, this project relies on a snapshot version of JBehave-3.9. 

# Introduction

This project aims at improving test creation **and maintenance** when developing process applications based on [the camunda BPM platform](http://camunda.org). 
Specifically, it focuses on the following aspects:

* enable test of process behavior
* foster involvement of the business during test creation
* ease the readability and maintainability of process model tests
* make the creation of process model tests more fluent and, thus more fun!
* make it easy to mock the services available to a process instance

# Libraries

This project provides a tool set for different testing approaches. This starts with generic JUnit test with an in-memory engine and ends with BDD-Style integration tests. 


| Module | Description |
| ----------------| --- |
| **camunda-bpm-testing-core** | foundation and basis for testing with Camunda BPM platform. |
| **camunda-bpm-testing-examples** | shared examples for demonstrating different testing strategies and techniques. |
| **camunda-bpm-jbehave** | is a basis for writing behaviour specifications and test process up-front of implementation and later after integration |
| **camunda-bpm-fluent-engine-api** | provides a [fluent](http://www.martinfowler.com/bliki/FluentInterface.html) Camunda BPM API so you can focus on your process expert's domain knowledge while writing and reading your tests |
| **camunda-bpm-fest-assertions** | provides a [fluent](http://www.martinfowler.com/bliki/FluentInterface.html) convenience methods, matchers and assertions for testing. |
| **camunda-bpm-needle** | is offering an integration to Needle Mocking provider framework, which eases the use of Mocking Frameworks in Camunda tests and helps to test components with Dependency Injection. |

Suggestions, pull requests, ... you name it... are very welcome! Meet us on the [camunda BPM dev list](https://groups.google.com/forum/?fromgroups#!forum/camunda-bpm-dev) list.

* [Project history on ohloh.net](https://www.ohloh.net/p/camunda-bpm-testing)
