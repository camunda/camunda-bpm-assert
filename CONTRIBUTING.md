# Contributing Guide

First of all, please have a look at our general [contribution guide](https://github.com/camunda/camunda-bpm-platform/blob/master/CONTRIBUTING.md) for how to contribute to this repository.

When creating an issue, please add "assert" as a component, so that we can easily track all the issues related to this project.

Before v. 3.0.0, the issues were tracked in GitHub: https://github.com/camunda/camunda-bpm-assert/issues. They are now preserved for the reference. However, please, DON'T create new issues here any more.

## Creating pull requests

We use pull requests for feature discussion and bug fixes. If you are not yet familiar on how to create a pull request, [read this great guide](https://gun.io/blog/how-to-github-fork-branch-and-pull-request).

Some things that make it easier for us to accept your pull requests:

We use http://editorconfig.org in this project. If your IDE supports it, you do not have to add any additional configuration.
If you are not able to use this, make sure you use the [eclipse formatter](https://github.com/camunda/camunda-bpm-platform/blob/master/settings/eclipse/formatter.xml).

* The code adheres to our conventions
    * spaces instead of tabs
    * single-quotes
    * ...
    * see the [.editorconfig](https://github.com/camunda/camunda-bpm-assert/blob/master/.editorconfig) file
* The code is tested
* The `mvn clean install` build passes including tests
* The work is combined into a single commit

We'd be glad to assist you if you do not get these things right at first.

## What means DONE?

* Stuff is _implemented_, of course.
    - Stick to project coding conventions. Until these are documented, follow the style of existing code.
    - Use small commits
* Javadoc has been created or changed accordingly
* It is documented in the [README.md](./docs/README.md) - not the top-level one, but the one inside the docs folder
* It is thoroughly tested. 
    'What's _thoroughly_?' you might ask, well, here's the answer:
    - One test class for each method. E.g. if you are going to work on `CaseInstanceAssert.isActive()` you will have a test class `CaseInstanceAssertIsActiveTest` containing all tests for the `isActive()` method.
    - Small test methods for single cases/behaviours. Use speaking names. Don't write a novel.
        * Successful operation of the tested method. E.g. test that `isActive()` does not fail for an _active_ case instance.  
        * Failed operation of the tested method. E.g. a test for `isActive()` on a _completed_ case instance.
        * Corner cases of the tested method as you see fit.
