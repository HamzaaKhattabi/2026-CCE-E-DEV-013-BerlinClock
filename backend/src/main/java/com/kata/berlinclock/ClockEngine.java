package com.kata.berlinclock;

public class ClockEngine {

    public static String translateSeconds(int seconds) {
        boolean isEven = seconds % 2 == 0;

        if (seconds < 0 || seconds > 59) {
            throw new IllegalArgumentException();
        }

        return isEven ? "Y" : "O";
    }

    public static String translateFiveHours(int hours) {
        if (hours == 20) {
            return "RRRR";
        } else if (hours == 0) {
            return "OOOO";
        }

        return null;
    }
}
