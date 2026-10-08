package com.kata.berlinclock.engines;

import com.kata.berlinclock.Lamp;
import com.kata.berlinclock.domains.BerlinHour;
import com.kata.berlinclock.exceptions.InvalidTimeException;
import org.springframework.stereotype.Component;

@Component
public class HourEngine implements ClockEngine<BerlinHour> {

    private static final int ROW_LAMP_COUNT = 4;
    private static final int HOURS_PER_FIVE_HOURS_LAMP = 5;

    @Override
    public BerlinHour translate(int hours) {
        if (hours < 0 || hours > 23) {
            throw new InvalidTimeException("Hours must be between 0 and 23");
        }
        String firstRow = translateFiveHours(hours);
        String secondRow = translateOneHours(hours);

        return new BerlinHour(firstRow, secondRow);
    }

    private static String translateFiveHours(int hours) {
        int litLampCount = hours / HOURS_PER_FIVE_HOURS_LAMP;

        return Lamp.RED.getIndicator().repeat(litLampCount)
                .concat(Lamp.OFF.getIndicator().repeat(ROW_LAMP_COUNT - litLampCount));
    }

    private static String translateOneHours(int hours) {
        int litLampCount = hours % HOURS_PER_FIVE_HOURS_LAMP;

        return Lamp.RED.getIndicator().repeat(litLampCount)
                .concat(Lamp.OFF.getIndicator().repeat(ROW_LAMP_COUNT - litLampCount));
    }
}
