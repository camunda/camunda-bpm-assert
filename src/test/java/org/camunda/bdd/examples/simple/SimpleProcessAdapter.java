package org.camunda.bdd.examples.simple;

import javax.inject.Named;

@Named(SimpleProcessAdapter.NAME)
public class SimpleProcessAdapter {
	public static final String NAME = "simpleProcessAdapter";
	
	public boolean loadContractData() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	public void processContract() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	public void cancelProcessing() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
