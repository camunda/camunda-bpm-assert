package org.camunda.bpm.needle;

import static org.camunda.bpm.engine.test.SetVariablesOnDelegateExecutionAnswer.doSetVariablesOnExecute;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.assertThat;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.newProcessInstance;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.processInstance;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

import javax.inject.Inject;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.fluent.FluentProcessInstance;
import org.camunda.bpm.engine.fluent.FluentTask;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.test.needle.ProcessEngineNeedleRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class FluentApiTest {

  @Rule
  public final ProcessEngineNeedleRule processEngineNeedleRule = ProcessEngineNeedleRule.fluentNeedleRule(this).build();

  @Inject
  private JavaDelegate delegate;

  @Test
  @Deployment(resources = "test-process.bpmn")
  public void testName() throws Exception {

    assertNotNull(delegate);
    Mocks.register("serviceTask", delegate);

    doSetVariablesOnExecute(delegate, "world", 1L);

    final FluentProcessInstance newProcessInstance = newProcessInstance("test-process").setVariable("foo", Boolean.TRUE).start();
    assertThat(newProcessInstance.task()).isUnassigned();
    final FluentTask taskWait = processInstance().task().claim("admin");
    // assertThat(taskWait).isAssignedTo("admin").hasName("Wait");

    taskWait.complete("bar", Boolean.FALSE, "hello", "jan");
    assertThat(processInstance()).isFinished();
  }

}
