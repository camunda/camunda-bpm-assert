package com.plexiti.helper;

/**
 *
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 *
 */
public class Strings {

    public static String trimToNull(String string) {
        if (string != null)
            string = string.trim();
        return "".equals(string) ? null : string;
    }

}
