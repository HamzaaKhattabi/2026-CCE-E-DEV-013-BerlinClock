package com.kata.berlinclock.engines;

import com.kata.berlinclock.domains.BerlinMinute;

public class MinuteEngine implements ClockEngine<BerlinMinute> {

    @Override
    public BerlinMinute translate(int time) {
        if (time == 59) {
            return new BerlinMinute("YYRYYRYYRYY", null);
        } else if (time == 0) {
            return new BerlinMinute("OOOOOOOOOOO", null);
        }

        return new BerlinMinute(null, null);
    }
}
