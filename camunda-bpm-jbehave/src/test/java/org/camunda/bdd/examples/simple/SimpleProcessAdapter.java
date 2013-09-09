package org.camunda.bdd.examples.simple;

import javax.inject.Named;

@Named(SimpleProcessAdapter.NAME)
public interface SimpleProcessAdapter {
  
  String NAME = "simpleProcessAdapter";

  boolean loadContractData();
  
  void processContract();
  
  void cancelProcessing();
}
