package org.camunda.bpm.engine.test.fluent;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.cdi.impl.util.ProgrammaticBeanLookup;
import org.camunda.bpm.engine.fluent.FluentProcessEngine;
import org.camunda.bpm.engine.impl.fluent.FluentProcessEngineImpl;
import org.camunda.bpm.engine.impl.test.TestHelper;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;
import org.camunda.bpm.engine.test.fluent.mocking.FluentMocks;
import org.camunda.bpm.engine.test.fluent.support.Classes;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentProcessEngineTestRule implements TestRule {

  private Object test;
  private FluentProcessEngine engine = null;

  public FluentProcessEngineTestRule(final Object test) {
    this.test = test;
  }

  @Override
  public Statement apply(final Statement statement, final Description description) {

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
    FluentProcessEngineTests.before(getEngine());
    FluentMocks.before(test);
  }

  public void after() {
    FluentMocks.after(test);
    FluentProcessEngineTests.after();
  }

  protected FluentProcessEngine getEngine() {
    if (engine != null) {
      return engine;
    } else {
      try {
        final ProcessEngine processEngine = ProgrammaticBeanLookup.lookup(ProcessEngine.class);
        return new FluentProcessEngineImpl(processEngine);
      } catch (final ProcessEngineException e) {
        // fallthrough
      }
      if (test instanceof ProcessEngineTestCase) {
        return engine = new FluentProcessEngineImpl(TestHelper.getProcessEngine(((ProcessEngineTestCase) test).getConfigurationResource()));
      } else {
        try {
          ProcessEngineRule processEngineRule = (ProcessEngineRule) Classes.getFieldByType(test.getClass(), ProcessEngineRule.class).get(test);
          if (processEngineRule == null) {
            // TODO default config name only
            processEngineRule = new ProcessEngineRule();
          }

          return engine = new FluentProcessEngineImpl(processEngineRule.getProcessEngine());
        } catch (final IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
}
