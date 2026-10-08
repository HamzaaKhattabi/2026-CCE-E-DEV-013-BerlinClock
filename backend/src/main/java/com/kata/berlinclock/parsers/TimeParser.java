package com.kata.berlinclock.parsers;

import com.kata.berlinclock.exceptions.InvalidTimeException;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Component
public class TimeParser {

    public LocalTime parse(String time) {
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException exception) {
            throw new InvalidTimeException("Time must be a valid ISO-8601 time");
        }
    }
}
