package org.example.delivery.storeadmin.doamin.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.example.delivery.db.storemenu.StoreMenuEntity;
import org.example.delivery.db.storemenu.StoreMenuRepository;
import org.example.delivery.db.storemenu.enums.StoreMenuStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;

    public StoreMenuEntity getStoreMenuWithThrow(Long id){
        return Optional.ofNullable(storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreMenuStatus.REGISTERED))
            .orElseThrow(()-> new RuntimeException("Store menu not found"));
    }
}