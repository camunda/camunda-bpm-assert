package org.camunda.bpm.engine.guard;

import static org.camunda.bpm.engine.guard.GuardSupport.checkIsSet;

import java.util.Map;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.guard.ActivityGuard;
import org.camunda.bpm.engine.guard.TaskGuard;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.test.needle.ProcessEngineNeedleRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class GuardBpmnParseListenerTest {

    private static final String BPMN_FILE = "test-process.bpmn";

    private static final String PROCESS_KEY = "test-process";

    @Rule
    public final ProcessEngineNeedleRule processEngine = ProcessEngineNeedleRule.fluentNeedleRule(this).build();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final Map<String, Object> variables = Maps.newHashMap();

    @Test
    @Deployment(resources = BPMN_FILE)
    public void shouldRunWithAutomaticGuards() {
        Mocks.register("serviceTask", new JavaDelegate() {

            @Override
            public void execute(final DelegateExecution execution) throws Exception {
                execution.setVariable("world", Boolean.TRUE);
            }
        });

        variables.put("foo", Boolean.TRUE);

        processEngine.startProcessInstanceByKey(PROCESS_KEY, variables);

        variables.put("bar", Boolean.FALSE);
        variables.put("hello", Boolean.FALSE);
        processEngine.completeTask(variables);
        processEngine.assertNoMoreRunningInstances();
    }

    @Test
    @Deployment(resources = BPMN_FILE)
    public void shouldFailWhenFooIsNotSetOnStart() {
        thrown.expect(ProcessEngineException.class);
        thrown.expectMessage("'foo'");
        processEngine.startProcessInstanceByKey(PROCESS_KEY);
    }

    @Test
    @Deployment(resources = BPMN_FILE)
    public void shouldFailWhenBarIsNotSetInUserTask() {

        thrown.expect(ProcessEngineException.class);
        thrown.expectMessage("'bar'");

        variables.put("foo", Boolean.TRUE);

        processEngine.startProcessInstanceByKey(PROCESS_KEY, variables);
        processEngine.completeTask();
    }

    public static class ExampleTaskGuard extends TaskGuard {

        @Override
        public void checkPostcondions(final DelegateExecution execution) throws IllegalStateException {
            checkIsSet(execution, "bar");
        }

        @Override
        public void checkPreconditions(final DelegateExecution execution) throws IllegalStateException {
            checkIsSet(execution, "foo");
        }
    }

    public static class ExampleActivityGuard extends ActivityGuard {

        @Override
        public void checkPreconditions(final DelegateExecution execution) throws IllegalStateException {
            checkIsSet(execution, "hello");
        }

        @Override
        public void checkPostcondions(final DelegateExecution execution) throws IllegalStateException {
            checkIsSet(execution, "world");
        }

    }
}
