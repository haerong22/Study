package org.example.money.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataMemberMoneyRepository extends JpaRepository<MemberMoneyJpaEntity, Long> {

    @Query("SELECT e FROM MemberMoneyJpaEntity e WHERE e.membershipId = :membershipId")
    List<MemberMoneyJpaEntity> findByMembershipId(@Param("membershipId") Long membershipId);

    @Query("SELECT e FROM MemberMoneyJpaEntity e WHERE e.membershipId in :membershipIds")
    List<MemberMoneyJpaEntity> findByMemberMoneyByMembershipIds(@Param("membershipIds") List<Long> membershipIds);
}
