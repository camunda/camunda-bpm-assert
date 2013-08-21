package org.camunda.bdd.examples.simple;

public class SimpleProcess {

    /**
     * Name and location of process definition.
     */
    public static final String BPMN = "simple.bpmn";
    /**
     * Process Key.
     */
    public static final String PROCESS = "simple-process";

    public static enum Variables {
        ;

        public static final String IS_AUTOMATIC = "isAutomaticProcessing";
        public static final String ARE_PROCESSING_ERRORS_PRESENT = "processingErrorsPresent";
    }

    public static enum Elements {
        ;

        public static final String EVENT_CONTRACT_PROCESSED = "event_contract_processed";
    }
}
