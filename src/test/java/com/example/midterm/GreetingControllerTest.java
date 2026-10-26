package com.example.midterm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GreetingController.class)
class GreetingControllerTest {
    @Autowired private MockMvc mvc;

    @Test void defaultGreeting() throws Exception {
        mvc.perform(get("/api/greeting")).andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Hello, World!"));
    }

    @Test void personalizedGreeting() throws Exception {
        mvc.perform(get("/api/greeting").param("name", "Jenkins")).andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Hello, Jenkins!"))
            .andExpect(jsonPath("$.project").value("springboot-midterm"));
    }

    @Test void healthIsUp() throws Exception {
        mvc.perform(get("/api/health")).andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("UP"));
    }
}
