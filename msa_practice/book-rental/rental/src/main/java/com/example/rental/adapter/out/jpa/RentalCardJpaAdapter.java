package com.example.rental.adapter.out.jpa;

import com.example.rental.adapter.out.jpa.entity.RentalCardJpaEntity;
import com.example.rental.adapter.out.jpa.repository.RentalCardJpaRepository;
import com.example.rental.application.port.out.RentalCardPort;
import com.example.rental.domain.model.RentalCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RentalCardJpaAdapter implements RentalCardPort {

    private final RentalCardJpaRepository jpaRepository;

    @Override
    public Optional<RentalCard> findRentalCard(String userId) {
        RentalCardJpaEntity entity = jpaRepository.findByMemberId(userId).orElse(null);

        if (entity == null) return Optional.empty();

        return Optional.of(entity.toDomain());
    }

    @Override
    public RentalCard save(RentalCard rentalCard) {
        RentalCardJpaEntity entity = RentalCardJpaEntity.fromDomain(rentalCard);
        return jpaRepository.save(entity).toDomain();
    }
}
