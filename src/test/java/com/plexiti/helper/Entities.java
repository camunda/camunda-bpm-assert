package com.plexiti.helper;

/**
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public class Entities {

    public static String idVariableName(Class<?> clazz) {
        String name = clazz.getSimpleName();
        return name.substring(0, 1).toLowerCase() + name.substring(1) + "Id";
    }

}
