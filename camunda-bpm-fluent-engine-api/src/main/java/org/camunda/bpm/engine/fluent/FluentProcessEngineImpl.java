package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentProcessEngineImpl extends AbstractFluentDelegate<ProcessEngine> implements FluentProcessEngine {

    private FluentProcessDefinitionRepository processDefinitionRepository;
    private FluentProcessInstanceRepository processInstanceRepository;

    public FluentProcessEngineImpl(ProcessEngine delegate) {
        super(null, delegate);
        this.engine = this;
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
    
}
