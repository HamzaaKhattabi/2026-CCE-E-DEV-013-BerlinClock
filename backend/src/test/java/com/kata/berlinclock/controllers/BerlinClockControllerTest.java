package com.kata.berlinclock.controllers;

import com.kata.berlinclock.domains.BerlinHour;
import com.kata.berlinclock.domains.BerlinMinute;
import com.kata.berlinclock.domains.BerlinSecond;
import com.kata.berlinclock.engines.HourEngine;
import com.kata.berlinclock.engines.MinuteEngine;
import com.kata.berlinclock.engines.SecondEngine;
import com.kata.berlinclock.exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BerlinClockControllerTest {

    MockMvc mockMvc;

    SecondEngine secondEngine;
    HourEngine hourEngine;
    MinuteEngine minuteEngine;

    @BeforeEach
    void setup() {
        secondEngine = mock(SecondEngine.class);
        hourEngine = mock(HourEngine.class);
        minuteEngine = mock(MinuteEngine.class);

        mockMvc = MockMvcBuilders
                .standaloneSetup(new BerlinClockController(
                        secondEngine,
                        hourEngine,
                        minuteEngine))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void seconds_row_is_returned_when_time_is_given() throws Exception {
        when(secondEngine.translate(anyInt()))
                .thenReturn(new BerlinSecond("Y"));

        mockMvc.perform(get("/berlin-clock").param("time", "00:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seconds.firstRow").value("Y"));
    }

    @Test
    void controller_return_the_entire_dto_based_on_engine_results() throws Exception {
        when(secondEngine.translate(anyInt()))
                .thenReturn(new BerlinSecond("O"));
        when(hourEngine.translate(anyInt()))
                .thenReturn(new BerlinHour("RRRR", "RRRO"));
        when(minuteEngine.translate(anyInt()))
                .thenReturn(new BerlinMinute("YYRYYRYYRYY", "YYYY"));

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
        when(hourEngine.translate(25))
                .thenThrow(new IllegalArgumentException("Hours must be between 0 and 23"));

        mockMvc.perform(get("/berlin-clock").param("time", "25:00:00"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Hours must be between 0 and 23"));
    }

    @Test
    void error_dto_has_timestamp_path_and_message_as_strings() throws Exception {
        when(hourEngine.translate(anyInt()))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/berlin-clock").param("time", "12:00:00"))
                .andExpect(jsonPath("$.timestamp").isString())
                .andExpect(jsonPath("$.path").isString())
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    void error_message_is_returned_when_an_unexpected_error_occurs() throws Exception {
        when(hourEngine.translate(anyInt()))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/berlin-clock").param("time", "12:00:00"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }
}
