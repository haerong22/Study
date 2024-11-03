package com.example.inventoryapp.test.fixture;

import com.example.inventoryapp.inventory.service.domain.Inventory;
import org.jetbrains.annotations.Nullable;

public class InventoryFixture {

    public static Inventory sampleInventory(
            @Nullable String itemId,
            @Nullable Long stock
    ) {
        return new Inventory(
                itemId == null ? "1" : itemId,
                stock == null ? 100L : stock
        );
    }
}
