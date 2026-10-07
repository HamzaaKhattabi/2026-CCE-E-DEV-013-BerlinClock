package com.kata.berlinclock;

public class ClockEngine {

    public static String translateSeconds(int seconds) {
        boolean isEven = seconds % 2 == 0;

        return isEven ? "Y" : "O";
    }
}
