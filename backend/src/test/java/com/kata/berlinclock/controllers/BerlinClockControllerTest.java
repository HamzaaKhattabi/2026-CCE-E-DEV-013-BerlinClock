package com.kata.berlinclock.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BerlinClockControllerTest {

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new BerlinClockController())
                .build();
    }

    @Test
    void seconds_row_is_returned_when_time_is_given() throws Exception {
        mockMvc.perform(get("/berlin-clock").param("time", "00:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seconds.firstRow").value("Y"));
    }

    @Test
    void berlin_clock_matches_the_highest_time_provided() throws Exception {
        mockMvc.perform(get("/berlin-clock").param("time", "23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seconds.firstRow").value("O"))
                .andExpect(jsonPath("$.hours.firstRow").value("RRRR"))
                .andExpect(jsonPath("$.hours.secondRow").value("RRRO"))
                .andExpect(jsonPath("$.minutes.firstRow").value("YYRYYRYYRYY"))
                .andExpect(jsonPath("$.minutes.secondRow").value("YYYY"));
    }
}
