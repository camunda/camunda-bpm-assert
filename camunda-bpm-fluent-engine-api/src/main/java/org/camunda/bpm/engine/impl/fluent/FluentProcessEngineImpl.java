package org.camunda.bpm.engine.impl.fluent;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.fluent.AbstractFluentDelegate;
import org.camunda.bpm.engine.fluent.FluentProcessDefinitionRepository;
import org.camunda.bpm.engine.fluent.FluentProcessEngine;
import org.camunda.bpm.engine.fluent.FluentProcessInstanceRepository;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentProcessEngineImpl extends AbstractFluentDelegate<ProcessEngine> implements FluentProcessEngine {

    private FluentProcessDefinitionRepository processDefinitionRepository;
    private FluentProcessInstanceRepository processInstanceRepository;

    public FluentProcessEngineImpl(final ProcessEngine delegate) {
        super(null, delegate);
        this.processDefinitionRepository = new FluentProcessDefinitionRepositoryImpl(this);
        this.processInstanceRepository = new FluentProcessInstanceRepositoryImpl(this);
    }

    @Override
    public RepositoryService getRepositoryService() {
        return delegate.getRepositoryService();
    }

    @Override
    public RuntimeService getRuntimeService() {
        return delegate.getRuntimeService();
    }

    @Override
    public TaskService getTaskService() {
        return delegate.getTaskService();
    }

    @Override
    public HistoryService getHistoryService() {
        return delegate.getHistoryService();
    }

    @Override
    public IdentityService getIdentityService() {
        return delegate.getIdentityService();
    }

    @Override
    public ManagementService getManagementService() {
        return delegate.getManagementService();
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public FormService getFormService() {
        return delegate.getFormService();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public FluentProcessDefinitionRepository getProcessDefinitionRepository() {
        return processDefinitionRepository;
    }

    @Override
    public FluentProcessInstanceRepository getProcessInstanceRepository() {
        return processInstanceRepository;
    }

    @Override
    public AuthorizationService getAuthorizationService() {
        return delegate.getAuthorizationService();
    }

}
