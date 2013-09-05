package org.camunda.bpm.engine.example.creditapplication;

public enum KreditantragProcess {
  ; // constant enum

  public static final String PROCESS_KEY = "process_credit_application";
  public static final String PROCESS_FILE = "test-credit-application.bpmn";

  public static enum VARIABLES {
    ; // constant enum

    public static final String NEXT_ACTION_NAME = "nextAction";

    public static final String KUNDE = "kunde.pojo";
    public static final String KUNDE_ID = "kunde.id";

    public static final String KREDITANTRAG = "kreditantrag.pojo";
    public static final String KREDITANTRAG_ID = "kreditantrag.id";

    public static final String ANTRAG_GENEHMIGT = "antrag.genehmigt";
    public static final String PRUEFUNG_ERFORDERLICH = "pruefung.erforderlich";

    public static final String IS_MANUAL_EXAMINATION = "isManualExamination";
    public static final String IS_CREDIT_APPLICATION_GRANTED = "isCreditApplicationGranted";
  }

  public static enum ELEMENTS {
    ; // constant enum
    public static final String TASK_ENTER_CUSTOMER_DATA = "task_enter_customer_data";
    public static final String SERVICE_SAVE_CUSTOMER_DATA = "service_save_customer_data";
    public static final String TASK_ENTER_APPLICATION_DATA = "task_enter_application_data";
    public static final String SERVICE_SAVE_APPLICATION_DATA = "service_save_application_data";
    public static final String SERVICE_CALCULATE_STATEMENT_OF_COSTS = "service_calculate_statement_of_costs";
    public static final String SERVICE_CALCULATE_CREDIT_APPROVAL = "service_calculate_credit_approval";
    public static final String SERVICE_CHECK_IF_MANUAL_INSPECTION_IS_REQUIRED = "service_check_if_manual_inspection_is_required";
    public static final String TASK_SHOW_APPLICATION_DATA = "task_show_applicationdata";
    public static final String SERVICE_CHECK_IF_CREDIT_APPLICATION_IS_APPROVED = "service_check_if_credit_application_is_approved";
    public static final String TASK_INSPECT_CREDIT_APPLICATION = "task_inspect_credit_application";
    public static final String SERVICE_SAVE_APPLICATION_DATA_2 = "service_save_application_data_2";
    public static final String END_CREDIT_APPLICATION_APPROVED = "end_credit_application_is_approved";
    public static final String END_CREDIT_APPLICATION_NOT_APPROVED = "end_credit_application_is_not_approved";
  }
}
