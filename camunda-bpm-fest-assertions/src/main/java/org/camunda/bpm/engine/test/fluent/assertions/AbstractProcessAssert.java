package org.camunda.bpm.engine.test.fluent.assertions;

import org.camunda.bpm.engine.ProcessEngine;
import org.fest.assertions.api.AbstractAssert;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class AbstractProcessAssert<S extends AbstractProcessAssert<S, A>, A> extends AbstractAssert<S, A> {

  protected ProcessEngine engine;

  public AbstractProcessAssert(ProcessEngine engine, A actual, Class<?> selfType) {
    super(actual, selfType);
    this.engine = engine;
  }
    
  public A getActual() {
    return actual;
  }

}
