package com.kata.berlinclock.engines;

import com.kata.berlinclock.domains.BerlinSecond;
import com.kata.berlinclock.domains.Lamp;
import com.kata.berlinclock.exceptions.InvalidTimeException;
import org.springframework.stereotype.Component;

@Component
public class SecondEngine implements ClockEngine<BerlinSecond> {

    private static final int MIN_SECONDS = 0;
    private static final int MAX_SECONDS = 59;
    private static final String INVALID_SECONDS_MESSAGE =
            "Seconds must be between " + MIN_SECONDS + " and " + MAX_SECONDS;

    @Override
    public BerlinSecond translate(int seconds) {
        if (seconds < MIN_SECONDS || seconds > MAX_SECONDS) {
            throw new InvalidTimeException(INVALID_SECONDS_MESSAGE);
        }

        boolean isEven = seconds % 2 == 0;
        String lampIndicator = isEven ? Lamp.YELLOW.getIndicator() : Lamp.OFF.getIndicator();

        return new BerlinSecond(lampIndicator);
    }
}
