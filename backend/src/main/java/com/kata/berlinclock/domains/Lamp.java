package com.kata.berlinclock.domains;

public enum Lamp {
    OFF("O"),
    YELLOW("Y"),
    RED("R");

    private final String indicator;

    Lamp(String indicator) {
        this.indicator = indicator;
    }

    public String getIndicator() {
        return indicator;
    }
}
