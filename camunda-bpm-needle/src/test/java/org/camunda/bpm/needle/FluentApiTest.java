package org.camunda.bpm.needle;

import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.assertThat;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.newProcessInstance;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.processInstance;

import org.camunda.bpm.engine.fluent.FluentProcessInstance;
import org.camunda.bpm.engine.fluent.FluentTask;
import org.camunda.bpm.engine.test.DelegateExpressions;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.needle.ProcessEngineNeedleRule;
import org.junit.Rule;
import org.junit.Test;

public class FluentApiTest {

  private static final String PROCESS_KEY = "test-process";
  private static final String BPMN_FILE = "test-process.bpmn";
  @Rule
  public final ProcessEngineNeedleRule processEngineNeedleRule = ProcessEngineNeedleRule.fluentNeedleRule(this).build();

  @Test
  @Deployment(resources = BPMN_FILE)
  public void shouldStartAndCompleteProcessInstance() throws Exception {

    DelegateExpressions.registerJavaDelegateMock("serviceTask").onExecutionSetVariables("world", 1L);

    final FluentProcessInstance newProcessInstance = newProcessInstance(PROCESS_KEY).setVariable("foo", Boolean.TRUE).start();
    assertThat(newProcessInstance.task()).isUnassigned();
    final FluentTask taskWait = processInstance().task().claim("admin");

    taskWait.complete("bar", Boolean.FALSE, "hello", "jan");
    assertThat(processInstance()).isFinished();
  }

}
