package com.kata.berlinclock.engines;

import com.kata.berlinclock.domains.BerlinHour;
import com.kata.berlinclock.exceptions.InvalidTimeException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HourEngineTest {

    HourEngine hourEngine = new HourEngine();

    @Test
    void all_five_hours_lamps_turned_on_when_hours_is_20() {
        BerlinHour berlinHour = hourEngine.translate(20);
        assertThat(berlinHour.firstRow())
                .isEqualTo("RRRR");
    }

    @Test
    void two_five_hours_lamps_turned_on_when_hours_is_10() {
        BerlinHour berlinHour = hourEngine.translate(10);
        assertThat(berlinHour.firstRow())
                .isEqualTo("RROO");
    }

    @Test
    void one_five_hours_lamp_turned_on_when_hour_is_5() {
        BerlinHour berlinHour = hourEngine.translate(5);
        assertThat(berlinHour.firstRow())
                .isEqualTo("ROOO");
    }

    @Test
    void three_five_hours_lamps_turned_on_when_hour_is_18() {
        BerlinHour berlinHour = hourEngine.translate(18);
        assertThat(berlinHour.firstRow())
                .isEqualTo("RRRO");
    }

    @Test
    void all_one_hours_lamps_turned_on_when_hour_is_4() {
        BerlinHour berlinHour = hourEngine.translate(4);
        assertThat(berlinHour.secondRow())
                .isEqualTo("RRRR");
    }

    @Test
    void all_one_hours_lamps_turned_on_when_hour_is_14() {
        BerlinHour berlinHour = hourEngine.translate(14);
        assertThat(berlinHour.secondRow())
                .isEqualTo("RRRR");
    }

    @Test
    void two_one_hours_lamps_turned_on_when_hour_is_22() {
        BerlinHour berlinHour = hourEngine.translate(22);
        assertThat(berlinHour.secondRow())
                .isEqualTo("RROO");
    }

    @Test
    void all_one_hours_lamps_turned_off_when_hours_is_5() {
        BerlinHour berlinHour = hourEngine.translate(5);
        assertThat(berlinHour.secondRow())
                .isEqualTo("OOOO");
    }

    @Test
    void hours_translated_into_five_hours_row_and_one_hours_row_when_hours_is_15() {
        BerlinHour berlinHour = hourEngine.translate(15);
        assertThat(berlinHour)
                .isEqualTo(new BerlinHour("RRRO", "OOOO"));
    }

    @Test
    void both_rows_combined_when_hours_is_16() {
        BerlinHour berlinHour = hourEngine.translate(16);

        assertThat(berlinHour)
                .isEqualTo(new BerlinHour("RRRO", "ROOO"));
    }

    @Test
    void both_rows_combined_when_hours_is_23() {
        BerlinHour berlinHour = hourEngine.translate(23);

        assertThat(berlinHour)
                .isEqualTo(new BerlinHour("RRRR", "RRRO"));
    }

    @Test
    void both_rows_combined_when_hours_is_0() {
        BerlinHour berlinHour = hourEngine.translate(0);

        assertThat(berlinHour)
                .isEqualTo(new BerlinHour("OOOO", "OOOO"));
    }

    @Test
    void exception_message_states_valid_range_when_hours_less_than_0() {
        assertThatThrownBy(() -> hourEngine.translate(-1))
                .isInstanceOf(InvalidTimeException.class)
                .hasMessage("Hours must be between 0 and 23");
    }

    @Test
    void exception_message_states_valid_range_when_hours_greater_than_23() {
        assertThatThrownBy(() -> hourEngine.translate(24))
                .isInstanceOf(InvalidTimeException.class)
                .hasMessage("Hours must be between 0 and 23");
    }
}
