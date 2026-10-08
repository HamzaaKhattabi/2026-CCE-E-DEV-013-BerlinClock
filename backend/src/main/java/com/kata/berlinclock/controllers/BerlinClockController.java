package com.kata.berlinclock.controllers;

import com.kata.berlinclock.domains.BerlinClock;
import com.kata.berlinclock.parsers.TimeParser;
import com.kata.berlinclock.services.BerlinClockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
public class BerlinClockController {

    private final TimeParser timeParser;
    private final BerlinClockService berlinClockService;

    public BerlinClockController(TimeParser timeParser,
                                 BerlinClockService berlinClockService) {
        this.timeParser = timeParser;
        this.berlinClockService = berlinClockService;
    }

    @GetMapping("/berlin-clock")
    public BerlinClock translate(@RequestParam("time") String time) {
        LocalTime localTime = timeParser.parse(time);

        return berlinClockService.translate(localTime);
    }
}
