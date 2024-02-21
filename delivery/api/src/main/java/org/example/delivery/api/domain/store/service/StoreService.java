package org.example.delivery.api.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.example.delivery.common.error.CommonErrorCode;
import org.example.delivery.common.exception.ApiException;
import org.example.delivery.db.store.StoreEntity;
import org.example.delivery.db.store.StoreRepository;
import org.example.delivery.db.store.enums.StoreCategory;
import org.example.delivery.db.store.enums.StoreStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreEntity getStoreWithThrow(Long id) {
        return Optional.ofNullable(storeRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED))
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }

    @Transactional
    public StoreEntity register(StoreEntity store) {
        return Optional.ofNullable(store)
                .map(it -> storeRepository.save(it.register()))
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }

    public List<StoreEntity> searchByCategory(StoreCategory category) {
        return storeRepository.findAllByStatusAndCategoryOrderByStarDesc(
                StoreStatus.REGISTERED,
                category
        );
    }

    public List<StoreEntity> registerStore() {
        return storeRepository.findAllByStatusOrderByIdDesc(StoreStatus.REGISTERED);
    }
}
