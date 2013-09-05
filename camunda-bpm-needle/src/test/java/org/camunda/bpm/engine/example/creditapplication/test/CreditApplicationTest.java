package org.camunda.bpm.engine.example.creditapplication.test;

import static org.camunda.bpm.engine.example.creditapplication.KreditantragProcess.PROCESS_KEY;
import static org.camunda.bpm.engine.example.creditapplication.KreditantragProcess.PROCESS_FILE;

import static org.camunda.bpm.engine.example.creditapplication.KreditantragProcess.ELEMENTS.TASK_ENTER_CUSTOMER_DATA;
import static org.camunda.bpm.engine.example.creditapplication.KreditantragProcess.ELEMENTS.SERVICE_SAVE_CUSTOMER_DATA;
import static org.camunda.bpm.engine.example.creditapplication.KreditantragProcess.ELEMENTS.TASK_ENTER_APPLICATION_DATA;
import static org.camunda.bpm.engine.example.creditapplication.KreditantragProcess.ELEMENTS.SERVICE_SAVE_APPLICATION_DATA;
import static org.camunda.bpm.engine.example.creditapplication.KreditantragProcess.ELEMENTS.END_CREDIT_APPLICATION_NOT_APPROVED;
import static org.camunda.bpm.engine.example.creditapplication.KreditantragProcess.ELEMENTS.END_CREDIT_APPLICATION_APPROVED;
import static org.camunda.bpm.engine.example.creditapplication.KreditantragProcess.ELEMENTS.*;

import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.newProcessInstance;

import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.camunda.bpm.engine.example.creditapplication.service.CreditApplicationService;
import org.camunda.bpm.engine.example.creditapplication.service.FinancialService;
import org.camunda.bpm.engine.fluent.FluentProcessInstance;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.test.needle.ProcessEngineNeedleRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class CreditApplicationTest {

  /**
   * Glue code.
   * 
   * @author Sven Polte, Holisticon AG
   */
  class Glue {
    FluentProcessInstance processInstance = null;

    /**
     * Start given process.
     * 
     * @param processKey
     */
    private void startProcess(String processKey) {
      processInstance = newProcessInstance(processKey).start();
    }

    /**
     * Completes: Enter Customer Data.
     */
    public void enterCustomerData() {
      processInstance.task().complete();
    }

    /**
     * Completes: Enter Application Data.
     */
    public void enterApplicationData() {
      processInstance.task().complete();
    }

    /**
     * Completes: Inspect Credit Application.
     */
    public void inspectCreditApplication() {
      processInstance.task().complete();
    }

    /**
     * Completes: Show Application Data.
     */
    public void showApplicationData() {
      processInstance.task().complete();
    }

    /**
     * Sets decision for ManualInspection
     * 
     * @param isManualInspection
     */
    public void isManualInspection(boolean isManualInspection) {
      when(financialService.checkIfManualInspectionIsRequired()).thenReturn(isManualInspection);
    }

    public void isCreditApplicationApproved(Boolean isCreditApplicationApproved) {
      when(financialService.checkIfCreditApplicationIsApproved()).thenReturn(isCreditApplicationApproved);
    }

    /**
     * Assert that process execution has run through the activity with given id.
     * 
     * @param name
     *          name of the activity.
     */
    private void assertActivityVisitedOnce(final String name) {
      HistoricActivityInstance singleResult = processEngineNeedleRule.getHistoryService().createHistoricActivityInstanceQuery().finished().activityId(name)
          .singleResult();
      assertNotNull(singleResult);
    }

    /**
     * Assert process end event.
     * 
     * @param name
     *          name of the end event.
     */
    private void assertEndEvent(final String name) {
      assertActivityVisitedOnce(name);
      processEngineNeedleRule.assertNoMoreRunningInstances();

    }
  }

  @Rule
  public final ProcessEngineNeedleRule processEngineNeedleRule = ProcessEngineNeedleRule.fluentNeedleRule(this).build();

  @Inject
  private CreditApplicationService creditApplicationService;

  @Inject
  private FinancialService financialService;

  @Before
  public void initTest() {
    Mocks.register(CreditApplicationService.NAME, creditApplicationService);
    Mocks.register(FinancialService.NAME, financialService);
  }

  private final Glue glue = new Glue();

  /**
   * Simple deployment test.
   */
  @Deployment(resources = PROCESS_FILE)
  @Test
  public void shouldDeploy() {
    // do nothing
  }

  @Deployment(resources = PROCESS_FILE)
  @Test
  public void shouldApproveCreditApplication() {
    // given
    glue.isManualInspection(false);
    glue.isCreditApplicationApproved(true);

    // when
    glue.startProcess(PROCESS_KEY);
    glue.enterCustomerData();
    glue.enterApplicationData();
    glue.showApplicationData();

    // then
    glue.assertActivityVisitedOnce(TASK_ENTER_CUSTOMER_DATA);
    glue.assertActivityVisitedOnce(SERVICE_SAVE_CUSTOMER_DATA);
    glue.assertActivityVisitedOnce(TASK_ENTER_APPLICATION_DATA);
    glue.assertActivityVisitedOnce(SERVICE_SAVE_APPLICATION_DATA);
    glue.assertActivityVisitedOnce(SERVICE_CALCULATE_STATEMENT_OF_COSTS);
    glue.assertActivityVisitedOnce(SERVICE_CALCULATE_CREDIT_APPROVAL);
    glue.assertActivityVisitedOnce(SERVICE_CHECK_IF_MANUAL_INSPECTION_IS_REQUIRED);
    glue.assertActivityVisitedOnce(TASK_SHOW_APPLICATION_DATA);
    glue.assertActivityVisitedOnce(SERVICE_CHECK_IF_CREDIT_APPLICATION_IS_APPROVED);

    glue.assertEndEvent(END_CREDIT_APPLICATION_APPROVED);
  }

  @Deployment(resources = PROCESS_FILE)
  @Test
  public void shouldApproveCreditApplicationWithManualInspection() {
    // given
    glue.isManualInspection(true);
    glue.isCreditApplicationApproved(true);

    // when
    glue.startProcess(PROCESS_KEY);
    glue.enterCustomerData();
    glue.enterApplicationData();
    glue.inspectCreditApplication();

    // then
    glue.assertActivityVisitedOnce(TASK_ENTER_CUSTOMER_DATA);
    glue.assertActivityVisitedOnce(SERVICE_SAVE_CUSTOMER_DATA);
    glue.assertActivityVisitedOnce(TASK_ENTER_APPLICATION_DATA);
    glue.assertActivityVisitedOnce(SERVICE_SAVE_APPLICATION_DATA);
    glue.assertActivityVisitedOnce(SERVICE_CALCULATE_STATEMENT_OF_COSTS);
    glue.assertActivityVisitedOnce(SERVICE_CALCULATE_CREDIT_APPROVAL);
    glue.assertActivityVisitedOnce(SERVICE_CHECK_IF_MANUAL_INSPECTION_IS_REQUIRED);
    glue.assertActivityVisitedOnce(TASK_INSPECT_CREDIT_APPLICATION);
    glue.assertActivityVisitedOnce(SERVICE_SAVE_APPLICATION_DATA_2);
    glue.assertActivityVisitedOnce(SERVICE_CHECK_IF_CREDIT_APPLICATION_IS_APPROVED);

    glue.assertEndEvent(END_CREDIT_APPLICATION_APPROVED);
  }

  @Deployment(resources = PROCESS_FILE)
  @Test
  public void shouldNotApproveCreditApplication() {
    // given
    glue.isManualInspection(false);
    glue.isCreditApplicationApproved(false);

    // when
    glue.startProcess(PROCESS_KEY);
    glue.enterCustomerData();
    glue.enterApplicationData();
    glue.showApplicationData();

    // then
    glue.assertActivityVisitedOnce(TASK_ENTER_CUSTOMER_DATA);
    glue.assertActivityVisitedOnce(SERVICE_SAVE_CUSTOMER_DATA);
    glue.assertActivityVisitedOnce(TASK_ENTER_APPLICATION_DATA);
    glue.assertActivityVisitedOnce(SERVICE_SAVE_APPLICATION_DATA);
    glue.assertActivityVisitedOnce(SERVICE_CALCULATE_STATEMENT_OF_COSTS);
    glue.assertActivityVisitedOnce(SERVICE_CALCULATE_CREDIT_APPROVAL);
    glue.assertActivityVisitedOnce(SERVICE_CHECK_IF_MANUAL_INSPECTION_IS_REQUIRED);
    glue.assertActivityVisitedOnce(TASK_SHOW_APPLICATION_DATA);
    glue.assertActivityVisitedOnce(SERVICE_CHECK_IF_CREDIT_APPLICATION_IS_APPROVED);

    glue.assertEndEvent(END_CREDIT_APPLICATION_NOT_APPROVED);
  }

  @Deployment(resources = PROCESS_FILE)
  @Test
  public void shouldNotApproveCreditApplicationWithManualInspection() {
    // given
    glue.isManualInspection(true);
    glue.isCreditApplicationApproved(false);

    // when
    glue.startProcess(PROCESS_KEY);
    glue.enterCustomerData();
    glue.enterApplicationData();
    glue.inspectCreditApplication();

    // then
    glue.assertActivityVisitedOnce(TASK_ENTER_CUSTOMER_DATA);
    glue.assertActivityVisitedOnce(SERVICE_SAVE_CUSTOMER_DATA);
    glue.assertActivityVisitedOnce(TASK_ENTER_APPLICATION_DATA);
    glue.assertActivityVisitedOnce(SERVICE_SAVE_APPLICATION_DATA);
    glue.assertActivityVisitedOnce(SERVICE_CALCULATE_STATEMENT_OF_COSTS);
    glue.assertActivityVisitedOnce(SERVICE_CALCULATE_CREDIT_APPROVAL);
    glue.assertActivityVisitedOnce(SERVICE_CHECK_IF_MANUAL_INSPECTION_IS_REQUIRED);
    glue.assertActivityVisitedOnce(TASK_INSPECT_CREDIT_APPLICATION);
    glue.assertActivityVisitedOnce(SERVICE_SAVE_APPLICATION_DATA_2);
    glue.assertActivityVisitedOnce(SERVICE_CHECK_IF_CREDIT_APPLICATION_IS_APPROVED);

    glue.assertEndEvent(END_CREDIT_APPLICATION_NOT_APPROVED);
  }
}
