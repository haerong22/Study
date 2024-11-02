package com.example.inventoryapp.inventory.service;

import com.example.inventoryapp.inventory.repository.InventoryJpaRepositoryStub;
import com.example.inventoryapp.inventory.service.domain.Inventory;
import com.example.inventoryapp.inventory.service.exception.InsufficientStockException;
import com.example.inventoryapp.inventory.service.exception.InvalidDecreaseQuantityException;
import com.example.inventoryapp.inventory.service.exception.ItemNotFoundException;
import com.example.inventoryapp.test.exception.NotImplementedTestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {
    @InjectMocks
    private InventoryService sut;

    @Spy
    private InventoryJpaRepositoryStub inventoryJpaRepository;

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
        final String existingItemId = "1";
        final Long stock = 100L;

        @BeforeEach
        void setUp() {
            inventoryJpaRepository.addInventoryEntity(existingItemId, stock);
        }


        @DisplayName("quantity 가 음수라면, Exception 을 throw 한다.")
        @Test
        void test1() {
            // given
            final Long quantity = -1L;

            // when
            assertThrows(InvalidDecreaseQuantityException.class, () -> {
                sut.decreaseByItemId(existingItemId, quantity);
            });
        }

        @DisplayName("itemId를 갖는 entity 를 찾지 못하면, Exception 을 throw 한다.")
        @Test
        void test2() {
            // given
            final Long quantity = 10L;
            final String nonExistingItemId = "2";

            // when
            assertThrows(ItemNotFoundException.class, () -> {
                sut.decreaseByItemId(nonExistingItemId, quantity);
            });
        }

        @DisplayName("quantity 가 stock 보다 크면, Exception 을 throw 한다.")
        @Test
        void test3() {
            // given
            final Long quantity = stock + 1;

            // when
            assertThrows(InsufficientStockException.class, () -> {
                sut.decreaseByItemId(existingItemId, quantity);
            });
        }

        @DisplayName("변경된 entity 가 없다면, Exception 을 throw 한다.")
        @Test
        void test4() {
            // given
            final Long quantity = 10L;

            doReturn(0).when(inventoryJpaRepository)
                    .decreaseStock(existingItemId, quantity);

            // when
            assertThrows(ItemNotFoundException.class, () -> {
                sut.decreaseByItemId(existingItemId, quantity);
            });

            verify(inventoryJpaRepository).decreaseStock(existingItemId, quantity);
        }

        @DisplayName("itemId 를 갖는 entity 를 찾으면, stock 을 차감하고 inventory 를 반환한다.")
        @Test
        void test1000() {
            // given
            final Long quantity = 10L;

            // when
            final Inventory result = sut.decreaseByItemId(existingItemId, quantity);

            // then
            assertNotNull(result);
            assertEquals(existingItemId, result.getItemId());
            assertEquals(stock - quantity, result.getStock());
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
