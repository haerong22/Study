package com.example.inventoryapp.inventory.repository;

import com.example.inventoryapp.inventory.repository.entity.InventoryEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InventoryJpaRepositoryStub implements InventoryJpaRepository {
    private final List<InventoryEntity> inventoryEntities = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public void addInventoryEntity(@NotNull String itemId, @NotNull Long stock) {
        final Long id = idGenerator.getAndIncrement();
        inventoryEntities.add(new InventoryEntity(id, itemId, stock));
    }

    @Override
    public @NotNull Optional<InventoryEntity> findByItemId(@NotNull String itemId) {
        return inventoryEntities.stream()
                .filter(entity ->  entity.getItemId().equals(itemId))
                .findFirst();
    }

    @Override
    public @NotNull Integer decreaseStock(@NotNull String itemId, @NotNull Long quantity) {
        final Optional<InventoryEntity> optionalEntity = inventoryEntities.stream()
                .filter(entity -> entity.getItemId().equals(itemId))
                .findFirst();

        if (optionalEntity.isEmpty()) {
            return 0;
        }

        final InventoryEntity entity = optionalEntity.get();
        entity.setStock(entity.getStock() - quantity);

        return 1;
    }
}
