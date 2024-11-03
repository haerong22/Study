package com.example.inventoryapp.inventory.controller;

import com.example.inventoryapp.config.JsonConfig;
import com.example.inventoryapp.inventory.service.InventoryService;
import com.example.inventoryapp.inventory.service.domain.Inventory;
import com.example.inventoryapp.inventory.service.exception.InsufficientStockException;
import com.example.inventoryapp.inventory.service.exception.InvalidDecreaseQuantityException;
import com.example.inventoryapp.inventory.service.exception.ItemNotFoundException;
import com.example.inventoryapp.test.exception.NotImplementedTestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(JsonConfig.class)
@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {
    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("재고 조회")
    @Nested
    class GetStock {
        final String itemId = "1";
        final Long stock = 100L;

        @DisplayName("자산이 존재하지 않을 경우, 404 status 와 error 를 반환한다.")
        @Test
        void test1() throws Exception {
            // given
            given(inventoryService.findByItemId(itemId))
                    .willReturn(null);

            // when, then
            mockMvc.perform(get("/api/v1/inventory/{itemId}", itemId))
                    .andExpectAll(
                            status().isNotFound(),
                            jsonPath("$.error.code").value(1000L),
                            jsonPath("$.error.local_message").value("자산이 존재하지 않습니다.")
                    );
        }

        @DisplayName("정상인 경우, 200 status 와 결과를 반환하다.")
        @Test
        void test1000() throws Exception {
            // given
            final Inventory inventory = new Inventory(itemId, stock);
            given(inventoryService.findByItemId(itemId))
                    .willReturn(inventory);

            // when, then
            mockMvc.perform(get("/api/v1/inventory/{itemId}", itemId))
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.data.item_id").value(itemId),
                            jsonPath("$.data.stock").value(stock)
                    );
        }
    }

    @DisplayName("재고 차감")
    @Nested
    class DecreaseQuantity {
        final String itemId = "1";
        final Long quantity = 10L;
        final Long stock = 9L;

        @DisplayName("자산이 존재하지 않을 경우, 404 status 와 error 를 반환한다.")
        @Test
        void test1() throws Exception {
            // given
            given(inventoryService.decreaseByItemId(itemId, quantity))
                    .willThrow(ItemNotFoundException.class);

            final Map<String, Object> requestBody = Map.of("quantity", quantity);

            // when, then
            mockMvc.perform(
                            post("/api/v1/inventory/{itemId}/decrease", itemId)
                                    .content(objectMapper.writeValueAsString(requestBody))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isNotFound())
                    .andExpectAll(
                            jsonPath("$.error.code").value(1000L),
                            jsonPath("$.error.local_message").value("자산이 존재하지 않습니다.")
                    );
        }

        @DisplayName("재고가 부족할 경우, 400 status 와 error 를 반환한다.")
        @Test
        void test2() throws Exception {
            // given
            given(inventoryService.decreaseByItemId(itemId, quantity))
                    .willThrow(InsufficientStockException.class);

            final Map<String, Object> requestBody = Map.of("quantity", quantity);

            // when, then
            mockMvc.perform(
                            post("/api/v1/inventory/{itemId}/decrease", itemId)
                                    .content(objectMapper.writeValueAsString(requestBody))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpectAll(
                            jsonPath("$.error.code").value(1001L),
                            jsonPath("$.error.local_message").value("재고가 부족합니다.")
                    );
        }

        @DisplayName("차감 수량이 유효하지 않은 경우, 400 status 와 error 를 반환한다.")
        @Test
        void test3() throws Exception {
            // given
            given(inventoryService.decreaseByItemId(itemId, quantity))
                    .willThrow(InvalidDecreaseQuantityException.class);

            final Map<String, Object> requestBody = Map.of("quantity", quantity);

            // when, then
            mockMvc.perform(
                            post("/api/v1/inventory/{itemId}/decrease", itemId)
                                    .content(objectMapper.writeValueAsString(requestBody))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpectAll(
                            jsonPath("$.error.code").value(1002L),
                            jsonPath("$.error.local_message").value("차감 수량이 유효하지 않습니다.")
                    );
        }

        @DisplayName("정상인 경우, 200 status 와 결과를 반환하다.")
        @Test
        void test1000() throws Exception {
            // given
            final Inventory inventory = new Inventory(itemId, stock);
            given(inventoryService.decreaseByItemId(itemId, quantity))
                    .willReturn(inventory);

            final Map<String, Object> requestBody = Map.of("quantity", quantity);

            // when, then
            mockMvc.perform(
                            post("/api/v1/inventory/{itemId}/decrease", itemId)
                                    .content(objectMapper.writeValueAsString(requestBody))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$.data.item_id").value(itemId),
                            jsonPath("$.data.stock").value(stock)
                    );
        }
    }

    @DisplayName("재고 수정")
    @Nested
    class UpdateStock {
        @DisplayName("자산이 존재하지 않을 경우, 404 status 와 error 를 반환한다.")
        @Test
        void test1() {
            throw new NotImplementedTestException();
        }

        @DisplayName("수정하려는 재고가 유효하지 않은 경우, 400 status 와 error 를 반환한다.")
        @Test
        void test2() {
            throw new NotImplementedTestException();
        }

        @DisplayName("정상인 경우, 200 status 와 결과를 반환하다.")
        @Test
        void test1000() {
            throw new NotImplementedTestException();
        }
    }
}
