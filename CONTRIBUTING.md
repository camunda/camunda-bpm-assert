# Contributing Guide

There are several ways in which you may contribute to this project.

* [File issues](link-to-issue-tracker)
* [Submit a pull requests](#submit-a-pull-request)

## Submit a Pull Request

If you would like to submit a pull request make sure to 

1. [Create an issue](link-to-issue-tracker) for your work. Take care that your piece of work isn't too big. Usually adding one single assertion is big enough. 
1. Fork camunda/camunda-bpm-assert and create a branch for your issue. Don't reuse existing branches. 
1. Implement, document and test (see [What means DONE?](#what-means-done-))
1. If working on CMMN features, mark the method in the [CMMN.md](./CMMN.md) with a :white_check_mark: `:white_check_mark:`
1. Create a pull request

## What means DONE?

* Stuff is _implemented_, of course.
    - Stick to project coding conventions. Until these are documented, follow the style of existing code.
* Javadoc has been created or changed accordingly
* It is documented in the [README.md](./README.md) - not the top-level one, but the one inside the camunda-bpm-assert folder
* It is thoroughly tested. 
    'What's _thoroughly_?' you might ask, well, here's the answer:
    - One test class for each method. E.g. if you are going to work on `CaseInstanceAssert.isActive()` you will have a test class `CaseInstanceAssertIsActiveTest` containing all tests for the `isActive()` method.
    - Small test methods for single cases/behaviours. Use speaking names. Don't write a novel.
        * Successful operation of the tested method. E.g. test that `isActive()` does not fail for an _active_ case instance.  
        * Failed operation of the tested method. E.g. a test for `isActive()` on a _completed_ case instance.
        * Corner cases of the tested method as you see fit.