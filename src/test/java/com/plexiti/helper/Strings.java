package com.plexiti.helper;

public class Strings {

    public static String trimToNull(String string) {
        if (string != null)
            string = string.trim();
        return "".equals(string) ? null : string;
    }

}
