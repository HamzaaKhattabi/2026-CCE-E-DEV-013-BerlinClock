package com.kata.berlinclock.engines;

public interface ClockEngine<T> {
    T translate(int time);
}
