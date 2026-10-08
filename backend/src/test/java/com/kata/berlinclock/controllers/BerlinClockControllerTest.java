package com.kata.berlinclock.controllers;

import com.kata.berlinclock.domains.BerlinClock;
import com.kata.berlinclock.domains.BerlinHour;
import com.kata.berlinclock.domains.BerlinMinute;
import com.kata.berlinclock.domains.BerlinSecond;
import com.kata.berlinclock.exceptions.GlobalExceptionHandler;
import com.kata.berlinclock.parsers.TimeParser;
import com.kata.berlinclock.services.BerlinClockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BerlinClockControllerTest {

    MockMvc mockMvc;

    BerlinClockService berlinClockService;

    @BeforeEach
    void setup() {
        berlinClockService = mock(BerlinClockService.class);

        mockMvc = MockMvcBuilders
                .standaloneSetup(new BerlinClockController(
                        new TimeParser(),
                        berlinClockService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void seconds_row_is_returned_when_time_is_given() throws Exception {
        when(berlinClockService.translate(LocalTime.of(0, 0, 0)))
                .thenReturn(new BerlinClock(new BerlinSecond("Y"), null, null));

        mockMvc.perform(get("/berlin-clock").param("time", "00:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seconds.firstRow").value("Y"));
    }

    @Test
    void controller_returns_the_entire_dto_based_on_service_result() throws Exception {
        when(berlinClockService.translate(LocalTime.of(23, 59, 59)))
                .thenReturn(new BerlinClock(
                        new BerlinSecond("O"),
                        new BerlinHour("RRRR", "RRRO"),
                        new BerlinMinute("YYRYYRYYRYY", "YYYY")));

        mockMvc.perform(get("/berlin-clock").param("time", "23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seconds.firstRow").value("O"))
                .andExpect(jsonPath("$.hours.firstRow").value("RRRR"))
                .andExpect(jsonPath("$.hours.secondRow").value("RRRO"))
                .andExpect(jsonPath("$.minutes.firstRow").value("YYRYYRYYRYY"))
                .andExpect(jsonPath("$.minutes.secondRow").value("YYYY"));
    }

    @Test
    void bad_request_with_error_message_is_returned_when_time_is_not_accepted() throws Exception {
        when(berlinClockService.translate(LocalTime.of(12, 0, 0)))
                .thenThrow(new IllegalArgumentException("Hours must be between 0 and 23"));

        mockMvc.perform(get("/berlin-clock").param("time", "12:00:00"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Hours must be between 0 and 23"));
    }

    @Test
    void error_dto_has_timestamp_path_and_message_as_strings() throws Exception {
        when(berlinClockService.translate(any(LocalTime.class)))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/berlin-clock").param("time", "12:00:00"))
                .andExpect(jsonPath("$.timestamp").isString())
                .andExpect(jsonPath("$.path").isString())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void error_message_is_returned_when_an_unexpected_error_occurs() throws Exception {
        when(berlinClockService.translate(any(LocalTime.class)))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/berlin-clock").param("time", "12:00:00"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }

    @Test
    void bad_request_is_returned_when_time_is_not_a_time() throws Exception {
        mockMvc.perform(get("/berlin-clock").param("time", "abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Time must be a valid ISO-8601 time"));
    }

    @Test
    void bad_request_is_returned_when_time_is_out_of_range() throws Exception {
        mockMvc.perform(get("/berlin-clock").param("time", "25:00:00"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Time must be a valid ISO-8601 time"));
    }
}
