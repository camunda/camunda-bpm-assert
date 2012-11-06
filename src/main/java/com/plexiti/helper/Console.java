package com.plexiti.helper;

public class Console {

    public static void println(Object... objects) {
        for (Object o: objects) {
            System.out.println(o);
        }
    }

}
