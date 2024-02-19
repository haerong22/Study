package org.example.delivery.storeadmin.doamin.storemenu.converter;

import org.example.delivery.db.storemenu.StoreMenuEntity;
import org.example.delivery.storeadmin.doamin.storemenu.controller.model.StoreMenuResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreMenuConverter {

    public StoreMenuResponse toResponse(StoreMenuEntity storeMenuEntity) {
        return StoreMenuResponse.builder()
                .id(storeMenuEntity.getId())
                .name(storeMenuEntity.getName())
                .status(storeMenuEntity.getStatus())
                .amount(storeMenuEntity.getAmount())
                .thumbnailUrl(storeMenuEntity.getThumbnailUrl())
                .likeCount(storeMenuEntity.getLikeCount())
                .sequence(storeMenuEntity.getSequence())
                .build();
    }

    public List<StoreMenuResponse> toResponse(List<StoreMenuEntity> list) {
        return list.stream()
                .map(this::toResponse)
                .toList();
    }
}