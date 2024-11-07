package com.example.inventoryapp.inventory.repository;

import com.example.inventoryapp.inventory.repository.jpa.InventoryJpaRepository;
import com.example.inventoryapp.inventory.repository.jpa.entity.InventoryEntity;
import com.example.inventoryapp.inventory.service.domain.Inventory;
import com.example.inventoryapp.inventory.service.persistence.InventoryPersistenceAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryPersistenceAdapterImpl implements InventoryPersistenceAdapter {

    private final InventoryJpaRepository inventoryJpaRepository;

    public InventoryPersistenceAdapterImpl(InventoryJpaRepository inventoryJpaRepository) {
        this.inventoryJpaRepository = inventoryJpaRepository;
    }

    @Override
    public @Nullable Inventory findByItemId(@NotNull String itemId) {
        return inventoryJpaRepository.findByItemId(itemId)
                .map(this::mapToDomain)
                .orElse(null);
    }

    @Override
    public @Nullable Inventory decreaseStock(@NotNull String itemId, @NotNull Long quantity) {
        final Integer updateCount = inventoryJpaRepository.decreaseStock(itemId, quantity);
        if (updateCount == 0) {
            return null;
        }
        return inventoryJpaRepository.findByItemId(itemId)
                .map(this::mapToDomain)
                .orElse(null);
    }

    @Override
    public @NotNull Inventory save(@NotNull Inventory inventory) {
        InventoryEntity entity = null;

        if (inventory.getId() != null) {
            entity = inventoryJpaRepository.findById(inventory.getId()).orElse(null);
        }

        if (entity == null) {
            entity = new InventoryEntity(null, inventory.getItemId(), inventory.getStock());
        }

        entity.setStock(inventory.getStock());

        return mapToDomain(inventoryJpaRepository.save(entity));
    }

    private @NotNull Inventory mapToDomain(InventoryEntity entity) {
        return new Inventory(entity.getId(), entity.getItemId(), entity.getStock());
    }
}
