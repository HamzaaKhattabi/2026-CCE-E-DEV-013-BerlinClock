package com.kata.berlinclock;

import com.kata.berlinclock.domains.BerlinHour;

public class ClockEngine {

    private static final int HOURS_ROW_LAMP_COUNT = 4;
    private static final int HOURS_PER_FIVE_HOURS_LAMP = 5;

    public static String translateSeconds(int seconds) {
        boolean isEven = seconds % 2 == 0;

        if (seconds < 0 || seconds > 59) {
            throw new IllegalArgumentException();
        }

        return isEven ? Lamp.YELLOW.getIndicator() : Lamp.OFF.getIndicator();
    }

    public static BerlinHour translateHours(int hours) {
        String firstRow = translateFiveHours(hours);
        String secondRow = translateOneHours(hours);

        return new BerlinHour(firstRow, secondRow);
    }

    public static String translateFiveHours(int hours) {
        if (hours < 0 || hours > 23) {
            throw new IllegalArgumentException();
        }

        int litLampCount = hours / HOURS_PER_FIVE_HOURS_LAMP;

        return Lamp.RED.getIndicator().repeat(litLampCount)
                .concat(Lamp.OFF.getIndicator().repeat(HOURS_ROW_LAMP_COUNT - litLampCount));
    }

    public static String translateOneHours(int hours) {
        int litLampCount = hours % HOURS_PER_FIVE_HOURS_LAMP;

        return Lamp.RED.getIndicator().repeat(litLampCount)
                .concat(Lamp.OFF.getIndicator().repeat(HOURS_ROW_LAMP_COUNT - litLampCount));
    }
}
