package org.example.delivery.api.domain.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.example.delivery.api.common.error.CommonErrorCode;
import org.example.delivery.api.common.exception.ApiException;
import org.example.delivery.db.storemenu.StoreMenuEntity;
import org.example.delivery.db.storemenu.StoreMenuRepository;
import org.example.delivery.db.storemenu.enums.StoreMenuStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;

    public StoreMenuEntity getStoreMenuWithThrow(Long id) {
        return storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreMenuStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }

    public List<StoreMenuEntity> getStoreMenuByStoreId(Long storeId) {
        return storeMenuRepository.findAllByStoreIdAndStatusOrderBySequenceDesc(storeId, StoreMenuStatus.REGISTERED);
    }

    public StoreMenuEntity register(StoreMenuEntity storeMenuEntity) {
        return Optional.ofNullable(storeMenuEntity)
                .map(it -> storeMenuRepository.save(it.register()))
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }
}
