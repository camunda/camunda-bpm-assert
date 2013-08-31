package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.runtime.Job;

import java.util.Date;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentJobImpl extends AbstractFluentDelegate<Job> implements FluentJob {

    protected FluentJobImpl(final FluentProcessEngine engine, final Job delegate) {
        super(engine, delegate);
    }

    @Override
    public String getId() {
        return delegate.getId();
    }

    @Override
    public Date getDuedate() {
        return delegate.getDuedate();
    }

    @Override
    public String getProcessInstanceId() {
        return delegate.getProcessInstanceId();
    }

    @Override
    public String getExecutionId() {
        return delegate.getExecutionId();
    }

    @Override
    public int getRetries() {
        return delegate.getRetries();
    }

    @Override
    public String getExceptionMessage() {
        return delegate.getExceptionMessage();
    }

    @Override
    public FluentJob execute() {
        engine.getManagementService().executeJob(getId());
        return this;
    }

    @Override
    public String getDeploymentId() {
        return delegate.getDeploymentId();
    }

}
