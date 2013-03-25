package org.camunda.bpm.engine.test.fluent.assertions;

import org.camunda.bpm.engine.fluent.FluentProcessVariable;
import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;
import org.fest.assertions.api.LongAssert;
import org.fest.assertions.api.StringAssert;

/**
 * @author Rafael Cordones <rafael.cordones@camunda.com>
 * @author Martin Schimak <martin.schimak@camunda.com>
 */
public class ProcessVariableAssert extends AbstractAssert<ProcessVariableAssert, FluentProcessVariable> {

    protected ProcessVariableAssert(FluentProcessVariable actual) {
        super(actual, ProcessVariableAssert.class);
    }

    public static ProcessVariableAssert assertThat(FluentProcessVariable actual) {
        return new ProcessVariableAssert(actual);
    }

    public ProcessVariableAssert exists() {
        isNotNull();

        Assertions.assertThat(actual.getValue())
                .overridingErrorMessage("Unable to find processInstance processVariable '%s'", actual.getName())
                .isNotNull();

        return this;
    }

    public ProcessVariableAssert isDefined() {
        return exists();
    }

    public LongAssert asLong() {
        Assertions.assertThat(actual.getValue())
            .overridingErrorMessage("Unable to convert processInstance processVariable '%s' to an instance of class '%s'", actual.getName(), Long.class)
            .isInstanceOf(Long.class);
        return Assertions.assertThat((Long) actual.getValue());
    }

    public StringAssert asString() {
        Assertions.assertThat(actual.getValue())
            .overridingErrorMessage("Unable to convert processInstance processVariable '%s' to an instance of class '%s'", actual.getName(), String.class)
            .isInstanceOf(String.class);
        return Assertions.assertThat((String) actual.getValue());
    }

    // TODO: asInteger(), asList(), asMap(), ...
}
