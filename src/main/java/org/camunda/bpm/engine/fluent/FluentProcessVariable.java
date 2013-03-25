package org.camunda.bpm.engine.fluent;


import java.io.Serializable;

/**
 * @author Rafael Cordones <rafael.cordones@camunda.com>
 * @author Martin Schimak <martin.schimak@camunda.com>
 */
public class FluentProcessVariable implements Serializable {

    private final String name;
    private final Object value;

    public FluentProcessVariable(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
