package com.kata.berlinclock.engines;

import com.kata.berlinclock.domains.BerlinMinute;

public class MinuteEngine implements ClockEngine<BerlinMinute> {

    @Override
    public BerlinMinute translate(int minutes) {
        if (minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException();
        }

        int litLampCount = minutes / 5;
        StringBuilder firstRow = new StringBuilder();
        for (int i = 1; i <= 11; i++) {
            if (i > litLampCount) {
                firstRow.append("O");
            } else if (i % 3 == 0) {
                firstRow.append("R");
            } else {
                firstRow.append("Y");
            }
        }

        return new BerlinMinute(firstRow.toString(), null);
    }
}
