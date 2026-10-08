package com.kata.berlinclock.engines;

import com.kata.berlinclock.domains.BerlinMinute;
import com.kata.berlinclock.exceptions.InvalidTimeException;
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
        assertThatThrownBy(() -> minuteEngine.translate(60))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void one_five_minutes_lamp_turned_on_when_minutes_is_5() {
        BerlinMinute berlinMinute = minuteEngine.translate(5);
        assertThat(berlinMinute.firstRow())
                .isEqualTo("YOOOOOOOOOO");
    }

    @Test
    void six_five_minutes_lamps_turned_on_when_minutes_is_30() {
        BerlinMinute berlinMinute = minuteEngine.translate(30);
        assertThat(berlinMinute.firstRow())
                .isEqualTo("YYRYYROOOOO");
    }

    @Test
    void all_one_minute_lamps_turned_off_when_minutes_is_0() {
        BerlinMinute berlinMinute = minuteEngine.translate(0);
        assertThat(berlinMinute.secondRow())
                .isEqualTo("OOOO");
    }

    @Test
    void four_one_minute_lamps_turned_on_when_minutes_is_4() {
        BerlinMinute berlinMinute = minuteEngine.translate(4);
        assertThat(berlinMinute.secondRow())
                .isEqualTo("YYYY");
    }

    @Test
    void both_rows_combined_when_minutes_is_16() {
        BerlinMinute berlinMinute = minuteEngine.translate(16);

        assertThat(berlinMinute)
                .isEqualTo(new BerlinMinute("YYROOOOOOOO", "YOOO"));
    }

    @Test
    void both_rows_combined_when_minutes_is_34() {
        BerlinMinute berlinMinute = minuteEngine.translate(34);

        assertThat(berlinMinute)
                .isEqualTo(new BerlinMinute("YYRYYROOOOO", "YYYY"));
    }

    @Test
    void both_rows_combined_when_minutes_is_59() {
        BerlinMinute berlinMinute = minuteEngine.translate(59);

        assertThat(berlinMinute)
                .isEqualTo(new BerlinMinute("YYRYYRYYRYY", "YYYY"));
    }

    @Test
    void exception_message_states_valid_range_when_minutes_less_than_0() {
        assertThatThrownBy(() -> minuteEngine.translate(-1))
                .isInstanceOf(InvalidTimeException.class)
                .hasMessage("Minutes must be between 0 and 59");
    }

    @Test
    void exception_message_states_valid_range_when_minutes_greater_than_59() {
        assertThatThrownBy(() -> minuteEngine.translate(60))
                .isInstanceOf(InvalidTimeException.class)
                .hasMessage("Minutes must be between 0 and 59");
    }
}
