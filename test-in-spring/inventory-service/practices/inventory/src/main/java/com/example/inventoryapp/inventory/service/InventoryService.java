package com.example.inventoryapp.inventory.service;

import com.example.inventoryapp.inventory.repository.InventoryJpaRepository;
import com.example.inventoryapp.inventory.repository.entity.InventoryEntity;
import com.example.inventoryapp.inventory.service.domain.Inventory;
import com.example.inventoryapp.inventory.service.exception.InsufficientStockException;
import com.example.inventoryapp.inventory.service.exception.InvalidDecreaseQuantityException;
import com.example.inventoryapp.inventory.service.exception.InvalidStockException;
import com.example.inventoryapp.inventory.service.exception.ItemNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InventoryService {
    private final InventoryJpaRepository inventoryJpaRepository;

    public InventoryService(InventoryJpaRepository inventoryJpaRepository) {
        this.inventoryJpaRepository = inventoryJpaRepository;
    }

    public @Nullable Inventory findByItemId(@NotNull String itemId) {
        return inventoryJpaRepository.findByItemId(itemId)
                .map(this::mapToDomain)
                .orElse(null);
    }

    public @NotNull Inventory decreaseByItemId(@NotNull String itemId,@NotNull Long quantity) {
        if (quantity < 0) {
            throw new InvalidDecreaseQuantityException();
        }

        InventoryEntity entity = inventoryJpaRepository.findByItemId(itemId)
                .orElseThrow(ItemNotFoundException::new);

        if (quantity > entity.getStock()) {
            throw new InsufficientStockException();
        }

        final Integer updateCount = inventoryJpaRepository.decreaseStock(itemId, quantity);
        if (updateCount == 0) {
            throw new ItemNotFoundException();
        }

        final InventoryEntity updatedEntity = inventoryJpaRepository.findByItemId(itemId)
                .orElseThrow(ItemNotFoundException::new);

        return mapToDomain(updatedEntity);
    }

    private Inventory mapToDomain(InventoryEntity entity) {
        return new Inventory(entity.getItemId(), entity.getStock());
    }

    public @NotNull Inventory updateStock(@NotNull String itemId, @NotNull Long stock) {
        if (stock < 0) {
            throw new InvalidStockException();
        }

        InventoryEntity entity = inventoryJpaRepository.findByItemId(itemId)
                .orElseThrow(ItemNotFoundException::new);

        entity.setStock(stock);

        return mapToDomain(inventoryJpaRepository.save(entity));
    }
}
