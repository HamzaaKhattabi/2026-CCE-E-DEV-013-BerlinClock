package com.kata.berlinclock.engines;

import com.kata.berlinclock.domains.BerlinMinute;

public class MinuteEngine implements ClockEngine<BerlinMinute> {

    @Override
    public BerlinMinute translate(int time) {
        return new BerlinMinute("YYRYYRYYRYY", null);
    }
}
