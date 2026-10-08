package com.kata.berlinclock.exceptions;

import java.time.Instant;

public record ErrorResponse(Instant timestamp, String path, String message) {
}
