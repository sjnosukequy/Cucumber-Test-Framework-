package org.example.cucumber.utils;

public class emptyUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty() || str.isBlank();
    }
}
