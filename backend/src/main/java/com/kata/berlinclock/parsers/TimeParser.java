package com.kata.berlinclock.parsers;

import com.kata.berlinclock.exceptions.InvalidTimeException;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Component
public class TimeParser {

    private static final String INVALID_TIME_MESSAGE = "Time must be a valid ISO-8601 time";

    public LocalTime parse(String time) {
        if (time == null) {
            throw new InvalidTimeException(INVALID_TIME_MESSAGE);
        }

        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException exception) {
            throw new InvalidTimeException(INVALID_TIME_MESSAGE);
        }
    }
}
