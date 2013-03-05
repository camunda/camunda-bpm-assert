package com.plexiti.activiti.test.fluent.engine;


import java.io.Serializable;

/**
 *
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 * @author Martin Schimak <martin.schimak@plexiti.com>
 *
 */
public class FluentBpmnProcessVariable implements Serializable {

    private final String name;
    private final Object value;

    public FluentBpmnProcessVariable(String name, Object value) {
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
