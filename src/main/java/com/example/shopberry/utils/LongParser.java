package com.example.shopberry.utils;

public class LongParser {

    public static Long parse(String val) {
        try {
            return Long.parseLong(val.trim());
        } catch (Exception e) {
            return 0L;
        }
    }

}
