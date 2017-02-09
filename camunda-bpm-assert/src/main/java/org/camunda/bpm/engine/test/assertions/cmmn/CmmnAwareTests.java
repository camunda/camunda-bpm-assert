package org.camunda.bpm.engine.test.assertions.cmmn;


import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.repository.CaseDefinition;
import org.camunda.bpm.engine.repository.CaseDefinitionQuery;
import org.camunda.bpm.engine.runtime.CaseExecution;
import org.camunda.bpm.engine.runtime.CaseExecutionQuery;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.runtime.CaseInstanceQuery;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;

/**
 * Convenience class to access camunda *BPMN* and *CMMN* 
 * related Assertions PLUS helper methods. Use it with a static import:
 *
 * import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.*;
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Martin Günther <martin.guenter@holisticon.de>
 * @author Malte Sörensen <malte.soerensen@holisticon.de>
 */
public class CmmnAwareTests extends BpmnAwareTests {
  
  /**
   * Assert that... the given CaseInstance meets your expectations.
   *
   * @param   actual CaseInstance under test
   * @return Assert object offering CaseInstance specific assertions.
   */
  public static CaseInstanceAssert assertThat(CaseInstance actual) {
    return CmmnAwareAssertions.assertThat(actual);
  }

  /**
   * Assert that... the given CaseExecution meets your expectations.
   *
   * @param   actual CaseExecution under test
   * @return Assert object offering CaseExecution specific assertions.
   */
  public static CaseExecutionAssert assertThat(CaseExecution actual) {
    return CmmnAwareAssertions.assertThat(actual);
  }

  /**
   * Assert that... the given CaseDefinition meets your expectations.
   *
   * @param   actual ProcessDefinition under test
   * @return Assert object offering ProcessDefinition specific assertions.
   */
  public static CaseDefinitionAssert assertThat(CaseDefinition actual) {
    return CmmnAwareAssertions.assertThat(actual);
  }

  /**
   * Helper method to easily access CaseService
   *
   * @return  CaseService of process engine bound to this testing thread
   * @see     org.camunda.bpm.engine.CaseService
   */
  public static CaseService caseService() {
    return processEngine().getCaseService();
  }

  /**
   * Helper method to easily create a new CaseInstanceQuery.
   * @return new CaseInstanceQuery for process engine bound to this testing thread
   */
  public static CaseInstanceQuery caseInstanceQuery() {
    return caseService().createCaseInstanceQuery();
  }

  /**
   * Helper method to easily create a new CaseExecutionQuery.
   * @return new CaseExecutionQuery for process engine bound to this testing thread
   */
  public static CaseExecutionQuery caseExecutionQuery() {
    return caseService().createCaseExecutionQuery();
  }

  /**
   * Helper method to easily create a new CaseDefinitionQuery.
   * @return new CaseExecutionQuery for process engine bound to this testing thread
   */
  public static CaseDefinitionQuery caseDefinitionQuery() {
    return repositoryService().createCaseDefinitionQuery();
  }

  /**
   * Helper method to easily complete a case.
   *
   * @param caseExecution the case to complete
   */
  public static void complete(CaseExecution caseExecution) {
    if (caseExecution == null) {
      throw new IllegalArgumentException("Illegal call of complete(caseExecution) - must not be null!");
    }
    caseService().completeCaseExecution(caseExecution.getId());
  }

  /**
   * Helper method to easily disable a case execution.
   *
   * @param caseExecution
   *        the case execution to complete
   */
  public static void disable(CaseExecution caseExecution) {
    if (caseExecution == null) {
      throw new IllegalArgumentException("Illegal call of disable(caseExecution) - must not be null!");
    }
    caseService().disableCaseExecution(caseExecution.getId());
  }

  /**
   * Helper method to manually activate a case execution.
   *
   * @param caseExecution
   *        the case execution to avtivate
   */
  public static void manuallyStart(CaseExecution caseExecution) {
    if (caseExecution == null) {
      throw new IllegalArgumentException("Illegal call of manuallyStart(caseExecution) - must not be null!");
    }
    caseService().manuallyStartCaseExecution(caseExecution.getId());
  }

  // TODO: this is the only method so far to retrieve caseExecutions - should follow known pattern
  public static CaseExecution caseExecution(String activityId, CaseInstance caseInstance) {
    CaseExecution caseExecution = caseService().createCaseExecutionQuery().caseInstanceId(caseInstance.getCaseInstanceId()).activityId(activityId).singleResult();
    assertThat(caseExecution).overridingErrorMessage("CaseExecution for activity '" + activityId + "' not found!").isNotNull();
    return caseExecution;
  }

}
