package org.camunda.bpm.engine.impl.fluent;

import org.camunda.bpm.engine.fluent.AbstractFluentProcessEngineAware;
import org.camunda.bpm.engine.fluent.FluentProcessEngine;
import org.camunda.bpm.engine.fluent.FluentProcessInstance;
import org.camunda.bpm.engine.fluent.FluentProcessInstanceRepository;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentProcessInstanceRepositoryImpl extends AbstractFluentProcessEngineAware implements FluentProcessInstanceRepository {

  public FluentProcessInstanceRepositoryImpl(FluentProcessEngine engine) {
    super(engine);
  }

  /**
   * @see org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests#newProcessInstance(String)
   */
  @Override
  public FluentProcessInstance newProcessInstance(String processDefinitionKey) {
    return new FluentProcessInstanceImpl(engine, processDefinitionKey);
  }

}
