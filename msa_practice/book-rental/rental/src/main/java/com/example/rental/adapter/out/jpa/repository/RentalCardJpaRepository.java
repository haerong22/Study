package com.example.rental.adapter.out.jpa.repository;

import com.example.rental.adapter.out.jpa.entity.RentalCardJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalCardJpaRepository extends JpaRepository<RentalCardJpaEntity, RentalCardJpaEntity.RentalCardNo> {

    @Query("select m from RentalCardJpaEntity m where m.member.id = :id")
    Optional<RentalCardJpaEntity> findByMemberId(@Param("id") String memberId);

    @Query("select m from RentalCardJpaEntity m where m.rentalCardNo.no = :id")
    Optional<RentalCardJpaEntity> findById(@Param("id") Long rentalCardId);
}
