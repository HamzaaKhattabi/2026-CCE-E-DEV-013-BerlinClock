package com.kata.berlinclock.engines;

import com.kata.berlinclock.domains.BerlinSecond;
import com.kata.berlinclock.exceptions.InvalidTimeException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SecondEngineTest {

    SecondEngine secondEngine = new SecondEngine();

    @Test
    void seconds_lamp_turned_on_when_seconds_even() {
        BerlinSecond berlinSecond = secondEngine.translate(0);
        assertThat(berlinSecond.firstRow())
                .isEqualTo("Y");
    }

    @Test
    void seconds_lamp_turned_off_when_seconds_odd() {
        BerlinSecond berlinSecond = secondEngine.translate(1);
        assertThat(berlinSecond.firstRow())
                .isEqualTo("O");
    }

    @Test
    void seconds_lamp_turned_off_when_seconds_is_59() {
        BerlinSecond berlinSecond = secondEngine.translate(59);
        assertThat(berlinSecond.firstRow())
                .isEqualTo("O");
    }

    @Test
    void exception_message_states_valid_range_when_seconds_less_than_0() {
        assertThatThrownBy(() -> secondEngine.translate(-1))
                .isInstanceOf(InvalidTimeException.class)
                .hasMessage("Seconds must be between 0 and 59");
    }

    @Test
    void exception_message_states_valid_range_when_seconds_greater_than_59() {
        assertThatThrownBy(() -> secondEngine.translate(60))
                .isInstanceOf(InvalidTimeException.class)
                .hasMessage("Seconds must be between 0 and 59");
    }
}
