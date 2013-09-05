package org.camunda.bpm.engine.example.creditapplication.test;

import static org.camunda.bpm.engine.example.creditapplication.KreditantragProcess.PROCESS_KEY;
import static org.camunda.bpm.engine.example.creditapplication.KreditantragProcess.PROCESS_FILE;

import static org.camunda.bpm.engine.example.creditapplication.KreditantragProcess.ELEMENTS.TASK_ENTER_CUSTOMER_DATA;
import static org.camunda.bpm.engine.example.creditapplication.KreditantragProcess.ELEMENTS.TASK_ENTER_APPLICATION_DATA;
import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.newProcessInstance;

import javax.inject.Inject;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.camunda.bpm.engine.example.creditapplication.service.CreditApplicationService;
import org.camunda.bpm.engine.example.creditapplication.service.FinancialService;
import org.camunda.bpm.engine.fluent.FluentProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.test.needle.ProcessEngineNeedleRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class CreditApplicationSingleStepsTest {

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

    private void assertIsInTask(final String taskName) {
      assertThat(processInstance.task().getTaskDefinitionKey(), is(taskName));
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
  public void shouldBeInTaskEnterApplication() {
    // given
    glue.isManualInspection(false);
    glue.isCreditApplicationApproved(true);

    // when
    glue.startProcess(PROCESS_KEY);

    // then
    glue.assertIsInTask(TASK_ENTER_CUSTOMER_DATA);
  }

  @Deployment(resources = PROCESS_FILE)
  @Test
  public void shouldBeInTaskEnterApplicationData() {
    // given
    glue.isManualInspection(false);
    glue.isCreditApplicationApproved(true);

    // when
    glue.startProcess(PROCESS_KEY);
    glue.enterCustomerData();

    // then
    glue.assertIsInTask(TASK_ENTER_APPLICATION_DATA);
  }

}
