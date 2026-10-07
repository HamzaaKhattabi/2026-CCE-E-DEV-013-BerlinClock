package com.kata.berlinclock;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClockEngineTest {

    @Test
    void seconds_lamp_turned_on_when_seconds_even() {
        assertThat(ClockEngine.translateSeconds(0))
                .isEqualTo("Y");
    }

    @Test
    void seconds_lamp_turned_off_when_seconds_odd() {
        assertThat(ClockEngine.translateSeconds(1))
                .isEqualTo("O");
    }
}
