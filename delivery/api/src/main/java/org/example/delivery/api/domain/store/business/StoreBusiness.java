package org.example.delivery.api.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.example.delivery.common.annotation.Business;
import org.example.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.example.delivery.api.domain.store.controller.model.StoreResponse;
import org.example.delivery.api.domain.store.converter.StoreConverter;
import org.example.delivery.api.domain.store.service.StoreService;
import org.example.delivery.db.store.StoreEntity;
import org.example.delivery.db.store.enums.StoreCategory;

import java.util.List;

@Business
@RequiredArgsConstructor
public class StoreBusiness {

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    public StoreResponse register(StoreRegisterRequest storeRegisterRequest) {
        StoreEntity entity = storeConverter.toEntity(storeRegisterRequest);
        StoreEntity registered = storeService.register(entity);
        return storeConverter.toResponse(registered);
    }

    public List<StoreResponse> searchCategory(StoreCategory storeCategory) {
        return storeService.searchByCategory(storeCategory).stream()
                .map(storeConverter::toResponse)
                .toList();
    }
}
