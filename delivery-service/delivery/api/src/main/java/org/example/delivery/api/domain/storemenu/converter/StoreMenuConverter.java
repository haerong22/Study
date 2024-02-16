package org.example.delivery.api.domain.storemenu.converter;

import org.example.delivery.api.common.annotation.Converter;
import org.example.delivery.api.common.error.CommonErrorCode;
import org.example.delivery.api.common.exception.ApiException;
import org.example.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.example.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.example.delivery.db.storemenu.StoreMenuEntity;

import java.util.List;
import java.util.Optional;

@Converter
public class StoreMenuConverter {

    public StoreMenuEntity toEntity(StoreMenuRegisterRequest request) {
        return Optional.ofNullable(request)
                .map(it -> {
                    return StoreMenuEntity.builder()
                            .storeId(it.getStoreId())
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
                            .storeId(it.getStoreId())
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
