package org.example.delivery.api.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.example.delivery.api.common.annotation.Business;
import org.example.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.example.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.example.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.example.delivery.api.domain.storemenu.service.StoreMenuService;
import org.example.delivery.db.storemenu.StoreMenuEntity;

import java.util.List;

@Business
@RequiredArgsConstructor
public class StoreMenuBusiness {

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    public StoreMenuResponse register(StoreMenuRegisterRequest request) {
        StoreMenuEntity entity = storeMenuConverter.toEntity(request);
        StoreMenuEntity registered = storeMenuService.register(entity);
        return storeMenuConverter.toResponse(registered);
    }

    public List<StoreMenuResponse> search(Long storeId) {
        return storeMenuService.getStoreMenuByStoreId(storeId).stream()
                .map(storeMenuConverter::toResponse)
                .toList();
    }
}
