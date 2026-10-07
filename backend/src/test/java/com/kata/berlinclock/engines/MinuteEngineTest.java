package com.kata.berlinclock.engines;

import com.kata.berlinclock.domains.BerlinMinute;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MinuteEngineTest {

    MinuteEngine secondEngine = new MinuteEngine();

    @Test
    void all_five_minutes_lamps_turned_on_when_minutes_is_59() {
        BerlinMinute berlinMinute = secondEngine.translate(59);

        assertThat(berlinMinute.firstRow())
                .isEqualTo("YYRYYRYYRYY");
    }

    @Test
    void all_five_minutes_lamps_turned_off_when_minutes_is_0() {
        BerlinMinute berlinMinute = secondEngine.translate(0);
        assertThat(berlinMinute.firstRow())
                .isEqualTo("OOOOOOOOOOO");
    }
}
