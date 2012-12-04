package com.plexiti.activiti.showcase.jobannouncement.process;

/**
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public abstract class ProcessConstants {

    public static final String JOBANNOUNCEMENT_PROCESS = "job-announcement";
    public static final String JOBANNOUNCEMENT_PUBLICATION_PROCESS = "job-announcement-publication";

    public static final String JOBANNOUNCEMENT_PROCESS_RESOURCE = "com/plexiti/activiti/showcase/jobannouncement/process/job-announcement.bpmn";
    public static final String JOBANNOUNCEMENT_PUBLICATION_PROCESS_RESOURCE = "com/plexiti/activiti/showcase/jobannouncement/process/job-announcement-publication.bpmn";

    public static final String JOBANNOUNCEMENT_PROCESS_VARIABLE_APPROVED = "approved";
    public static final String JOBANNOUNCEMENT_PROCESS_VARIABLE_TWITTER = "twitter";
    public static final String JOBANNOUNCEMENT_PROCESS_VARIABLE_FACEBOOK = "facebook";
    public static final String JOBANNOUNCEMENT_PROCESS_VARIABLE_COMMENT = "comment";
    public static final String JOBANNOUNCEMENT_PROCESS_VARIABLE_LAST_COMMENT = "last_comment";

    public static final String ROLE_STAFF = "engineering";
    public static final String ROLE_MANAGER = "management";

    public static final String USER_STAFF = "fozzie";
    public static final String USER_MANAGER = "gonzo";

    public static final String TASK_DESCRIBE_POSITION = "edit";
    public static final String TASK_REVIEW_ANNOUNCEMENT = "review";
    public static final String TASK_CORRECT_ANNOUNCEMENT = "correct";
    public static final String TASK_INITIATE_ANNOUNCEMENT = "publish";

}
