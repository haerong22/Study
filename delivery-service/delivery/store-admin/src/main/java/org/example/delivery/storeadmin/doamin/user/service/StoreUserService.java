package org.example.delivery.storeadmin.doamin.user.service;

import lombok.RequiredArgsConstructor;
import org.example.delivery.db.storeuser.StoreUserEntity;
import org.example.delivery.db.storeuser.StoreUserRepository;
import org.example.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreUserService {

    private final StoreUserRepository storeUserRepository;
    private final PasswordEncoder passwordEncoder;

    public StoreUserEntity register(StoreUserEntity storeUserEntity) {
        String encoded = passwordEncoder.encode(storeUserEntity.getPassword());
        return storeUserRepository.save(storeUserEntity.register(encoded));
    }

    public Optional<StoreUserEntity> getUser(String email) {
        return storeUserRepository.findFirstByEmailAndStatusOrderByIdDesc(email, StoreUserStatus.REGISTERED);
    }
}
