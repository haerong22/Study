package com.example.inventoryapp.test.assertion;

import com.example.inventoryapp.inventory.controller.consts.ErrorCodes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Assertions {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());

    public static void assertMvcErrorEquals(
            MvcResult result,
            ErrorCodes errorCodes
    ) throws UnsupportedEncodingException, JsonProcessingException {
        final String content = result.getResponse().getContentAsString();
        final JsonNode responseBody = objectMapper.readTree(content);
        final JsonNode errorField = responseBody.get("error");

        assertNotNull(errorField);
        assertEquals(errorCodes.code, errorField.get("code").asInt());
        assertEquals(errorCodes.message, errorField.get("local_message").asText());
    }

    public static void assertMvcDataEquals(
            MvcResult result,
            Consumer<JsonNode> consumer
    ) throws UnsupportedEncodingException, JsonProcessingException {
        final String content = result.getResponse().getContentAsString();
        final JsonNode responseBody = objectMapper.readTree(content);
        final JsonNode dataField = responseBody.get("data");
        assertNotNull(dataField);
        consumer.accept(dataField);
    }
}
