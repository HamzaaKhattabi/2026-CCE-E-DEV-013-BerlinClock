package com.kata.berlinclock.exceptions;

public class InvalidTimeException extends IllegalArgumentException {

    public InvalidTimeException(String message) {
        super(message);
    }
}
