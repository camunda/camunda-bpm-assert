package com.plexiti.helper;

/**
 *
 * @author Martin Schimak <martin.schimak@camunda.com>
 * @author Rafael Cordones <rafael.cordones@camunda.com>
 *
 */
public class Strings {

    public static String trimToNull(String string) {
        if (string != null)
            string = string.trim();
        return "".equals(string) ? null : string;
    }

}
