package org.camunda.bpm.engine.fluent;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class AbstractFluentDelegate<D> extends AbstractFluentProcessEngineAware implements FluentDelegate<D> {

  protected D delegate;

  protected AbstractFluentDelegate(FluentProcessEngine engine, D delegate) {
    super(engine);
    this.delegate = delegate;
  }

  @Override
  public D getDelegate() {
    return delegate;
  }

}
