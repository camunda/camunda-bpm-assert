package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.ProcessEngine;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public interface FluentProcessEngine extends FluentDelegate<ProcessEngine>, ProcessEngine {

  FluentProcessDefinitionRepository getProcessDefinitionRepository();

  FluentProcessInstanceRepository getProcessInstanceRepository();

}
