package com.example.inventoryapp.inventory.repository;

import com.example.inventoryapp.inventory.repository.entity.InventoryEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface InventoryJpaRepository {
    void addInventoryEntity(String itemId, Long stock);

    @NotNull Optional<InventoryEntity> findByItemId(@NotNull String itemId);

    @NotNull Integer decreaseStock(@NotNull String itemId, @NotNull Long quantity);
}
