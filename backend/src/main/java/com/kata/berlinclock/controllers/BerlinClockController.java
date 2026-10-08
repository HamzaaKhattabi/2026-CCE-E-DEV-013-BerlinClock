package com.kata.berlinclock.controllers;

import com.kata.berlinclock.domains.BerlinClock;
import com.kata.berlinclock.domains.BerlinHour;
import com.kata.berlinclock.domains.BerlinMinute;
import com.kata.berlinclock.domains.BerlinSecond;
import com.kata.berlinclock.engines.HourEngine;
import com.kata.berlinclock.engines.MinuteEngine;
import com.kata.berlinclock.engines.SecondEngine;
import com.kata.berlinclock.exceptions.InvalidTimeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@RestController
public class BerlinClockController {

    private final SecondEngine secondEngine;
    private final HourEngine hourEngine;
    private final MinuteEngine minuteEngine;

    public BerlinClockController(SecondEngine secondEngine,
                                 HourEngine hourEngine,
                                 MinuteEngine minuteEngine) {
        this.secondEngine = secondEngine;
        this.hourEngine = hourEngine;
        this.minuteEngine = minuteEngine;
    }

    @GetMapping("/berlin-clock")
    public BerlinClock translate(@RequestParam("time") String time) {
        LocalTime localTime;
        try {
            localTime = LocalTime.parse(time);
        } catch (DateTimeParseException exception) {
            throw new InvalidTimeException("Time must be a valid ISO-8601 time");
        }

        BerlinSecond berlinSecond = secondEngine.translate(localTime.getSecond());
        BerlinHour berlinHour = hourEngine.translate(localTime.getHour());
        BerlinMinute berlinMinute = minuteEngine.translate(localTime.getMinute());

        return new BerlinClock(berlinSecond, berlinHour, berlinMinute);
    }
}
