package com.kata.berlinclock.parsers;

import com.kata.berlinclock.exceptions.InvalidTimeException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeParserTest {

    TimeParser timeParser = new TimeParser();

    @Test
    void exception_thrown_when_time_is_null() {
        assertThatThrownBy(() -> timeParser.parse(null))
                .isInstanceOf(InvalidTimeException.class)
                .hasMessage("Time must be a valid ISO-8601 time");
    }
}
