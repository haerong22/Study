package com.example.member.adapter.out.jpa.repository;

import com.example.member.adapter.out.jpa.entity.MemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {

    Optional<MemberJpaEntity> findMemberJpaEntityByIdAndName(String id, String name);
}
