package org.camunda.bpm.engine.test.fluent;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.fluent.assertions.JobAssert;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessDefinitionAssert;
import org.camunda.bpm.engine.test.fluent.assertions.ProcessInstanceAssert;
import org.camunda.bpm.engine.test.fluent.assertions.TaskAssert;

import org.fest.assertions.api.Assertions;

/**
 * Class meant to statically access all 
 * camunda BPM Process Engine Assertions. 
 *
 * In your code use import static org.camunda.bpm.engine.test.fluent.ProcessEngineAssertions.*;
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineAssertions extends Assertions {

    private static ThreadLocal<ProcessEngine> processEngine = new ThreadLocal<ProcessEngine>();

    protected ProcessEngineAssertions() {}

    public static ProcessEngine processEngine() {
        ProcessEngine processEngine = ProcessEngineAssertions.processEngine.get();
        if (processEngine != null)
            return processEngine;
        throw new IllegalStateException(String.format("Call %s.init(ProcessEngine processEngine) first!", ProcessEngineAssertions.class.getSimpleName()));
    }

    public static void init(final ProcessEngine processEngine) {
        ProcessEngineAssertions.processEngine.set(processEngine);
    }

    public static void reset() {
        ProcessEngineAssertions.processEngine.remove();
    }

    public static ProcessDefinitionAssert assertThat(final ProcessDefinition actual) {
        return ProcessDefinitionAssert.assertThat(processEngine(), actual);
    }

    public static ProcessInstanceAssert assertThat(final ProcessInstance actual) {
        return ProcessInstanceAssert.assertThat(processEngine(), actual);
    }

    public static TaskAssert assertThat(final Task actual) {
        return TaskAssert.assertThat(processEngine(), actual);
    }

    public static JobAssert assertThat(final Job actual) {
        return JobAssert.assertThat(processEngine(), actual);
    }

}
