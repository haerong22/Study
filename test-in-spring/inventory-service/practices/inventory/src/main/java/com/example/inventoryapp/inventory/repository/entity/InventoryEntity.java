package com.example.inventoryapp.inventory.repository.entity;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Table(name = "inventory")
@Entity
public class InventoryEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Long id;
    private @NotNull String itemId;
    private @NotNull Long stock;

    public InventoryEntity() {
    }

    public InventoryEntity(@Nullable Long id, @NotNull String itemId, @NotNull Long stock) {
        this.id = id;
        this.itemId = itemId;
        this.stock = stock;
    }

    public @Nullable Long getId() {
        return id;
    }

    public @NotNull String getItemId() {
        return itemId;
    }

    public @NotNull Long getStock() {
        return stock;
    }

    public void setStock(@NotNull Long stock) {
        this.stock = stock;
    }
}
