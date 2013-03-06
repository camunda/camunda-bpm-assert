package com.plexiti.activiti.test.fluent.engine;

import org.camunda.bpm.engine.runtime.Job;

import java.util.Date;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentBpmnJobImpl implements FluentBpmnJob {

    private Job delegate;

    protected FluentBpmnJobImpl(Job delegate) {
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
    public void execute() {
        FluentBpmnLookups.getManagementService().executeJob(getId());
    }

}
