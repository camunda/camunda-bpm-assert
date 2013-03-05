package com.plexiti.activiti.test.fluent.assertions;

import com.plexiti.activiti.test.fluent.engine.FluentBpmnProcessVariable;
import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;
import org.fest.assertions.api.LongAssert;
import org.fest.assertions.api.StringAssert;

/**
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class TestProcessVariableAssert extends AbstractAssert<TestProcessVariableAssert, FluentBpmnProcessVariable> {

    protected TestProcessVariableAssert(FluentBpmnProcessVariable actual) {
        super(actual, TestProcessVariableAssert.class);
    }

    public static TestProcessVariableAssert assertThat(FluentBpmnProcessVariable actual) {
        return new TestProcessVariableAssert(actual);
    }

    public TestProcessVariableAssert exists() {
        isNotNull();

        Assertions.assertThat(actual.getValue())
                .overridingErrorMessage("Unable to find processInstance processVariable '%s'", actual.getName())
                .isNotNull();

        return this;
    }

    public TestProcessVariableAssert isDefined() {
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
