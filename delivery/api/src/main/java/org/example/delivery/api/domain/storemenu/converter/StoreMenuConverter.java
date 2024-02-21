package org.example.delivery.api.domain.storemenu.converter;

import org.example.delivery.common.annotation.Converter;
import org.example.delivery.common.error.CommonErrorCode;
import org.example.delivery.common.exception.ApiException;
import org.example.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.example.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.example.delivery.db.store.StoreEntity;
import org.example.delivery.db.storemenu.StoreMenuEntity;

import java.util.List;
import java.util.Optional;

@Converter
public class StoreMenuConverter {

    public StoreMenuEntity toEntity(StoreMenuRegisterRequest request, StoreEntity storeEntity) {
        return Optional.ofNullable(request)
                .map(it -> {
                    return StoreMenuEntity.builder()
                            .store(storeEntity)
                            .name(it.getName())
                            .amount(it.getAmount())
                            .thumbnailUrl(it.getThumbnailUrl())
                            .build();
                })
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }

    public StoreMenuResponse toResponse(StoreMenuEntity entity) {
        return Optional.ofNullable(entity)
                .map(it -> {
                    return StoreMenuResponse.builder()
                            .id(it.getId())
                            .storeId(it.getStore().getId())
                            .name(it.getName())
                            .amount(it.getAmount())
                            .status(it.getStatus())
                            .thumbnailUrl(it.getThumbnailUrl())
                            .likeCount(it.getLikeCount())
                            .sequence(it.getSequence())
                            .build();
                })
                .orElseThrow(() -> new ApiException(CommonErrorCode.NULL_POINT));
    }

    public List<StoreMenuResponse> toResponse(List<StoreMenuEntity> list) {
        return list.stream().map(this::toResponse).toList();
    }
}
