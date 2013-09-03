package org.camunda.bpm.needle;

import static org.junit.Assert.*;

import org.camunda.bpm.engine.test.needle.ProcessEngineNeedleRule;
import org.junit.Test;

public class ProcessEngineNeedleRuleTestGetSingleTask {

    private final ProcessEngineNeedleRule rule = ProcessEngineNeedleRule.fluentNeedleRule(this).build();

    @Test(expected = IllegalStateException.class)
    public void shouldFailGetOnlyTask() {
        assertNotNull(rule.getTaskService());
        assertNotNull(rule.getTaskService().createTaskQuery());

        rule.getTask();
    }

}
