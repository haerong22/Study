package com.example.inventoryapp.inventory.controller;

import com.example.inventoryapp.common.controller.ApiResponse;
import com.example.inventoryapp.inventory.controller.dto.DecreaseQuantityRequest;
import com.example.inventoryapp.inventory.controller.dto.InventoryResponse;
import com.example.inventoryapp.inventory.controller.dto.UpdateStockRequest;
import com.example.inventoryapp.inventory.controller.exception.CommonInventoryHttpException;
import com.example.inventoryapp.inventory.service.InventoryService;
import com.example.inventoryapp.inventory.service.domain.Inventory;
import com.example.inventoryapp.inventory.service.exception.InsufficientStockException;
import com.example.inventoryapp.inventory.service.exception.InvalidDecreaseQuantityException;
import com.example.inventoryapp.inventory.service.exception.InvalidStockException;
import com.example.inventoryapp.inventory.service.exception.ItemNotFoundException;
import org.springframework.web.bind.annotation.*;

import static com.example.inventoryapp.inventory.controller.consts.ErrorCodes.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequestMapping("/api/v1/inventory")
@RestController
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{itemId}")
    ApiResponse<InventoryResponse> findByItemId(@PathVariable String itemId) {
        final Inventory inventory = inventoryService.findByItemId(itemId);
        if (inventory == null) {
            throw new CommonInventoryHttpException(
                    ITEM_NOT_FOUND,
                    NOT_FOUND
            );
        }
        return ApiResponse.success(
                InventoryResponse.fromDomain(inventory)
        );
    }

    @PostMapping("/{itemId}/decrease")
    ApiResponse<InventoryResponse> decreaseItem(
            @PathVariable String itemId,
            @RequestBody DecreaseQuantityRequest request
    ) {
        Inventory inventory;

        try {
            inventory = inventoryService.decreaseByItemId(itemId, request.quantity());
        } catch (ItemNotFoundException e) {
            throw new CommonInventoryHttpException(ITEM_NOT_FOUND, NOT_FOUND);
        } catch (InsufficientStockException e) {
            throw new CommonInventoryHttpException(INSUFFICIENT_STOCK, BAD_REQUEST);
        } catch (InvalidDecreaseQuantityException e) {
            throw new CommonInventoryHttpException(INVALID_DECREASE_QUANTITY, BAD_REQUEST);
        }

        return ApiResponse.success(
                InventoryResponse.fromDomain(inventory)
        );
    }

    @PatchMapping("/{itemId}/stock")
    ApiResponse<InventoryResponse> updateStock(
            @PathVariable String itemId,
            @RequestBody UpdateStockRequest request
    ) {
        Inventory inventory;

        try {
            inventory = inventoryService.updateStock(itemId, request.stock());
        } catch (ItemNotFoundException e) {
            throw new CommonInventoryHttpException(ITEM_NOT_FOUND, NOT_FOUND);
        } catch (InvalidStockException e) {
            throw new CommonInventoryHttpException(INVALID_STOCK, BAD_REQUEST);
        }

        return ApiResponse.success(
                InventoryResponse.fromDomain(inventory)
        );
    }
}
