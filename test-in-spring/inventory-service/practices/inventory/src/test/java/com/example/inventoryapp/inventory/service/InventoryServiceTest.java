package com.example.inventoryapp.inventory.service;

import com.example.inventoryapp.inventory.repository.InventoryJpaRepository;
import com.example.inventoryapp.inventory.repository.InventoryJpaRepositoryStub;
import com.example.inventoryapp.inventory.service.domain.Inventory;
import com.example.inventoryapp.test.exception.NotImplementedTestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTest {
    private InventoryService sut;
    private InventoryJpaRepository inventoryJpaRepository;

    @BeforeEach
    void setUpAll() {
        inventoryJpaRepository = new InventoryJpaRepositoryStub();
        sut = new InventoryService(inventoryJpaRepository);
    }

    @Nested
    class FindByItemId {
        final String itemId = "1";
        final Long stock = 10L;

        @BeforeEach
        void setUp() {
            inventoryJpaRepository.addInventoryEntity(itemId, stock);
        }

        @DisplayName("itemId를 갖는 entity 를 찾지 못하면, null 을 반환한다.")
        @Test
        void test1() {
            // given
            final String nonExistingItemId = "2";

            // when
            final Inventory result = sut.findByItemId(nonExistingItemId);

            // then
            assertNull(result);
        }

        @DisplayName("itemId를 갖는 entity 를 찾으면, inventory 를 반환한다.")
        @Test
        void test1000() {
            // given
            final String existingItemId = "1";
            final Long stock = 10L;

            // when
            final Inventory result = sut.findByItemId(existingItemId);

            // then
            assertNotNull(result);
            assertEquals(existingItemId, result.getItemId());
            assertEquals(stock, result.getStock());
        }
    }

    @Nested
    class DecreaseByItemId {

        @DisplayName("quantity 가 음수라면, Exception 을 throw 한다.")
        @Test
        void test1() {
            throw new NotImplementedTestException();
        }

        @DisplayName("itemId를 갖는 entity 를 찾지 못하면, Exception 을 throw 한다.")
        @Test
        void test2() {
            throw new NotImplementedTestException();
        }

        @DisplayName("quantity 가 stock 보다 크면, Exception 을 throw 한다.")
        @Test
        void test3() {
            throw new NotImplementedTestException();
        }

        @DisplayName("변경된 entity 가 없다면, Exception 을 throw 한다.")
        @Test
        void test4() {
            throw new NotImplementedTestException();
        }

        @DisplayName("itemId 를 갖는 entity 를 찾으면, stock 을 차감하고 inventory 를 반환한다.")
        @Test
        void test1000() {
            throw new NotImplementedTestException();
        }
    }

    @Nested
    class UpdateStock {

        @DisplayName("수정할 stock 이 유효하지 않다면, Exception 을 throw 한다.")
        @Test
        void test1() {
            throw new NotImplementedTestException();
        }

        @DisplayName("itemId를 갖는 entity 를 찾지 못하면, Exception 을 throw 한다.")
        @Test
        void test2() {
            throw new NotImplementedTestException();
        }

        @DisplayName("itemId 를 갖는 entity 를 찾으면, stock 을 수정하고 inventory 를 반환한다.")
        @Test
        void test1000() {
            throw new NotImplementedTestException();
        }
    }
}
