package com.example.inventoryapp.inventory.service;

import com.example.inventoryapp.inventory.service.domain.Inventory;
import com.example.inventoryapp.inventory.service.event.InventoryDecreasedEvent;
import com.example.inventoryapp.inventory.service.event.InventoryEventPublisher;
import com.example.inventoryapp.inventory.service.event.InventoryUpdatedEvent;
import com.example.inventoryapp.inventory.service.exception.InsufficientStockException;
import com.example.inventoryapp.inventory.service.exception.InvalidDecreaseQuantityException;
import com.example.inventoryapp.inventory.service.exception.InvalidStockException;
import com.example.inventoryapp.inventory.service.exception.ItemNotFoundException;
import com.example.inventoryapp.inventory.service.persistence.InventoryPersistenceAdapterStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    private InventoryPersistenceAdapterStub inventoryAdapter;

    @Mock
    private InventoryEventPublisher inventoryEventPublisher;

    @Nested
    class FindByItemId {
        final String itemId = "1";
        final Long stock = 10L;

        @BeforeEach
        void setUp() {
            inventoryAdapter.addInventory(itemId, stock);
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
            inventoryAdapter.addInventory(existingItemId, stock);
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

            doReturn(null).when(inventoryAdapter).decreaseStock(existingItemId, quantity);

            // when
            assertThrows(ItemNotFoundException.class, () -> {
                sut.decreaseByItemId(existingItemId, quantity);
            });

            verify(inventoryAdapter).decreaseStock(existingItemId, quantity);
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

            final InventoryDecreasedEvent event = new InventoryDecreasedEvent(existingItemId, quantity, stock - quantity);
            verify(inventoryEventPublisher).publish(event);
        }
    }

    @Nested
    class UpdateStock {
        final String existingItemId = "1";
        final Long stock = 100L;

        @BeforeEach
        void setUp() {
            inventoryAdapter.addInventory(existingItemId, stock);
        }

        @DisplayName("수정할 stock 이 유효하지 않다면, Exception 을 throw 한다.")
        @Test
        void test1() {
            // given
            final Long newStock = -1L;

            // when
            assertThrows(InvalidStockException.class, () -> {
                sut.updateStock(existingItemId, newStock);
            });
        }

        @DisplayName("itemId를 갖는 entity 를 찾지 못하면, Exception 을 throw 한다.")
        @Test
        void test2() {
            // given
            final Long nextStock = 200L;
            final String nonExistingItemId = "2";

            // when
            assertThrows(ItemNotFoundException.class, () -> {
                sut.updateStock(nonExistingItemId, nextStock);
            });
        }

        @DisplayName("itemId 를 갖는 entity 를 찾으면, stock 을 수정하고 inventory 를 반환한다.")
        @Test
        void test1000() {
            // given
            final Long newStock = 200L;

            // when
            final Inventory result = sut.updateStock(existingItemId, newStock);

            // then
            assertNotNull(result);
            assertEquals(existingItemId, result.getItemId());
            assertEquals(newStock, result.getStock());

            final InventoryUpdatedEvent event = new InventoryUpdatedEvent(existingItemId, newStock);
            verify(inventoryEventPublisher).publish(event);
        }
    }
}
