package org.camunda.bpm.engine.impl.fluent;

import org.camunda.bpm.engine.fluent.AbstractFluentDelegate;
import org.camunda.bpm.engine.fluent.FluentProcessEngine;
import org.camunda.bpm.engine.fluent.FluentTask;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.DelegationState;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.fluent.support.Maps;

import java.util.Date;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentTaskImpl extends AbstractFluentDelegate<Task> implements FluentTask {

  protected FluentTaskImpl(final FluentProcessEngine engine, final Task delegate) {
    super(engine, delegate);
  }

  @Override
  public String getId() {
    return delegate.getId();
  }

  @Override
  public String getName() {
    return delegate.getName();
  }

  @Override
  public void setName(final String name) {
    delegate.setName(name);
  }

  @Override
  public String getDescription() {
    return delegate.getDescription();
  }

  @Override
  public void setDescription(final String description) {
    delegate.setDescription(description);
  }

  @Override
  public int getPriority() {
    return delegate.getPriority();
  }

  @Override
  public void setPriority(final int priority) {
    delegate.setPriority(priority);
  }

  @Override
  public String getOwner() {
    return delegate.getOwner();
  }

  @Override
  public void setOwner(final String owner) {
    delegate.setOwner(owner);
  }

  @Override
  public String getAssignee() {
    return delegate.getAssignee();
  }

  @Override
  public void setAssignee(final String assignee) {
    delegate.setAssignee(assignee);
  }

  @Override
  public DelegationState getDelegationState() {
    return delegate.getDelegationState();
  }

  @Override
  public void setDelegationState(final DelegationState delegationState) {
    delegate.setDelegationState(delegationState);
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
  public String getProcessDefinitionId() {
    return delegate.getProcessDefinitionId();
  }

  @Override
  public Date getCreateTime() {
    return delegate.getCreateTime();
  }

  @Override
  public String getTaskDefinitionKey() {
    return delegate.getTaskDefinitionKey();
  }

  @Override
  public Date getDueDate() {
    return delegate.getDueDate();
  }

  @Override
  public void setDueDate(final Date dueDate) {
    delegate.setDueDate(dueDate);
  }

  @Override
  public void delegate(final String userId) {
    delegate.delegate(userId);
  }

  @Override
  public void setParentTaskId(final String parentTaskId) {
    delegate.setParentTaskId(parentTaskId);
  }

  @Override
  public String getParentTaskId() {
    return delegate.getParentTaskId();
  }

  @Override
  public FluentTask claim(final User user) {
    claim(user.getId());
    return this;
  }

  @Override
  public FluentTask claim(final String userId) {
    engine.getTaskService().claim(delegate.getId(), userId);
    return this;
  }

  @Override
  public FluentTask complete(final Object... variables) {
    engine.getTaskService().complete(delegate.getId(), Maps.parseMap(variables));
    return this;
  }

  @Override
  public boolean isSuspended() {
    return delegate.isSuspended();
  }

}
