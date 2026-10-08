package com.kata.berlinclock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BerlinClockIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void berlin_clock_is_returned_for_a_valid_time() throws Exception {
        mockMvc.perform(get("/berlin-clock").param("time", "12:34:56"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seconds.firstRow").value("Y"))
                .andExpect(jsonPath("$.hours.firstRow").value("RROO"))
                .andExpect(jsonPath("$.hours.secondRow").value("RROO"))
                .andExpect(jsonPath("$.minutes.firstRow").value("YYRYYROOOOO"))
                .andExpect(jsonPath("$.minutes.secondRow").value("YYYY"));
    }

    @Test
    void error_response_is_returned_for_an_invalid_time() throws Exception {
        mockMvc.perform(get("/berlin-clock").param("time", "abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isString())
                .andExpect(jsonPath("$.path").value("/berlin-clock"))
                .andExpect(jsonPath("$.message").value("Time must be a valid ISO-8601 time"));
    }
}
