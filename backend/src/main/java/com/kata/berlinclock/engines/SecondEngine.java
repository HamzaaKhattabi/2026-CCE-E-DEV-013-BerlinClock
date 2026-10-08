package com.kata.berlinclock.engines;


import com.kata.berlinclock.Lamp;
import com.kata.berlinclock.domains.BerlinSecond;
import com.kata.berlinclock.exceptions.InvalidTimeException;
import org.springframework.stereotype.Component;

@Component
public class SecondEngine implements ClockEngine<BerlinSecond> {

    @Override
    public BerlinSecond translate(int seconds) {
        boolean isEven = seconds % 2 == 0;

        if (seconds < 0 || seconds > 59) {
            throw new InvalidTimeException();
        }

        String lampIndicator = isEven ? Lamp.YELLOW.getIndicator() : Lamp.OFF.getIndicator();

        return new BerlinSecond(lampIndicator);
    }
}
