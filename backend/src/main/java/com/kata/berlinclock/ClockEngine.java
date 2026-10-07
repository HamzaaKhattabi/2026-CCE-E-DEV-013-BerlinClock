package com.kata.berlinclock;

public class ClockEngine {

    public static String translateSeconds(int seconds) {
        boolean isEven = seconds % 2 == 0;

        if (seconds < 0 || seconds > 59) {
            throw new IllegalArgumentException();
        }

        return isEven ? Lamp.YELLOW.getIndicator() : Lamp.OFF.getIndicator();
    }

    public static String translateFiveHours(int hours) {
        int maxLength = 4;
        int multiple = 5;

        if (hours < 0 || hours > 23) {
            throw new IllegalArgumentException();
        }

        int redLampCount = hours / multiple;

        return Lamp.RED.getIndicator().repeat(redLampCount)
                .concat(Lamp.OFF.getIndicator().repeat(maxLength - redLampCount));
    }

    public static String translateOneHours(int hours) {
        int maxLength = 4;
        int multiple = 1;

        int redLampCount = hours / multiple;

        return Lamp.RED.getIndicator().repeat(redLampCount)
                .concat(Lamp.OFF.getIndicator().repeat(maxLength - redLampCount));
    }
}
