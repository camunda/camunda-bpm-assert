package org.camunda.bpm.engine.test.mock;

import org.camunda.bpm.engine.test.Expressions;
import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RegisterMockTestRule extends ExternalResource {

  public static final RegisterMockTestRule registerMockTestRule(final Object testInstance) {
    return new RegisterMockTestRule(testInstance);
  }

  private final Object testInstance;

  private RegisterMockTestRule(final Object testInstance) {
    this.testInstance = testInstance;
  }

  @Override
  public Statement apply(final Statement base, final Description description) {
    return new Statement() {

      @Override
      public void evaluate() throws Throwable {
        Expressions.registerMockInstancesForFields(testInstance);
        base.evaluate();
      }
    };
  }

  @Override
  protected void after() {
    Mocks.reset();
  }

}
