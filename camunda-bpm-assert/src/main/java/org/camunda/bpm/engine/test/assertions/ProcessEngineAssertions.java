package org.camunda.bpm.engine.test.assertions;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.test.assertions.bpmn.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Martin Günther <martin.guenter@holisticon.de>
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 *   
 * @deprecated Use org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests
 *              or org.camunda.bpm.engine.test.assertions.cmmn.ProcessEngineTests
 */
@Deprecated
public class ProcessEngineAssertions extends Assertions {
  
  protected ProcessEngineAssertions() {
  }

  /**
   * Assert that... the given Job meets your expectations.
   *
   * @param   actual Job under test
   * @return Assert object offering Job specific assertions.
   */
  public static JobAssert assertThat(Job actual) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.assertThat(actual);
  }

  /**
   * Retrieve the processEngine bound to the current testing thread
   * via calling init(ProcessEngine processEngine). In case no such
   * processEngine is bound yet, init(processEngine) is called with
   * a default process engine.
   *
   * @return processEngine bound to the current testing thread
   * @throws IllegalStateException in case a processEngine has not
   *          been initialised yet and cannot be initialised with a 
   *          default engine.
   */
  public static ProcessEngine processEngine() {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.processEngine();
  }

  /**
   * Bind an instance of ProcessEngine to the current testing calls done
   * in your test method.
   *
   * @param   processEngine ProcessEngine which should be bound to the
   *          current testing thread.
   */
  public static void init(ProcessEngine processEngine) {
    org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.init(processEngine);
  }

  /**
   * Resets operations done via calling init(ProcessEngine processEngine)
   * to its clean state - just as before calling init() for the first time.
   */
  public static void reset() {
    org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.reset();
  }

  /**
   * Assert that... the given ProcessDefinition meets your expectations.
   *
   * @param   actual ProcessDefinition under test
   * @return Assert object offering ProcessDefinition specific assertions.
   */
  public static ProcessDefinitionAssert assertThat(ProcessDefinition actual) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.assertThat(actual);
  }

  /**
   * Assert that... the given ProcessInstance meets your expectations.
   *
   * @param   actual ProcessInstance under test
   * @return Assert object offering ProcessInstance specific assertions.
   */
  public static ProcessInstanceAssert assertThat(ProcessInstance actual) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.assertThat(actual);
  }

  /**
   * Assert that... the given Task meets your expectations.
   *
   * @param   actual Task under test
   * @return Assert object offering Task specific assertions.
   */
  public static TaskAssert assertThat(Task actual) {
    return org.camunda.bpm.engine.test.assertions.bpmn.ProcessEngineTests.assertThat(actual);
  }
  
}
