package org.camunda.bdd.examples.simple;

/**
 * Process constants.
 * @author Simon Zambrovski, Holisticon AG.
 */
public class SimpleProcess {

    /**
     * Name and location of process definition.
     */
    public static final String BPMN = "simple.bpmn";
    /**
     * Process Key.
     */
    public static final String PROCESS = "simple-process";

    /**
     * Process variables.
     */
    public static enum Variables {
        ;

        public static final String IS_AUTOMATIC = "isAutomaticProcessing";
        public static final String ARE_PROCESSING_ERRORS_PRESENT = "processingErrorsPresent";
    }

    /**
     * Process elements.
     */
    public static enum Elements {
        ;

        public static final String TASK_PROCESS_MANUALLY = "task_process_contract_manually";
        public static final String EVENT_CONTRACT_PROCESSED = "event_contract_processed";
        public static final String SERVICE_LOAD_CONTRACT_DATA = "service_load_contract_data";
        public static final String SERVICE_PROCESS_CONTRACT_AUTOMATICALLY = "service_process_contract_automatically";
    }

    public static enum Events {
        ;

        public static final String ERROR_PROCESS_AUTOMATICALLY_FAILED = "errorProcessAutomaticallyFailed";
    }
}
