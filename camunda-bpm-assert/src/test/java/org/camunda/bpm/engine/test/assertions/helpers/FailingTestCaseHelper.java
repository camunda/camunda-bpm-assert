package org.camunda.bpm.engine.test.assertions.helpers;

import org.assertj.core.api.Assertions;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public abstract class FailingTestCaseHelper {

  protected void failure(Check fail) {
    try {
      fail.when();
      throw new RuntimeException();
    } catch (AssertionError e) {
      System.out.println(String.format("caught expected AssertionError with message '%s'", e.getMessage()));
    } catch (RuntimeException e) {
      Assertions.fail("expected an assertion error to be thrown, but did not see any");
    }
  }
  
}
