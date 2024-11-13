package com.example.inventoryapp.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.example.inventoryapp.inventory.controller.consts.ErrorCodes.*;
import static com.example.inventoryapp.test.assertion.Assertions.assertDecreasedEventEquals;
import static com.example.inventoryapp.test.assertion.Assertions.assertUpdatedEventEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@Transactional
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InventoryIntegrationTest {
    @Container
    private static final GenericContainer<?> redisContainer = new GenericContainer<>("redis:7.2")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private OutputDestination outputDestination;

    private final String existingItemId = "1";
    private final String nonExistingItemId = "2";
    private final Long stock = 100L;

    @BeforeEach
    void setUp() {
        redisTemplate.opsForValue().set("inventory:" + existingItemId, stock.toString());
    }

    @DisplayName("재고 조회 실패")
    @Test
    void test1() throws Exception {

        mockMvc.perform(
                        get("/api/v1/inventory/{itemId}", nonExistingItemId)
                )
                .andExpect(status().isNotFound())
                .andExpectAll(
                        jsonPath("$.error.code").value(ITEM_NOT_FOUND.code),
                        jsonPath("$.error.local_message").value(ITEM_NOT_FOUND.message)
                );
    }

    @DisplayName("재고 조회 성공")
    @Test
    void test2() throws Exception {
        mockMvc.perform(
                        get("/api/v1/inventory/{itemId}", existingItemId)
                )
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.data.item_id").value(existingItemId),
                        jsonPath("$.data.stock").value(stock)
                );
    }

    @DisplayName("재고 차감 실패")
    @Test
    void test3() throws Exception {
        successGetStock(existingItemId, stock);

        final Long quantity = 110L;
        final String requestBody = "{\"quantity\":" + quantity + "}";
        mockMvc.perform(
                        post("/api/v1/inventory/{itemId}/decrease", existingItemId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.error.code").value(INSUFFICIENT_STOCK.code),
                        jsonPath("$.error.local_message").value(INSUFFICIENT_STOCK.message)
                );

        successGetStock(existingItemId, stock);
    }

    @DisplayName("재고 차감 성공")
    @Test
    void test4() throws Exception {
        successGetStock(existingItemId, stock);

        final Long quantity = 10L;
        final Long expectedStock = stock - quantity;
        final String requestBody = "{\"quantity\":" + quantity + "}";
        mockMvc.perform(
                        post("/api/v1/inventory/{itemId}/decrease", existingItemId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.data.item_id").value(existingItemId),
                        jsonPath("$.data.stock").value(stock - quantity)
                );

        successGetStock(existingItemId, expectedStock);

        final Message<byte[]> result = outputDestination.receive(1000, "inventory-out-0");
        assertDecreasedEventEquals(result, existingItemId, quantity, expectedStock);
    }

    @DisplayName("재고 수정 실패")
    @Test
    void test5() throws Exception {
        successGetStock(existingItemId, stock);

        final Long newStock = -100L;
        final String requestBody = "{\"stock\":" + newStock + "}";
        mockMvc.perform(
                        patch("/api/v1/inventory/{itemId}/stock", existingItemId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.error.code").value(INVALID_STOCK.code),
                        jsonPath("$.error.local_message").value(INVALID_STOCK.message)
                );

        successGetStock(existingItemId, stock);
    }

    @DisplayName("재고 수정 성공")
    @Test
    void test6() throws Exception {
        successGetStock(existingItemId, stock);

        final Long newStock = 1000L;
        final String requestBody = "{\"stock\":" + newStock + "}";
        mockMvc.perform(
                        patch("/api/v1/inventory/{itemId}/stock", existingItemId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.data.item_id").value(existingItemId),
                        jsonPath("$.data.stock").value(newStock)
                );

        successGetStock(existingItemId, newStock);

        final Message<byte[]> result = outputDestination.receive(1000, "inventory-out-0");
        assertUpdatedEventEquals(result, existingItemId, newStock);
    }

    @DisplayName("재고 차감, 수정 종합")
    @Test
    void test7() throws Exception {
        Long expectedStock = 100L;

        successGetStock(existingItemId, expectedStock);

        final Long decreaseQuantity = 10L;
        for (int i = 0; i < 7; i++) {
            expectedStock -= decreaseQuantity;
            final String requestBody = "{\"quantity\": " + decreaseQuantity + "}";
            mockMvc.perform(
                            post("/api/v1/inventory/{itemId}/decrease", existingItemId)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$.data.item_id").value(existingItemId),
                            jsonPath("$.data.stock").value(expectedStock)
                    );
        }

        successGetStock(existingItemId, expectedStock); // expectedStock = 30L

        final Long newStock = 500L;
        final String requestBody = "{\"stock\": " + newStock + "}";
        mockMvc.perform(
                        patch("/api/v1/inventory/{itemId}/stock", existingItemId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.item_id").value(existingItemId))
                .andExpect(jsonPath("$.data.stock").value(newStock));

        successGetStock(existingItemId, newStock);

        Long prevStock = 100L;
        for (int i = 0; i < 7; i++) {
            prevStock -= decreaseQuantity;
            final Message<byte[]> result = outputDestination.receive(1000, "inventory-out-0");
            assertDecreasedEventEquals(result, existingItemId, decreaseQuantity, prevStock);
        }

        final Message<byte[]> result = outputDestination.receive(1000, "inventory-out-0");
        assertUpdatedEventEquals(result, existingItemId, newStock);
    }

    private void successGetStock(String itemId, Long stock) throws Exception {
        mockMvc.perform(
                        get("/api/v1/inventory/{itemId}", itemId)
                )
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.data.item_id").value(itemId),
                        jsonPath("$.data.stock").value(stock)
                );
    }

    @AfterEach
    void tearDown() {
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
    }
}
