package com.example.inventoryapp.inventory.service.persistence;

import com.example.inventoryapp.inventory.service.domain.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InventoryPersistenceAdapterStub implements InventoryPersistenceAdapter {

    private final List<Inventory> inventoryList = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public void addInventory(String itemId, Long stock) {
        final Long id = idGenerator.getAndIncrement();
        inventoryList.add(new Inventory(id, itemId, stock));
    }

    @Override
    public @Nullable Inventory findByItemId(@NotNull String itemId) {
        return internalFindByItemId(itemId).orElse(null);
    }

    @Override
    public @Nullable Inventory decreaseStock(@NotNull String itemId, @NotNull Long quantity) {
        final Optional<Inventory> optionalInventory = internalFindByItemId(itemId);

        if (optionalInventory.isEmpty()) {
            return null;
        }

        final Inventory inventory = optionalInventory.get();
        inventory.setStock(inventory.getStock() - quantity);

        return inventory;
    }

    @Override
    public @NotNull Inventory save(@NotNull Inventory inventory) {
        final Long inventoryId = inventory.getId();
        final Optional<Inventory> optionalInventory = inventoryList.stream()
                .filter(i -> i.getId() != null && i.getId().equals(inventoryId))
                .findFirst();

        Inventory inventoryToSave;
        if (optionalInventory.isPresent()) {
            inventoryToSave = optionalInventory.get();
            inventoryToSave.setStock(inventory.getStock());
        } else {
            final Long id = idGenerator.getAndIncrement();
            inventoryToSave = new Inventory(
                    id,
                    inventory.getItemId(),
                    inventory.getStock()
            );
            inventoryList.add(inventoryToSave);
        }

        return inventoryToSave;
    }

    private Optional<Inventory> internalFindByItemId(String itemId) {
        return inventoryList.stream()
                .filter(entity -> entity.getItemId().equals(itemId))
                .findFirst();
    }
}
