package com.kata.berlinclock.controllers;

import com.kata.berlinclock.domains.BerlinClock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BerlinClockController {

    public BerlinClockController() {

    }

    @GetMapping("/berlin-clock")
    public BerlinClock translate(@RequestParam String time) {
        return null;
    }
}
