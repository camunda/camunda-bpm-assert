package org.camunda.bpm.engine.test.assertions.helpers;


import org.assertj.core.util.Lists;
import org.camunda.bpm.engine.test.util.CamundaBpmApi;
import org.junit.Assume;

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

  /*
   * *Assumes* that process engine supports the requested API version. Use method
   * at the beginning of test method implementations which require Camunda BPM API
   * versions higher than Camunda BPM "7.0". Alternatively use it e.g. in @Before 
   * annotated methods for whole test classes. This will cause your test methods to 
   * be IGNORED, when executing it against a Camunda BPM engine older than needed.
   * 
   * @param   api Camunda BPM API version e.g. '7.1', '7.2' etc.
   * @throws  AssumptionViolatedException if process engine does not support the 
   *          requested API version
   */
  protected void assumeApi(String api) {
    Assume.assumeTrue(CamundaBpmApi.supports(api));
  }

}
