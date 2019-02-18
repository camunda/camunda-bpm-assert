package org.camunda.bpm.engine.test.assertions.helpers;


import org.assertj.core.util.Lists;

import static org.assertj.core.api.Assertions.fail;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public abstract class ProcessAssertTestCase {

  protected void expect(Failure fail) {
    expect(fail, AssertionError.class);
  }

  protected void expect(Failure fail, Class<? extends Throwable>... exception) {
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
