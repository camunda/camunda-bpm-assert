package com.plexiti.activiti.test.fluent;


import java.io.Serializable;

/**
 *
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 * @author Martin Schimak <martin.schimak@plexiti.com>
 *
 */
public class TestProcessVariable implements Serializable {

    private final String name;
    private final Object value;

    public TestProcessVariable(String name, Object value) {
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
