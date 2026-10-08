package com.kata.berlinclock.engines;

import com.kata.berlinclock.domains.BerlinMinute;

public class MinuteEngine implements ClockEngine<BerlinMinute> {

    @Override
    public BerlinMinute translate(int minutes) {
        if (minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException();
        }

        if (minutes == 59) {
            return new BerlinMinute("YYRYYRYYRYY", null);
        } else if (minutes == 0) {
            return new BerlinMinute("OOOOOOOOOOO", null);
        } else if (minutes == 15) {
            return new BerlinMinute("YYROOOOOOOO", null);
        }

        return new BerlinMinute(null, null);
    }
}
