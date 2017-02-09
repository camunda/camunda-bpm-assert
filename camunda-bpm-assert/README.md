# ![camunda logo](http://camunda.github.io/camunda-bpm-assert/resources/images/camunda.png)&nbsp;camunda-bpm-assert User Guide

This little community-extension to [camunda BPM](http://camunda.org) wants to make it easier to write tests for BPMN process definitions executed with the camunda process engine. 
For that reason, a set of **assertions** based on Joel Costigliola's [AssertJ](https://github.com/joel-costigliola/assertj-core) rich assertions library for java are provided as well as a few little **helpers** to make it a bit easier to drive you through your process.

[Getting started](../README.md#getting-started)

### Table of Contents
  
 * [User Guide for BPMN](./User_Guide_BPMN.md)
  
 * [User Guide for CMMN](./User_Guide_CMMN.md)
  
 * [Advanced Topics](#advanced)
   * [Add camunda-bpm-assert to Eclipse](#add-to-eclipse)
   * [Using a non-default process engine](#non-default-engine)

<a name="advanced"/>
## Advanced topics

<a name="#add-to-eclipse"/>
#### Add camunda-bpm-assert to Eclipse

Eclipse will remove the static import, when it organizes the imports. Thus it is recommended to add camunda-bpm-assert to your 'Content Assist Favorites'.In Eclipse go to Window -> Preferences -> Java -> Editor -> Content Assist -> Favorites -> New Type... and add the following type:

```java
org.camunda.bpm.engine.test.assertions.ProcessEngineTests
```

<a name="non-default-engine"/>
#### Using a non-default process engine
 
In case you want to test with a process engine, which is neither the default engine,
nor the only one ramped up, you can bind a specific process engine to your testing 
thread by means of the following initialisation code. This makes the assertions aware 
of your process engine, e.g.:
 
```java  
   @Before
   public void setUp() {
     init(processEngineRule.getProcessEngine());
   }
```
