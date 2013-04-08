package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.runtime.ProcessInstance;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public interface FluentProcessInstance extends FluentDelegate<ProcessInstance>, ProcessInstance {

    public static interface Move { 
    
        void along(); 
    
    }

    /**
     * Start a process instance ({@link org.camunda.bpm.engine.runtime.ProcessInstance}).
     * @return the started {@link FluentProcessInstance} to make assertions against
     */
    FluentProcessInstance start();

    /**
     * Starts a process instance ({@link org.camunda.bpm.engine.runtime.ProcessInstance}) and move 
     * it along another, already implemented test method which moves the process from a real 
     * instantiation along a path until it arrives at a given activity:
     * 
     *  newProcessInstance("jobAnnouncement", new Move() {
     *      public void along() {
     *          testHappyPath();
     *      }
     *  }, "review")
     *  .setVariable("jobAnnouncementId", jobAnnouncement.getId())
     *  .startAndMove();
     *
     * @return the started {@link FluentProcessInstance} to make assertions against
     * @throws IllegalArgumentException in case the process never arrived at the given activityId.
     */
    FluentProcessInstance startAndMove();

    /**
     * Sets a process getVariable for ({@link FluentProcessInstance}).
     *
     * @param name the name of the process variable to define
     * @param value the value for te process variable
     *
     * @return the same {@link FluentProcessInstance}
     */
     FluentProcessInstance setVariable(String name, Object value);

    /**
     * Retrieves a specific process variable from ({@link FluentProcessInstance}).
     *
     * @param name the name of the process variable which needs to be accessed.
     *
     * @return the process setVariable
     * @throws IllegalArgumentException in case such a process variable does not exist.
     */
    FluentProcessVariable getVariable(String name);

    /**
     * Returns the one and maximum one {@link FluentTask} currently waiting to be completed 
     * in the context of this {@link FluentProcessInstance}.
     *
     * @return the one and only {@link FluentTask} currently waiting to be completed or 
     * null in case no such task is currently waiting.
     * @throws IllegalStateException in case more than one task is currently waiting to be 
     * completed in the context of this {@link FluentProcessInstance}.
     */
    FluentTask task();

    /**
     * Returns the one and maximum one {@link FluentJob} currently waiting to be executed in 
     * the context of this {@link FluentProcessInstance}.
     *
     * @return the one and only {@link FluentJob} currently waiting to be executed or
     * null in case no such job is currently waiting.
     * @throws IllegalStateException in case more than one job is currently waiting to be 
     * executed in the context of this {@link FluentProcessInstance}.
     */
    FluentJob job();

}
