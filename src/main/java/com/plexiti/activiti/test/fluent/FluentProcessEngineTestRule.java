package com.plexiti.activiti.test.fluent;

import com.plexiti.activiti.test.fluent.engine.FluentLookups;
import com.plexiti.activiti.test.fluent.mocking.FluentMocks;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentProcessEngineTestRule implements TestRule {

    private Object test;

    public FluentProcessEngineTestRule(Object test) {
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
        FluentLookups.init(test);
        FluentMocks.init(test);
    }

    public void after() {
    }

}
