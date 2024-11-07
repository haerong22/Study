package com.example.inventoryapp.inventory.repository.jpa;

import com.example.inventoryapp.config.JpaConfig;
import com.example.inventoryapp.inventory.repository.jpa.entity.InventoryEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@Import(JpaConfig.class)
@ActiveProfiles("mysql-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class InventoryJpaRepositoryMysqlTest {
    @Autowired
    private InventoryJpaRepository sut;

    @Autowired
    private TestEntityManager entityManager;

    @Nested
    class FindByItemId {
        final String existingItemId = "1";
        final String nonExistingItemId = "2";
        final Long stock = 100L;

        @DisplayName("itemId 를 가지는 entity 가 없다면, empty 를 반환한다.")
        @Test
        void test1() {
            // when
            final Optional<InventoryEntity> result = sut.findByItemId(nonExistingItemId);

            // then
            assertTrue(result.isEmpty());
        }

        @DisplayName("itemId 를 가지는 entity 가 있다면, entity 를 반환한다.")
        @Test
        void test1000() {
            // when
            final Optional<InventoryEntity> result = sut.findByItemId(existingItemId);

            // then
            assertTrue(result.isPresent());

            final InventoryEntity entity = result.get();
            assertNotNull(entity.getId());
            assertEquals(existingItemId, entity.getItemId());
            assertEquals(stock, entity.getStock());
        }
    }

    @Nested
    class DecreaseStock {
        final String existingItemId = "1";
        final String nonExistingItemId = "2";
        final Long quantity = 10L;

        @DisplayName("itemId 를 가지는 entity 가 없다면, 0을 반환한다.")
        @Test
        void test1() {
            // when
            final Integer result = sut.decreaseStock(nonExistingItemId, quantity);

            // then
            assertEquals(0, result);
        }

        @DisplayName("itemId 를 가지는 entity 가 있다면, 1을 반환한다.")
        @Test
        void test1000() {
            // given
            ZonedDateTime lastUpdatedAt = sut.findByItemId(existingItemId).get().getUpdatedAt();

            // when
            final Integer result = sut.decreaseStock(existingItemId, quantity);
            entityManager.clear();

            // then
            assertEquals(1, result);

            final InventoryEntity entity = sut.findByItemId(existingItemId).get();
            assertEquals(100 - quantity, entity.getStock());
            assertNotEquals(lastUpdatedAt, entity.getUpdatedAt());
        }
    }

    @Nested
    class Save {
        final Long existingId = 1L;
        final String existingItemId = "1";
        final String nonExistingItemId = "2";

        @DisplayName("id 를 갖는 entity 가 없다면, entity 를 추가하고 추가된 entity 를 반환한다.")
        @Test
        void test1() {
            // given
            final Long newStock = 1234L;

            // when
            final InventoryEntity entity = new InventoryEntity(null, nonExistingItemId, newStock);
            final InventoryEntity result = sut.save(entity);

            // then
            assertNotNull(result.getId());
            assertEquals(nonExistingItemId, result.getItemId());
            assertEquals(newStock, result.getStock());

            assertNotNull(result.getCreatedAt());
            assertNotNull(result.getUpdatedAt());
        }

        @DisplayName("id 를 갖는 entity 가 있다면, entity 를 수정하고 추가된 entity 를 반환한다.")
        @Test
        void test1000() {
            // given
            final Long newStock = 1234L;
            final InventoryEntity entity = sut.findByItemId(existingItemId).get();
            final ZonedDateTime createdAt = entity.getCreatedAt();
            final ZonedDateTime lastUpdatedAt = entity.getUpdatedAt();

            // when
            entity.setStock(newStock);
            final InventoryEntity result = sut.save(entity);
            entityManager.flush();

            // then
            assertEquals(existingId, result.getId());
            assertEquals(existingItemId, result.getItemId());
            assertEquals(newStock, result.getStock());

            assertNotNull(result.getCreatedAt());
            assertNotNull(result.getUpdatedAt());
            assertEquals(createdAt, result.getCreatedAt());
            assertNotEquals(lastUpdatedAt, result.getUpdatedAt());
        }
    }
}
