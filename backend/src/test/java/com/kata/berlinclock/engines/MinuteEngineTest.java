package com.kata.berlinclock.engines;

import com.kata.berlinclock.domains.BerlinMinute;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MinuteEngineTest {

    MinuteEngine minuteEngine = new MinuteEngine();

    @Test
    void all_five_minutes_lamps_turned_on_when_minutes_is_59() {
        BerlinMinute berlinMinute = minuteEngine.translate(59);

        assertThat(berlinMinute.firstRow())
                .isEqualTo("YYRYYRYYRYY");
    }

    @Test
    void all_five_minutes_lamps_turned_off_when_minutes_is_0() {
        BerlinMinute berlinMinute = minuteEngine.translate(0);
        assertThat(berlinMinute.firstRow())
                .isEqualTo("OOOOOOOOOOO");
    }

    @Test
    void three_five_minutes_lamps_turned_on_when_minutes_is_15() {
        BerlinMinute berlinMinute = minuteEngine.translate(15);
        assertThat(berlinMinute.firstRow())
                .isEqualTo("YYROOOOOOOO");
    }

    @Test
    void exception_thrown_when_minutes_less_than_0() {
        assertThatThrownBy(() -> minuteEngine.translate(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void exception_thrown_when_minutes_greater_than_59() {
        assertThatThrownBy(() -> minuteEngine.translate(59))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
