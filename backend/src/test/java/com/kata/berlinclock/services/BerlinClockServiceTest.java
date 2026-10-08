package com.kata.berlinclock.services;

import com.kata.berlinclock.domains.BerlinClock;
import com.kata.berlinclock.domains.BerlinHour;
import com.kata.berlinclock.domains.BerlinMinute;
import com.kata.berlinclock.domains.BerlinSecond;
import com.kata.berlinclock.engines.HourEngine;
import com.kata.berlinclock.engines.MinuteEngine;
import com.kata.berlinclock.engines.SecondEngine;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class BerlinClockServiceTest {

    BerlinClockService berlinClockService = new BerlinClockService(
            new SecondEngine(),
            new HourEngine(),
            new MinuteEngine());

    @Test
    void each_time_part_is_translated_by_its_own_engine() {
        BerlinClock berlinClock = berlinClockService.translate(LocalTime.of(12, 34, 56));

        assertThat(berlinClock)
                .isEqualTo(new BerlinClock(
                        new BerlinSecond("Y"),
                        new BerlinHour("RROO", "RROO"),
                        new BerlinMinute("YYRYYROOOOO", "YYYY")));
    }
}
