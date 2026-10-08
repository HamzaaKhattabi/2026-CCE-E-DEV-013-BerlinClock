package com.kata.berlinclock.engines;

import com.kata.berlinclock.Lamp;
import com.kata.berlinclock.domains.BerlinMinute;

public class MinuteEngine implements ClockEngine<BerlinMinute> {

    private static final int FIVE_MINUTES_ROW_LAMP_COUNT = 11;
    private static final int ONE_MINUTE_ROW_LAMP_COUNT = 4;
    private static final int MINUTES_PER_FIVE_MINUTES_LAMP = 5;
    private static final int QUARTER_LAMP_FREQUENCY = 3;

    @Override
    public BerlinMinute translate(int minutes) {
        if (minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException();
        }

        return new BerlinMinute(translateFiveMinutes(minutes), translateOneMinutes(minutes));
    }

    private static String translateFiveMinutes(int minutes) {
        int litLampCount = minutes / MINUTES_PER_FIVE_MINUTES_LAMP;
        StringBuilder row = new StringBuilder();

        for (int position = 1; position <= FIVE_MINUTES_ROW_LAMP_COUNT; position++) {
            if (position > litLampCount) {
                row.append(Lamp.OFF.getIndicator());
            } else if (position % QUARTER_LAMP_FREQUENCY == 0) {
                row.append(Lamp.RED.getIndicator());
            } else {
                row.append(Lamp.YELLOW.getIndicator());
            }
        }

        return row.toString();
    }

    private static String translateOneMinutes(int minutes) {
        int litLampCount = minutes % MINUTES_PER_FIVE_MINUTES_LAMP;

        return Lamp.YELLOW.getIndicator().repeat(litLampCount)
                .concat(Lamp.OFF.getIndicator().repeat(ONE_MINUTE_ROW_LAMP_COUNT - litLampCount));
    }
}
