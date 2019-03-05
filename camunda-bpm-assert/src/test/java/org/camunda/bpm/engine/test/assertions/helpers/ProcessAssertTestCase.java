package org.camunda.bpm.engine.test.assertions.helpers;


import org.assertj.core.util.Lists;
import org.junit.After;

import static org.assertj.core.api.Assertions.fail;
import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.reset;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public abstract class ProcessAssertTestCase {

  @After
  public void tearDown() {
    reset();
  }
  
  protected void expect(Failure fail) {
    expect(fail, AssertionError.class);
  }

  @SafeVarargs
  protected final void expect(Failure fail, Class<? extends Throwable>... exception) {
    try {
      fail.when();
    } catch (Throwable e) {
      for (int i = 0; i< exception.length; i++) {
        Class<? extends Throwable> t = exception[i];
        if (t.isAssignableFrom(e.getClass())) {
          System.out.println(String.format("caught " + e.getClass().getSimpleName() + " of expected type " + t.getSimpleName() + " with message '%s'", e.getMessage()));
          return;
        }
      }
      throw (RuntimeException) e;
    }
    fail("expected one of " + Lists.newArrayList(exception) + " to be thrown, but did not see any");
  }

}
