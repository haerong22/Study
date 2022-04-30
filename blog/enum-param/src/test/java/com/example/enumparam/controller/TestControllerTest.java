package com.example.enumparam.controller;

import com.example.enumparam.domain.Developer;
import com.example.enumparam.domain.Position;
import javafx.geometry.Pos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = TestController.class)
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void queryStringEnum() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/enum")
                .param("position", "dba");

        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.content().string("success"));

    }

    @Test
    void queryStringObj() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/obj")
                .param("position", "frontend")
                .param("name", "kim");

        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.content().string("success"));
    }

    @Test
    void json() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/json")
                .content("{\"name\": \"kim\", \"position\":\"dba\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.content().string("success"));

        Position position = Position.DBA;

        Position.class.getEnumConstants();
    }
}