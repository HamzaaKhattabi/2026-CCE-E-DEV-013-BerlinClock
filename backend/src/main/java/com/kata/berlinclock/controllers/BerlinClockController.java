package com.kata.berlinclock.controllers;

import com.kata.berlinclock.domains.BerlinClock;
import com.kata.berlinclock.domains.BerlinHour;
import com.kata.berlinclock.domains.BerlinMinute;
import com.kata.berlinclock.domains.BerlinSecond;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BerlinClockController {

    public BerlinClockController() {}

    @GetMapping("/berlin-clock")
    public BerlinClock translate(@RequestParam("time") String time) {
        return new BerlinClock(
                new BerlinSecond("Y"),
                new BerlinHour(null, null),
                new BerlinMinute(null, null)
        );
    }
}
