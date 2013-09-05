package org.camunda.bpm.engine.example.creditapplication.service;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named(FinancialService.NAME)
public class FinancialService {
  public static final String NAME = "financialService";

  private final Logger logger = LoggerFactory.getLogger(FinancialService.class);

  /**
   * Calculates statement of costs.
   */
  public void calculateStatementOfCosts() {
    // Code or delegate to calculate statement of costs...

    logger.info("calculateStatementOfCosts");
  }

  /**
   * Calculates credit approval.
   */
  public void calculateCreditApproval() {
    // Code or delegate to calculate credit approval...

    logger.info("calculateCreditApproval");
  }

  /**
   * Checks if a manual inspection is required.
   * 
   * @return true or false
   */
  public Boolean checkIfManualInspectionIsRequired() {
    // Code or delegate to check if a manual inspection is required...

    logger.info("checkIfManualInspectionIsRequired");

    return null;
  }

  /**
   * Checks if credit application is approved.
   * 
   * @return true or false
   */
  public Boolean checkIfCreditApplicationIsApproved() {
    // Code or delegate to check if credit application is approved...

    logger.info("checkIfCreditApplicationIsApproved");

    return null;
  }
}
