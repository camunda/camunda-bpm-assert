package com.plexiti.activiti.test.fluent;

import com.plexiti.activiti.test.fluent.engine.TestLookups;
import com.plexiti.activiti.test.fluent.mocking.TestMocks;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class ActivitiFluentTestRule implements TestRule {

    private Object test;

    public ActivitiFluentTestRule(Object test) {
        this.test = test;
    }

    @Override
    public Statement apply(final Statement statement, Description description) {

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before();
                try {
                    statement.evaluate();
                } finally {
                    after();
                }
            }
        };

    }

    public void before() {
        TestLookups.init(test);
        TestMocks.init(test);
    }

    public void after() {
    }

}
