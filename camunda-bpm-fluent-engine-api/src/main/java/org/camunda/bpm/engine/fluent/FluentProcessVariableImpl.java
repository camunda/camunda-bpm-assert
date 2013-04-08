package org.camunda.bpm.engine.fluent;


/**
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class FluentProcessVariableImpl implements FluentProcessVariable {

    private final String name;
    private Object value;

    protected FluentProcessVariableImpl(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        return value;
    }

}
