package com.kata.berlinclock.exceptions;

import com.kata.berlinclock.domains.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred";

    @ExceptionHandler(InvalidTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidTime(InvalidTimeException exception, HttpServletRequest request) {
        return new ErrorResponse(Instant.now(), request.getRequestURI(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpectedError(HttpServletRequest request) {
        return new ErrorResponse(Instant.now(), request.getRequestURI(), UNEXPECTED_ERROR_MESSAGE);
    }
}
