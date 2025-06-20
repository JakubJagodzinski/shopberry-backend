package com.example.shopberry.utils;

public class SnakeCaseConverter {

    public static String convertToSnakeCase(String field) {
        return field.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

}
