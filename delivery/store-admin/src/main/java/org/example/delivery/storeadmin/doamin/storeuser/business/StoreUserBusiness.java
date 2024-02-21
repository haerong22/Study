package org.example.delivery.storeadmin.doamin.storeuser.business;


import lombok.RequiredArgsConstructor;
import org.example.delivery.db.store.StoreRepository;
import org.example.delivery.db.store.enums.StoreStatus;
import org.example.delivery.db.storeuser.StoreUserEntity;
import org.example.delivery.storeadmin.common.annotation.Business;
import org.example.delivery.storeadmin.doamin.storeuser.controller.model.StoreUserRegisterRequest;
import org.example.delivery.storeadmin.doamin.storeuser.controller.model.StoreUserResponse;
import org.example.delivery.storeadmin.doamin.storeuser.converter.StoreUserConverter;
import org.example.delivery.storeadmin.doamin.storeuser.service.StoreUserService;

@Business
@RequiredArgsConstructor
public class StoreUserBusiness {

    private final StoreUserConverter storeUserConverter;
    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;

    public StoreUserResponse register(StoreUserRegisterRequest request) {
        var storeEntity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(request.getStoreName(), StoreStatus.REGISTERED);
        StoreUserEntity entity = storeUserConverter.toEntity(request, storeEntity);
        StoreUserEntity registered = storeUserService.register(entity);
        return storeUserConverter.toResponse(registered, storeEntity);
    }
}
