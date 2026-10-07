package com.kata.berlinclock;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void exception_thrown_when_seconds_less_than_0() {
        assertThatThrownBy(() -> ClockEngine.translateSeconds(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void exception_thrown_when_seconds_greater_than_59() {
        assertThatThrownBy(() -> ClockEngine.translateSeconds(60))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void seconds_lamp_turned_off_when_seconds_is_59() {
        assertThat(ClockEngine.translateSeconds(59))
                .isEqualTo("O");
    }
}
