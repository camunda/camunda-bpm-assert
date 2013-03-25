package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.runtime.Job;

import java.util.Date;

/**
 * @author Martin Schimak <martin.schimak@camunda.com>
 * @author Rafael Cordones <rafael.cordones@camunda.com>
 */
public class FluentJobImpl implements FluentJob {

    private Job delegate;

    protected FluentJobImpl(Job delegate) {
        this.delegate = delegate;
    }

    @Override
    public Job getDelegate() {
        return delegate;
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
        FluentLookups.getManagementService().executeJob(getId());
        return this;
    }

}
