package com.example.shopberry.utils;

public class DoubleParser {

    public static Double parse(String val) {
        try {
            return Double.parseDouble(val.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

}
