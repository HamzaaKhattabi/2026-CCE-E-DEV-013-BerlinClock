package com.kata.berlinclock.domains;

import java.time.Instant;

public record ErrorResponse(Instant timestamp, String path, String message) {
}
