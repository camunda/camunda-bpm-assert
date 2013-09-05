package org.camunda.bpm.engine.fluent;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class AbstractFluentProcessEngineAware {

  protected final FluentProcessEngine engine;

  protected AbstractFluentProcessEngineAware(final FluentProcessEngine engine) {
    this.engine = engine;
  }

}
