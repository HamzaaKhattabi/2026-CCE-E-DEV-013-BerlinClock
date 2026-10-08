package com.kata.berlinclock.controllers;

import com.kata.berlinclock.domains.BerlinClock;
import com.kata.berlinclock.domains.BerlinHour;
import com.kata.berlinclock.domains.BerlinMinute;
import com.kata.berlinclock.domains.BerlinSecond;
import com.kata.berlinclock.domains.ErrorResponse;
import com.kata.berlinclock.engines.HourEngine;
import com.kata.berlinclock.engines.MinuteEngine;
import com.kata.berlinclock.engines.SecondEngine;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestController
public class BerlinClockController {

    private final SecondEngine secondEngine;
    private final HourEngine hourEngine;
    private final MinuteEngine minuteEngine;

    public BerlinClockController(SecondEngine secondEngine,
                                 HourEngine hourEngine,
                                 MinuteEngine minuteEngine) {
        this.secondEngine = secondEngine;
        this.hourEngine = hourEngine;
        this.minuteEngine = minuteEngine;
    }

    @GetMapping("/berlin-clock")
    public BerlinClock translate(@RequestParam("time") String time) {
        List<Integer> timeParts = Arrays.stream(time.split(":"))
                .map(Integer::parseInt)
                .toList();

        BerlinSecond berlinSecond = secondEngine.translate(timeParts.get(2));
        BerlinHour berlinHour = hourEngine.translate(timeParts.get(0));
        BerlinMinute berlinMinute = minuteEngine.translate(timeParts.get(1));

        return new BerlinClock(berlinSecond, berlinHour, berlinMinute);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidTime(IllegalArgumentException exception, HttpServletRequest request) {
        return new ErrorResponse(Instant.now(), request.getRequestURI(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpectedError(HttpServletRequest request) {
        return new ErrorResponse(Instant.now(), request.getRequestURI(), "An unexpected error occurred");
    }
}
