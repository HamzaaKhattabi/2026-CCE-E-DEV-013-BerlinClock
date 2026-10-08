package com.kata.berlinclock.engines;

import com.kata.berlinclock.domains.BerlinMinute;
import com.kata.berlinclock.domains.Lamp;
import com.kata.berlinclock.exceptions.InvalidTimeException;
import org.springframework.stereotype.Component;

@Component
public class MinuteEngine implements ClockEngine<BerlinMinute> {

    private static final int MIN_MINUTES = 0;
    private static final int MAX_MINUTES = 59;
    private static final int FIVE_MINUTES_ROW_LAMP_COUNT = 11;
    private static final int ONE_MINUTE_ROW_LAMP_COUNT = 4;
    private static final int MINUTES_PER_FIVE_MINUTES_LAMP = 5;
    private static final int QUARTER_LAMP_FREQUENCY = 3;

    private static final String INVALID_MINUTES_MESSAGE =
            "Minutes must be between " + MIN_MINUTES + " and " + MAX_MINUTES;

    @Override
    public BerlinMinute translate(int minutes) {
        if (minutes < MIN_MINUTES || minutes > MAX_MINUTES) {
            throw new InvalidTimeException(INVALID_MINUTES_MESSAGE);
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
