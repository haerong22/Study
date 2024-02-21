package org.example.delivery.api.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.example.delivery.api.domain.store.service.StoreService;
import org.example.delivery.common.annotation.Business;
import org.example.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.example.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.example.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.example.delivery.api.domain.storemenu.service.StoreMenuService;
import org.example.delivery.db.store.StoreEntity;
import org.example.delivery.db.storemenu.StoreMenuEntity;

import java.util.List;

@Business
@RequiredArgsConstructor
public class StoreMenuBusiness {

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;
    private final StoreService storeService;

    public StoreMenuResponse register(StoreMenuRegisterRequest request) {
        StoreEntity storeEntity = storeService.getStoreWithThrow(request.getStoreId());
        StoreMenuEntity entity = storeMenuConverter.toEntity(request, storeEntity);
        StoreMenuEntity registered = storeMenuService.register(entity);
        return storeMenuConverter.toResponse(registered);
    }

    public List<StoreMenuResponse> search(Long storeId) {
        return storeMenuService.getStoreMenuByStoreId(storeId).stream()
                .map(storeMenuConverter::toResponse)
                .toList();
    }
}
