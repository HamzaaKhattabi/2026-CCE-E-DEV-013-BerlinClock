package com.kata.berlinclock.services;

import com.kata.berlinclock.domains.BerlinClock;
import com.kata.berlinclock.domains.BerlinHour;
import com.kata.berlinclock.domains.BerlinMinute;
import com.kata.berlinclock.domains.BerlinSecond;
import com.kata.berlinclock.engines.HourEngine;
import com.kata.berlinclock.engines.MinuteEngine;
import com.kata.berlinclock.engines.SecondEngine;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class BerlinClockService {

    private final SecondEngine secondEngine;
    private final HourEngine hourEngine;
    private final MinuteEngine minuteEngine;

    public BerlinClockService(SecondEngine secondEngine,
                              HourEngine hourEngine,
                              MinuteEngine minuteEngine) {
        this.secondEngine = secondEngine;
        this.hourEngine = hourEngine;
        this.minuteEngine = minuteEngine;
    }

    public BerlinClock translate(LocalTime time) {
        BerlinSecond berlinSecond = secondEngine.translate(time.getSecond());
        BerlinHour berlinHour = hourEngine.translate(time.getHour());
        BerlinMinute berlinMinute = minuteEngine.translate(time.getMinute());

        return new BerlinClock(berlinSecond, berlinHour, berlinMinute);
    }
}
