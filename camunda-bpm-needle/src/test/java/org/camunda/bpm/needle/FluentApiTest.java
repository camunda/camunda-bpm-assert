package org.camunda.bpm.needle;

import static org.camunda.bpm.engine.test.SetVariablesOnDelegateExecutionAnswer.doSetVariablesOnExecute;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.assertThat;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.newProcessInstance;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.processInstance;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

import javax.inject.Inject;
import javax.inject.Named;

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

  private static final String PROCESS_KEY = "test-process";
  private static final String BPMN_FILE = "test-process.bpmn";
  @Rule
  public final ProcessEngineNeedleRule processEngineNeedleRule = ProcessEngineNeedleRule.fluentNeedleRule(this).build();

  @Named
  public static class ServiceTask implements JavaDelegate {

    @Override
    public void execute(final DelegateExecution execution) throws Exception {

    }

  }

  @Inject
  private ServiceTask delegate;

  @Test
  @Deployment(resources = BPMN_FILE)
  public void testName() throws Exception {
    assertNotNull(delegate);

    doSetVariablesOnExecute(delegate, "world", 1L);

    final FluentProcessInstance newProcessInstance = newProcessInstance(PROCESS_KEY).setVariable("foo", Boolean.TRUE).start();
    assertThat(newProcessInstance.task()).isUnassigned();
    final FluentTask taskWait = processInstance().task().claim("admin");
    // assertThat(taskWait).isAssignedTo("admin").hasName("Wait");

    taskWait.complete("bar", Boolean.FALSE, "hello", "jan");
    assertThat(processInstance()).isFinished();
  }

}
