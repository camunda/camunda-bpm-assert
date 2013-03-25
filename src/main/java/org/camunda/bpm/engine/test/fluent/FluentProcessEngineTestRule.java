package org.camunda.bpm.engine.test.fluent;

import org.camunda.bpm.engine.fluent.FluentLookups;
import org.camunda.bpm.engine.test.fluent.mocking.FluentMocks;
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
        FluentLookups.before(test);
        FluentMocks.before(test);
    }

    public void after() {
        FluentMocks.after(test);
        FluentLookups.after(test);
    }

}
