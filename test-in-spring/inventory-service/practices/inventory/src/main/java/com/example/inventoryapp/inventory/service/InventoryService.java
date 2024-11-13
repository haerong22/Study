package com.example.inventoryapp.inventory.service;

import com.example.inventoryapp.inventory.service.domain.Inventory;
import com.example.inventoryapp.inventory.service.event.InventoryDecreasedEvent;
import com.example.inventoryapp.inventory.service.event.InventoryEventPublisher;
import com.example.inventoryapp.inventory.service.event.InventoryUpdatedEvent;
import com.example.inventoryapp.inventory.service.exception.InsufficientStockException;
import com.example.inventoryapp.inventory.service.exception.InvalidDecreaseQuantityException;
import com.example.inventoryapp.inventory.service.exception.InvalidStockException;
import com.example.inventoryapp.inventory.service.exception.ItemNotFoundException;
import com.example.inventoryapp.inventory.service.persistence.InventoryPersistenceAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private final InventoryPersistenceAdapter inventoryAdapter;
    private final InventoryEventPublisher inventoryEventPublisher;

    public InventoryService(InventoryPersistenceAdapter inventoryAdapter, InventoryEventPublisher inventoryEventPublisher) {
        this.inventoryAdapter = inventoryAdapter;
        this.inventoryEventPublisher = inventoryEventPublisher;
    }

    public @Nullable Inventory findByItemId(@NotNull String itemId) {
        return inventoryAdapter.findByItemId(itemId);
    }

    @Transactional
    public @NotNull Inventory decreaseByItemId(@NotNull String itemId,@NotNull Long quantity) {
        if (quantity < 0) {
            throw new InvalidDecreaseQuantityException();
        }

        Inventory inventory = inventoryAdapter.findByItemId(itemId);
        if (inventory == null) {
            throw new ItemNotFoundException();
        }

        if (inventory.getStock() < quantity) {
            throw new InsufficientStockException();
        }

        final Inventory updatedInventory = inventoryAdapter.decreaseStock(itemId, quantity);
        if (updatedInventory == null) {
            throw new ItemNotFoundException();
        }

        final InventoryDecreasedEvent event = new InventoryDecreasedEvent(itemId, quantity, updatedInventory.getStock());
        inventoryEventPublisher.publish(event);

        return updatedInventory;
    }

    public @NotNull Inventory updateStock(@NotNull String itemId, @NotNull Long stock) {
        if (stock < 0) {
            throw new InvalidStockException();
        }

        Inventory inventory = inventoryAdapter.findByItemId(itemId);

        if (inventory == null) {
            throw new ItemNotFoundException();
        }


        inventory.setStock(stock);

        Inventory updatedInventory = inventoryAdapter.save(inventory);

        final InventoryUpdatedEvent event = new InventoryUpdatedEvent(itemId, updatedInventory.getStock());
        inventoryEventPublisher.publish(event);
        return updatedInventory;
    }
}
