package org.example.delivery.storeadmin.doamin.authorization;

import lombok.RequiredArgsConstructor;
import org.example.delivery.db.store.StoreEntity;
import org.example.delivery.db.store.StoreRepository;
import org.example.delivery.db.store.enums.StoreStatus;
import org.example.delivery.db.storeuser.StoreUserEntity;
import org.example.delivery.storeadmin.doamin.authorization.model.UserSession;
import org.example.delivery.storeadmin.doamin.storeuser.service.StoreUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        StoreUserEntity storeUserEntity = storeUserService.getUser(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StoreEntity storeEntity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(storeUserEntity.getStoreId(), StoreStatus.REGISTERED)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        return UserSession.builder()
                .userId(storeUserEntity.getId())
                .email(storeUserEntity.getEmail())
                .password(storeUserEntity.getPassword())
                .status(storeUserEntity.getStatus())
                .role(storeUserEntity.getRole())
                .registeredAt(storeUserEntity.getRegisteredAt())
                .unregisteredAt(storeUserEntity.getUnregisteredAt())
                .lastLoginAt(storeUserEntity.getLastLoginAt())
                .storeId(storeEntity.getId())
                .storeName(storeEntity.getName())
                .build();
    }

}
