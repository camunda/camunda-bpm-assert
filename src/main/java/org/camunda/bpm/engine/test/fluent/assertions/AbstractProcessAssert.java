package org.camunda.bpm.engine.test.fluent.assertions;

import org.camunda.bpm.engine.fluent.FluentProcessEngine;
import org.fest.assertions.api.AbstractAssert;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class AbstractProcessAssert<S extends AbstractProcessAssert<S,A>, A> extends AbstractAssert<S, A> {

    protected FluentProcessEngine engine;

    public AbstractProcessAssert(FluentProcessEngine engine, A actual, Class<?> selfType) {
        super(actual, selfType);
        this.engine = engine;
    }

}
