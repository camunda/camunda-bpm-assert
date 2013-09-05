package org.camunda.bpm.engine.example.creditapplication.service;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named(CreditApplicationService.NAME)
public class CreditApplicationService {

  public static final String NAME = "creditApplicationService";

  private final Logger logger = LoggerFactory.getLogger(CreditApplicationService.class);

  /**
   * Save customer.
   */
  public void saveCustomerData() {
    // Code or delegate to save customer...

    logger.info("saveCustomerData");
  }

  /**
   * Save application data.
   */
  public void saveApplicationData() {
    // Code or delegate to save application data...

    logger.info("saveApplicationData");
  }

}
