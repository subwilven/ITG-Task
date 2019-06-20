package com.islam.basepropject.project_base.utils;

public final class NumberUtils {

    private NumberUtils() {}

    public static String doubleToString(double num) {
        return String.format("%.2f", num);
    }
}
